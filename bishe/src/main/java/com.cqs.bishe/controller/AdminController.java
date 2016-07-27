package com.cqs.bishe.controller;

import com.cqs.bishe.bean.Fund;
import com.cqs.bishe.result.ResultEnum;
import com.cqs.bishe.service.FundService;
import com.cqs.bishe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cqs on 16-6-13.
 */


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private FundService fundService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/addFund",produces="application/json;charset=UTF-8")
    public String addFund(@RequestParam("fundYieldRate") double fundYieldRate,
                           @RequestParam("company") String company,
                           @RequestParam("note") String note,
                           @RequestParam("status") int status,
                           HttpServletRequest request){
        if(!userService.isAdmin(request)){
            return ResultEnum.NOT_ADMIN.toJsonString();
        }
        Fund fund = new Fund.Builder().setFund_cp(company).setRate(fundYieldRate).setInfo(note).setStatus(status).end();
        ResultEnum resultEnum = fundService.insertFund(fund);
        return resultEnum.toJsonString();

    }

    @RequestMapping(value = "/startFund",produces="application/json;charset=UTF-8")
    public String startFund(@RequestParam("company") String company,
                            HttpServletRequest request){
        if(!userService.isAdmin(request)){
            return ResultEnum.NOT_ADMIN.toJsonString();
        }
        ResultEnum resultEnum = fundService.startFund(company);
        return resultEnum.toJsonString();
    }


    @RequestMapping(value = "/stopFund",produces="application/json;charset=UTF-8")
    public String stopFund(@RequestParam("company") String company,
                           HttpServletRequest request){
        if(!userService.isAdmin(request)){
            return ResultEnum.NOT_ADMIN.toJsonString();
        }
        ResultEnum resultEnum = fundService.stopFund(company);
        return resultEnum.toJsonString();
    }

}
