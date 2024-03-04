package com.zb.tablereservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ZbTableReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZbTableReservationApplication.class, args);
    }

}
