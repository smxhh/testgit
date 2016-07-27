    package com.cqs.bishe.service;

    import com.cqs.bishe.result.ResultEnum;
    import com.cqs.bishe.service.inter.ServiceCheckInterface;
    import org.apache.commons.lang3.StringUtils;

    /**
     * Created by cqs on 16-6-13.
     */
    public class CheckService implements ServiceCheckInterface {
        @Override
        public ResultEnum check(String... params) {
            for (String param : params) {
                if (StringUtils.isBlank(param)) {
                    return ResultEnum.CHECK_ERROR;
                }
            }
            return ResultEnum.SUCCESS;
        }
    }
