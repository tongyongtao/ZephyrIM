package com.tongyt.config;

import com.tongyt.protocol.Serializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author tongyt
 * @date 2023-05-08
 */
@Slf4j
public class Config {

    static Properties properties;

    static {
        try (InputStream resourceAsStream = Config.class.getResourceAsStream("/applicant.properties")) {
            properties = new Properties();
            if (resourceAsStream != null) {
                properties.load(resourceAsStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort() {
        String port = properties.getProperty("server.port");
        if (port == null) {
            return 8088;
        } else {
            return Integer.parseInt(port);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm() {
        String algorithm = properties.getProperty("serializer.algorithm");
        if (algorithm == null) {
            return Serializer.Algorithm.JDK;
        } else {
            return Serializer.Algorithm.valueOf(algorithm);
        }
    }

}
