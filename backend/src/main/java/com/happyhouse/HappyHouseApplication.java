package com.happyhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HappyHouseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(HappyHouseApplication.class, args);
        System.out.println("🏠 HappyHouse Backend is running!");
        System.out.println("🔗 API: http://localhost:5000");
        System.out.println("🏥 Health: http://localhost:5000/api/health");
    }
}
