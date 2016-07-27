package com.cqs.bishe.service;

import com.cqs.bishe.service.inter.MobileCodeInterface;
import com.cqs.bishe.tool.ChuanglanSMS;
import com.cqs.bishe.tool.MD5Tool;
import com.cqs.bishe.tool.MobileCodeTool;
import com.cqs.bishe.tool.RedisTool;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by cqs on 16-6-6.
 */

@Service
public class MobileCodeService implements MobileCodeInterface {

    @Value("${key_time}")
    private int keyTime;


    @Override
    public String makeMobileCode(String mobile) {
        String value = RedisTool.getInstance().getCode(mobile);
        if(StringUtils.isBlank(value)){
            value = MobileCodeTool.makeMobileCode(mobile);
            RedisTool.getInstance().put(mobile,value,keyTime);
        }
        return value;
    }

    @Override
    public boolean equalVCode(String mobile, String vCode) {
        String value = RedisTool.getInstance().getCode(mobile);
        if(null != value && value.equals(vCode)){
            return true;
        }
        return false;
    }

    public void sendMessage(String mobile,String content){
        //发送短信
        CloseableHttpResponse response = ChuanglanSMS.getInstance().sendMessage(mobile, content);
        if(response != null && response.getStatusLine().getStatusCode()==200){
            try {
                logger.info("send message:{}",EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
