package com.cqs.bishe.service;

import com.cqs.bishe.bean.User;
import com.cqs.bishe.dao.mysqldb.UserMapper;
import com.cqs.bishe.result.ResultEnum;
import com.cqs.bishe.service.inter.ServiceCheckInterface;
import com.cqs.bishe.tool.MD5Tool;
import com.cqs.bishe.tool.RedisTool;
import com.google.common.base.Strings;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Created by cqs on 16-6-5.
 */


@Service
public class UserService extends CheckService implements ServiceCheckInterface {

    private static final String userNameCookie = "userMobile";

    @Autowired
    private UserMapper userMapper;

    public ResultEnum register(User user){
        ResultEnum result = check(user);
        if(ResultEnum.isSuccess(result)){
            if(! userMapper.insert(user) ){
                result = ResultEnum.INSERT_ERROR;
            } else if(user.getType() == User.UserType.normal.getId()){
                result = ResultEnum.REGISTER_SUCCESSS;
            } else {
                result = ResultEnum.ADMIN_REGISTER_SUCCESSS;
            }
        }
        return result;
    }


    public boolean checkPwd(String mobile,String pwd){
        if(StringUtils.isBlank(mobile) || StringUtils.isBlank(pwd)){
            return false;
        }
        User user = userMapper.getByMobile(mobile);
        if( null != user && null != user.getPwd() && user.getPwd().equals(MD5Tool.encodePwdByMd5(pwd))){
            return true;
        }
        return false;
    }

    private ResultEnum check(User user){
        return check(user.getCertNo(),user.getMobile(),user.getName(),user.getPwd());
    }

    public boolean isAdmin(String mobile){
        if(StringUtils.isBlank(mobile)){
            return false;
        }
        User user = userMapper.getByMobile(mobile);
        if(user != null && user.getType() == User.UserType.admin.getId()){
            return true;
        }
        return false;
    }

    public String getSessionMobile(HttpServletRequest request){
        return RedisTool.getInstance().getValue(userNameCookie+request.getRemoteAddr());
    }

    public boolean isAdmin(HttpServletRequest request){
        String mobile = getSessionMobile(request);
        return isAdmin(mobile);
    }

    public void setSessionMobile(HttpServletRequest request,HttpServletResponse response,String mobile){
        RedisTool.getInstance().put(userNameCookie+request.getRemoteAddr(),mobile,600);
    }
}
