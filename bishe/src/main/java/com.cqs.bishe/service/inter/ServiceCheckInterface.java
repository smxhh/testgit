package com.cqs.bishe.service.inter;

import com.cqs.bishe.result.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cqs on 16-6-5.
 */
public interface ServiceCheckInterface {
    public static final Logger logger = LoggerFactory.getLogger(ServiceCheckInterface.class);

    public ResultEnum check(String... params);
}
