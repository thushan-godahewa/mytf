package com.tg.qa.mytf.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The type Mytf sketch.
 */
public class MytfSketch {

    private static Log mytfLogger = LogFactory.getLog(MytfSketch.class);

    /**
     * The constant properties.
     */
    public static Properties properties = initialize();

    /**
     * The constant MYTF_PROPERTY_API_HOST_NAME.
     */
    public static final String MYTF_PROPERTY_API_HOST_NAME = properties.getProperty("mytf.api.public.url");

    /**
     * Initialize properties.
     *
     * @return the properties
     */
    public static final Properties initialize() {
        Properties properties = null;
        if(System.getenv("MYTF_ENV") != null){
            mytfLogger.info("Found the target environment to run tests and initializing it.");
            String loadedEnv = System.getenv("MYTF_ENV");
            mytfLogger.info("Value: " + loadedEnv);
            properties = loadProperties(loadedEnv);
        }
        else {
            mytfLogger.error("Not found the target environment to run the test.");
        }
        properties.putAll(System.getProperties());
        return properties;
    }

    private static final Properties loadProperties(final String fileNamePrefix) {
        Properties testProperties = null;
        try{
            final String propertyFileName = fileNamePrefix + ".properties";
            final InputStream stream = MytfSketch.class.getClassLoader().getResourceAsStream(propertyFileName);
            if(stream == null){
                throw new IOException("Could'nt find a property file with the specified name.");
            }
            else {
                mytfLogger.info("Found properties file: " + propertyFileName);
                try{
                    testProperties = new Properties();
                    testProperties.load(stream);
                }
                finally {
                    stream.close();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return testProperties;
    }
}
