package com.cqs.bishe.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by cqs on 16-6-6.
 */
public class PropertyTool {
    Logger logger = LoggerFactory.getLogger(PropertyTool.class);
    private volatile Properties properties;
    private static volatile PropertyTool propertyTool;
    private static final String[] configFiles={"property/redis.properties"};

    private PropertyTool(){
        for (String file:configFiles){
            read(file);
        }
    }

    public static PropertyTool getInstance(){
        if(propertyTool == null){
            getInstanceSafe();
        }
        return propertyTool;
    }


    private static synchronized void getInstanceSafe(){
        if(propertyTool == null){
            propertyTool = new PropertyTool();
        }
    }


    public void read(String file){
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
            if(properties == null) {
                synchronized (logger) {
                    if(properties == null) {
                        properties = new Properties();
                    }
                }
            }
            properties.load(in);

        } catch (Exception e) {
            logger.error("redis property file read error");
        }
    }

    public String getValue(String key){
        return properties.getProperty(key);
    }


}
