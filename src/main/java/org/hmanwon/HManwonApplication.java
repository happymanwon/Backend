package org.hmanwon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class HManwonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HManwonApplication.class, args);
    }
}