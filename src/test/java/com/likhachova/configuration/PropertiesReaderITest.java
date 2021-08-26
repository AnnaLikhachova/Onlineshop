package com.likhachova.configuration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesReaderITest {

    @Test
    @DisplayName("Read properties from file")
    public void test_readProperties() {
        String pathToProperties ="application.properties";
        PropertiesReader propertiesReader = new PropertiesReader(pathToProperties);
        Properties properties = propertiesReader.readProperties();
        assertEquals("b9a44a4dce4830",properties.getProperty("user"));
        assertEquals("4d091d98",properties.getProperty("password"));
        assertEquals("jdbc:mysql://us-cdbr-east-04.cleardb.com/heroku_92a6936952c08a5?password=4d091d98&reconnect=true&user=b9a44a4dce4830",properties.getProperty("url"));
    }

    @Test
    @DisplayName("Read properties from not existing source")
    public void test_readNotExistingProperties() {
        Assertions.assertThrows(NullPointerException.class, () -> {
            String pathToProperties ="properties.xml";
            PropertiesReader propertiesReader = new PropertiesReader(pathToProperties);
            propertiesReader.readProperties();
        });
    }
}
