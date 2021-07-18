package com.tangorabox.reactiverest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReactiveRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveRestApplication.class, args);
    }
}
