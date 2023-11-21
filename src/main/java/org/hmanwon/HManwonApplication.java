package org.hmanwon;

import org.hmanwon.domain.display.init.application.InitSeoulGoodShopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class HManwonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HManwonApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(InitSeoulGoodShopService seoulGoodShopService) {
        // TODO 애플리케이션 실행 전에 값들을 먼저 삽입시켜줌.
        return args -> seoulGoodShopService.loadJsonAndInsertData();
    }
}