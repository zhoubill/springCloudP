package com.yling.hbase.utils;



import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ConfigUtil {

    private static ConfigUtil initor = new ConfigUtil();

    private static Map<String, Object> configMap = new HashMap<String, Object>();

    private ConfigUtil() {}

    /**
     * 获取内容
     * @param configFile
     * @param property
     * @return
     */
    public static String get(String configFile, String property) {
        if(!configMap.containsKey(configFile)) {
            initor.initConfig(configFile);
        }
        PropertiesConfiguration config = (PropertiesConfiguration) configMap.get(configFile);
        String value = config.getString(property);
        //TODO LOG
        return value;
    }

    /**
     * 载入配置文件，初始化后加入map
     * @param configFile
     */
    private synchronized void initConfig(String configFile) {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(configFile);
            configMap.put(configFile, config);

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }
}  
