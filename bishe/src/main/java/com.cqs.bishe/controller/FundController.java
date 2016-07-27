package com.cqs.bishe.controller;

import com.cqs.bishe.bean.Fund;
import com.cqs.bishe.bean.FundOrder;
import com.cqs.bishe.bean.FundSelectCondition;
import com.cqs.bishe.bean.response.FundList;
import com.cqs.bishe.result.ResultEnum;
import com.cqs.bishe.service.FundService;
import com.cqs.bishe.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by cqs on 16-6-18.
 */


@Controller
@RequestMapping("/fund")
public class FundController {

    @Autowired
    private FundService fundService;
    @Autowired
    private UserService userService;

    @RequestMapping("/getFunds")
    @ResponseBody
    public FundList getFunds(
            @RequestParam(value = "size", required = true) int size,
            @RequestParam(value = "index", required = true) int index,
            @RequestParam(value = "company", required = false) String company,
            @RequestParam(value = "minRate", required = false) Double minRate,
            @RequestParam(value = "maxRate", required = false) Double maxRate,
            HttpServletRequest request
    ) {

        if (size <= 0 || index <= 0) {
            return new FundList(ResultEnum.CHECK_ERROR.getId(), null);
        }
        FundSelectCondition condition = new FundSelectCondition();
        condition.setSize(size);
        condition.setPageIndex(index);
        condition.setCompany(company);
        condition.setMinRate(minRate);
        condition.setMaxRate(maxRate);
        List<Fund> result = fundService.selectFund(condition);
        return new FundList(ResultEnum.SUCCESS.getId(), result);
    }

    @RequestMapping("/getFundCount")
    @ResponseBody
    public Map<String, Object> getFundCount() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 0);
        map.put("count", fundService.count());
        return map;
    }

    @RequestMapping("/purchase")
    @ResponseBody
    public Map<String, Object> getFundCount(
            @RequestParam("fundId") int fundId,
            @RequestParam("money") int money,
            @RequestParam("bankType") String bankType,
            @RequestParam("backNo") String bankNo,
            HttpServletRequest request
    ) {
        Map<String, Object> map = Maps.newHashMap();
        String mobile = userService.getSessionMobile(request);
        if (StringUtils.isBlank(mobile)) {
            map.put("id", 1);
            return map;
        }
        if (!ResultEnum.isSuccess(userService.check(bankNo, bankType))) {
            map.put("id", 2);
            return map;
        }
        FundOrder order = new FundOrder();
        order.setBankNo(bankNo);
        order.setBankType(bankType);
        order.setMobile(mobile);
        order.setMoney(money);
        order.setFundId(fundId);
        if (fundService.purchaseFund(order)) {
            map.put("id", 0);
        } else {
            map.put("id", 3);
        }


        return map;
    }

}
