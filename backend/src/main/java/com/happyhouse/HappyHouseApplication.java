package com.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.logging.Logger;

@SpringBootApplication // connects to Spring Boot (makes it so we can use Java for Backend)
@EnableScheduling 

// Starts the entire Backend when run, sets up the web server (how we check health of backend), and scans for all components of the app (controllers, entities, etc.)
public class HappyHouseApplication {

    private static final Logger logger = Logger.getLogger(HappyHouseApplication.class.getName());
    
    public static void main(String[] args) {
        SpringApplication.run(HappyHouseApplication.class, args);
        logger.info("🏠 HappyHouse Backend is running!");
        logger.info("🔗 API: http://localhost:5000");
        logger.info("🏥 Health: http://localhost:5000/api/health");
    }
}
