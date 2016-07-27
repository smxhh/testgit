package com.cqs.bishe.filter;

import com.google.common.base.Splitter;
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
import java.util.List;

/**
 * Created by cqs on 16-6-11.
 */
public class AdminFilter extends GenericFilterBean {
    private Logger logger = LoggerFactory.getLogger(AdminFilter.class);
    private List<String> whiteIps;
    private String ips;
    public void setIps(String ips) {
        this.ips = ips;
        whiteIps = Splitter.on(";").splitToList(ips);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if("1".equals(request.getParameter("type")) && ! whiteIps.contains(request.getRemoteAddr())){
            return;
        } else {
            filterChain.doFilter(request,response);
        }
    }


}
