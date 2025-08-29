package com.example.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class EnvConfig {
    private static final Logger log = LoggerFactory.getLogger(EnvConfig.class);
    private static final String ENV_FILE = ".env";

    public void load(@Observes StartupEvent ev) {
        File envFile = new File(ENV_FILE);
        if (!envFile.exists()) {
            log.warn(".env file not found at {}", envFile.getAbsolutePath());
            return;
        }

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(envFile)) {
            props.load(fis);
        } catch (IOException e) {
            log.error("Failed to read .env file", e);
            return;
        }

        props.forEach((key, value) -> {
            String k = key.toString();
            String v = value.toString();

            // Don’t overwrite system/env vars
            if (System.getenv(k) == null && System.getProperty(k) == null) {
                System.setProperty(k, v);
                log.debug("Loaded env var {} from .env", k);
            }
        });

        log.info("Loaded {} entries from .env", props.size());
    }
}
