package com.idle.createdmissionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = {"com.idle.createdmissionservice", "com.idle.commonservice"})
@EnableFeignClients
@EnableJpaAuditing
public class CreatedMissionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreatedMissionServiceApplication.class, args);
    }

}
