import com.cqs.bishe.bean.User;
import com.cqs.bishe.tool.MD5Tool;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Created by cqs on 16-6-5.
 */
public class Tst {

    @Test
    public void testmd5() {
        String str="345633345";
        try {
            String encoder = MD5Tool.encodePwdByMd5(str);
            System.out.println(encoder);
            String d = MD5Tool.encodePwdByMd5(encoder);
            System.out.println(d);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private volatile static ConcurrentHashMap<String, Long> checkIps = new ConcurrentHashMap();
    private volatile static Set<String> blackIps = Sets.newConcurrentHashSet();
    private static ReadWriteLock checkLock = new ReentrantReadWriteLock();
    private static Lock checkReadLock = checkLock.readLock();
    private static Lock checkWriteLock = checkLock.writeLock();

    private static ReadWriteLock blackLock = new ReentrantReadWriteLock();
    private static Lock blackReadLock = blackLock.readLock();
    private static Lock blackWriteLock = blackLock.writeLock();

    private static Logger logger = LoggerFactory.getLogger(Tst.class);

    static {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.scheduleWithFixedDelay(new ScheduledCheckIps(),1, 1, TimeUnit.SECONDS);
        executor.scheduleWithFixedDelay(new ScheduledBlackIps(), 12, 12, TimeUnit.HOURS);

    }

    public static void main(String[] args) {

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
