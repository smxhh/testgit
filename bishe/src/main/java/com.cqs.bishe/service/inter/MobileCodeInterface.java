package com.cqs.bishe.service.inter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PriorityQueue;

/**
 * Created by cqs on 16-6-6.
 */
public interface MobileCodeInterface {
    static final Logger logger = LoggerFactory.getLogger(MobileCodeInterface.class);

    public String makeMobileCode(String mobile);

    public boolean equalVCode(String mobile,String vCode);
}
