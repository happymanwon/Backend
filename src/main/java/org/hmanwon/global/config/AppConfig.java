package org.hmanwon.global.config;

import org.hmanwon.domain.display.init.application.InitSeoulGoodShopService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CommandLineRunner loadData(InitSeoulGoodShopService seoulGoodShopService) {
        // TODO 애플리케이션 실행 전에 값들을 먼저 삽입시켜줌.
        return args -> seoulGoodShopService.loadJsonAndInsertData();
    }

}
