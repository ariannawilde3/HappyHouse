package com.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication // connects to Spring Boot (makes it so we can use Java for Backend)
@EnableScheduling 

// Starts the entire Backend when run, sets up the web server (how we check health of backend), and scans for all components of the app (controllers, entities, etc.)
public class HappyHouseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(HappyHouseApplication.class, args);
        System.out.println("🏠 HappyHouse Backend is running!");
        System.out.println("🔗 API: http://localhost:5000");
        System.out.println("🏥 Health: http://localhost:5000/api/health");
    }
}
