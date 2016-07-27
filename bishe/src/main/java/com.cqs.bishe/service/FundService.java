package com.cqs.bishe.service;

import com.cqs.bishe.bean.Fund;
import com.cqs.bishe.bean.FundOrder;
import com.cqs.bishe.bean.FundSelectCondition;
import com.cqs.bishe.dao.mysqldb.FundMapper;
import com.cqs.bishe.result.ResultEnum;
import com.cqs.bishe.service.inter.FundInterface;
import com.cqs.bishe.service.inter.ServiceCheckInterface;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by cqs on 16-6-13.
 */


@Service
public class FundService extends CheckService implements FundInterface, ServiceCheckInterface {

    @Autowired
    private FundMapper fundMapper;


    @Override
    public ResultEnum insertFund(Fund fund) {
        ResultEnum result = check(fund.getFundCp(), fund.getInfo());
        if (ResultEnum.isSuccess(result)) {
            try {
                boolean insertSuccess = fundMapper.insert(fund);
                if (!insertSuccess) {
                    result = ResultEnum.INSERT_ERROR;
                }
            }catch (Exception e){
                result = ResultEnum.INSERT_ERROR;
                logger.error("insert fund error, company:{}",fund.getFundCp(),e);
            }
        }
        return result;
    }

    @Override
    public boolean purchaseFund(FundOrder fundOrder) {
        boolean success = fundMapper.purchase(fundOrder);
        return success;
    }

    public ResultEnum startFund(String company) {
        ResultEnum resultEnum = ResultEnum.ADMIN_START_FUND_SUCCESS;
        try {
            boolean success = fundMapper.start(company);
            if(! success){
                resultEnum = ResultEnum.ADMIN_START_FUND_ERROR;
            }
        }catch (Exception e){
            resultEnum = ResultEnum.ADMIN_START_FUND_ERROR;
            logger.error("start fund error",e);
        }
        return resultEnum;
    }

    public ResultEnum stopFund(String company) {
        ResultEnum resultEnum = ResultEnum.ADMIN_STOP_FUND_SUCCESS;
        try {
            boolean success = fundMapper.stop(company);
            if(! success){
                resultEnum = ResultEnum.ADMIN_STOP_FUND_ERROR;
            }
        }catch (Exception e){
            resultEnum = ResultEnum.ADMIN_STOP_FUND_ERROR;
            logger.error("start fund error",e);
        }
        return resultEnum;
    }


    @Override
    public ResultEnum startFund(Fund fund) {
        if(fund == null || StringUtils.isBlank(fund.getFundCp())){
            return ResultEnum.ADMIN_START_FUND_ERROR;
        }
        return startFund(fund.getFundCp());
    }

    @Override
    public ResultEnum stopFund(Fund fund) {
        if(fund == null || StringUtils.isBlank(fund.getFundCp())){
            return ResultEnum.ADMIN_START_FUND_ERROR;
        }
        return stopFund(fund.getFundCp());
    }

    @Override
    public List<Fund> selectFund(FundSelectCondition condition) {
        if(condition == null){
            return Lists.newArrayList();
        }
        int offset = (condition.getPageIndex() - 1) *condition.getSize();
        return fundMapper.select(condition,new RowBounds(offset,condition.getSize()));
    }

    @Override
    public int count(FundSelectCondition condition) {
        if(condition == null){
            return 0;
        }
        return fundMapper.count(condition);
    }

    public int count() {
        return count(new FundSelectCondition());
    }

}
