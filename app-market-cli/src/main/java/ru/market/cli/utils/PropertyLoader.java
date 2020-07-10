package ru.market.cli.utils;

import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class PropertyLoader {
    private static final String PROPERTIES_FILE_NAME = "config.properties";

    private static Properties properties = new Properties();

    private PropertyLoader(){}

    static {
        try {
            String path = System.getProperty("user.dir") + File.separator + PROPERTIES_FILE_NAME;
            properties.load(new FileInputStream(new File(path)));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getProperty(String name, String defaultValue){
        String property = properties.getProperty(name);
        return StringUtils.isEmpty(property) ? defaultValue : property;
    }
}
