package com.cqs.bishe.tool;


import java.awt.image.VolatileImage;
import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by cqs on 16-6-6.
 */
public class SMSTool {
    private Logger logger = LoggerFactory.getLogger(SMSTool.class);
    private static volatile SMSTool smsTool;

    private SMSTool() {

    }

    public static SMSTool getInstance() {
        if (smsTool == null) {
            return getInstanceSafe();
        }
        return smsTool;
    }

    private static synchronized SMSTool getInstanceSafe() {
        if (smsTool == null) {
            smsTool = new SMSTool();
        }
        return smsTool;
    }


    public int sendMessage(String mobile, String msg) {

        if (isNotMobile(mobile) || StringUtils.isBlank(msg)) {
            return 0;
        }
        if(true) {
            return 0;
        }

        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn");
        post.addRequestHeader("Content-Type",
                "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
        NameValuePair[] data = {new NameValuePair("Uid", "cqs_2012"),// 注册的用户名
                new NameValuePair("Key", "6c212f49757972c1fb33"),// 注册成功后，登录网站后得到的密钥
                new NameValuePair("smsMob", mobile),// 手机号码
                new NameValuePair("smsText", msg)};// 短信内容
        post.setRequestBody(data);

        try {
            client.executeMethod(post);
            String result = new String(post.getResponseBodyAsString().getBytes(
                    "gbk"));
            return Integer.parseInt(result);
        } catch (Exception e) {
            logger.error("send msg fail, mobile;{}, msg:{}", mobile, msg);
        } finally {
            return 0;
        }

    }

    public boolean isMobile(String mobile) {
        if (StringUtils.isBlank(mobile) || mobile.length() != 11)
            return false;
        return true;
    }

    public boolean isNotMobile(String mobile) {
        return !isMobile(mobile);
    }


    public String initMsg(String msg) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("【cqs JiJin】欢迎注册wxj基金,你的验证码是:");
        stringBuffer.append(msg);
        stringBuffer.append(",还请注意保密，禁止泄露。");
        return stringBuffer.toString();
    }

}
