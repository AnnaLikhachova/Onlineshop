package com.likhachova.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesReader {

    private final String path;

    public PropertiesReader(String path) {
        this.path = path;
    }

    public Properties readProperties() {
        Properties properties = new Properties();
        try(InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(path)) {
            properties.load(input);
        }
        catch(IOException e) {
            throw new RuntimeException("Can not read properties from project classpath", e);
        }
        return properties;
    }
}
