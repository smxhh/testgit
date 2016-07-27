package com.cqs.bishe.filter;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by cqs on 16-6-18.
 */
public class SMSFilter extends GenericFilterBean {

    private volatile static ConcurrentHashMap<String, Long> checkIps = new ConcurrentHashMap();
    private volatile static Set<String> blackIps = Sets.newConcurrentHashSet();
    private static ReadWriteLock checkLock = new ReentrantReadWriteLock();
    private static Lock checkReadLock = checkLock.readLock();
    private static Lock checkWriteLock = checkLock.writeLock();

    private static ReadWriteLock blackLock = new ReentrantReadWriteLock();
    private static Lock blackReadLock = blackLock.readLock();
    private static Lock blackWriteLock = blackLock.writeLock();
    private static ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);

    private static Logger logger = LoggerFactory.getLogger(SMSFilter.class);

    /**
     * 2个定时任务（1s执行2次则加入黑名单；12小时候清除黑名单）
     */
    static {
        executor.scheduleWithFixedDelay(new ScheduledCheckIps(),1, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(new ScheduledBlackIps(), 12, 12, TimeUnit.HOURS);
    }

    @Override
    protected void finalize() throws Throwable {
        executor.shutdown();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String ip = String.valueOf(request.getRemoteAddr());
        if (isBlackIp(ip)) {
            return;
        }
        updateCheckIps(ip);//把次数加1，实时判断次数

        logger.info("ip:{},times:{}", ip, checkIps.get(ip));
        filterChain.doFilter(request, response);//如果不在黑名单中返回到controller
    }

    private boolean isBlackIp(String ip) {
        boolean result = false;
        blackReadLock.lock();
        if (blackIps.contains(ip)) {
            result = true;
        }
        blackReadLock.unlock();
        return result;
    }

    private void updateCheckIps(String ip) {
        checkReadLock.lock();

        if (checkIps.containsKey(ip)) {
            // 为了保证原子性，使用了序列化
            synchronized (ip) {
                long oldValue = checkIps.get(ip);
                checkIps.put(ip, oldValue + 1);
            }
        } else {
            synchronized (ip) {
                if (checkIps.containsKey(ip)) {
                    long oldValue = checkIps.get(ip);
                    checkIps.put(ip, oldValue + 1);
                } else {
                    checkIps.put(ip, new Long(1));
                }
            }
        }
        checkReadLock.unlock();

    }


    static class ScheduledCheckIps implements Runnable {

        @Override
        public void run() {
            checkReadLock.lock();
            for (Map.Entry<String,Long> one:checkIps.entrySet()){
                if(one.getValue() >= 2){
                    blackIps.add(one.getKey());
                }
            }
            checkReadLock.unlock();


            checkWriteLock.lock();
            checkIps.clear();
            checkWriteLock.unlock();
            logger.info("ScheduledCheckIps done，now：{}",System.nanoTime());
        }
    }


    static class ScheduledBlackIps implements Runnable {

        @Override
        public void run() {
            blackWriteLock.lock();
            blackIps.clear();
            blackWriteLock.unlock();
            logger.info("ScheduledBlackIps done，now：{}",System.nanoTime());
        }
    }
}
