package com.cqs.bishe.controller;

import com.cqs.bishe.bean.User;
import com.cqs.bishe.result.ResultEnum;
import com.cqs.bishe.service.MobileCodeService;
import com.cqs.bishe.service.UserService;
import com.cqs.bishe.tool.SMSTool;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by cqs on 16-6-5.
 */

@Controller()
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MobileCodeService mobileCodeService;

    @RequestMapping(value = "/login")
    @ResponseBody
    public Map<String,String> login(@RequestParam("mobile") String mobile,
                        @RequestParam("pwd") String pwd,
                        HttpServletRequest request,
                        HttpServletResponse response){
        Map map = Maps.newHashMap();
        boolean result = userService.checkPwd(mobile,pwd);
        if(result == false){
            map.put("id",ResultEnum.PWD_CHECK_ERROR.getId());
            return map;
        }
        userService.setSessionMobile(request,response,mobile);
        map.put("id",ResultEnum.SUCCESS.getId());
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/register")
    public Map register(@RequestParam(name = "mobile") String mobile,
                           @RequestParam(name = "name") String name,
                           @RequestParam(name = "cert_no") String certNo,
                           @RequestParam(name = "type") int type,
                           @RequestParam(name = "pwd") String pwd,
                           @RequestParam(name = "vCode") String vCode,
                           HttpServletRequest request,
                           HttpServletResponse response) {

        Map<String,Integer> resultMap = Maps.newHashMap();
        if(! mobileCodeService.equalVCode(mobile,vCode)){
            resultMap.put("id", ResultEnum.VCODE_CHECK_ERROR.getId());
            return resultMap;
        }
        User user =new User.Builder().start().setName(name).setMobile(mobile).setCertNo(certNo).setPwd(pwd).setType(type).end();
        ResultEnum result = userService.register(user);
        if(ResultEnum.isLoginSuccess(result)){
            userService.setSessionMobile(request,response,mobile);
        }
        logger.info(result.toJsonString());
        resultMap.put("id", result.getId());
        return resultMap;
    }

    @ResponseBody
    @RequestMapping(value = "/getCode")
    public Map<String,String> register(@RequestParam(name = "mobile",required = true) String mobile) {
        Map map = Maps.newHashMap();
        ResultEnum result = ResultEnum.SUCCESS;
        if(StringUtils.isBlank(mobile)){
            result = ResultEnum.CHECK_ERROR;
        } else {
            String value = mobileCodeService.makeMobileCode(mobile);
         mobileCodeService.sendMessage(mobile,SMSTool.getInstance().initMsg(value));
        }
        map.put("id",result.getId());
        return map;
    }


}
