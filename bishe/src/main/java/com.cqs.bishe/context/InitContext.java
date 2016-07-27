package com.cqs.bishe.context;

import com.cqs.bishe.tool.MD5Tool;
import com.cqs.bishe.tool.PropertyTool;
import com.cqs.bishe.tool.RedisTool;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;

/**
 * Created by cqs on 16-6-6.
 */
public class InitContext implements ApplicationListener<ContextRefreshedEvent> {
    private String packageName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null){
            //root application context 没有parent，他就是老大.
            PropertyTool.getInstance();
            RedisTool.getInstance();
        }
    }
}
