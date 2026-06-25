package com.nfctag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NfctagApplication {

    public static void main(String[] args) {
        SpringApplication.run(NfctagApplication.class, args);
    }
}
