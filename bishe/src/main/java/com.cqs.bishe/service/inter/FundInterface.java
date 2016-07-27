package com.cqs.bishe.service.inter;

import com.cqs.bishe.bean.Fund;
import com.cqs.bishe.bean.FundOrder;
import com.cqs.bishe.bean.FundSelectCondition;
import com.cqs.bishe.result.ResultEnum;

import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by cqs on 16-6-13.
 */
public interface FundInterface {

    public ResultEnum insertFund(Fund fund);
    public boolean purchaseFund(FundOrder fundOrder);
    public ResultEnum startFund(Fund fund);
    public ResultEnum stopFund(Fund fund);
    public List<Fund> selectFund(FundSelectCondition condition);
    public int count(FundSelectCondition condition);
}
