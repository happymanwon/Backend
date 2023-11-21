package org.hmanwon.domain.display.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.application.DisplayService;
import org.hmanwon.domain.display.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.display.dto.SeoulGoodShopResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping("/api/shops")
    public List<SeoulGoodShopResponse> getAllShops(){
        return displayService.getAllShops();
    }

    //지금은 Category가 Long이지만 나중엔 Category 클래스로 뺄거임
    @GetMapping("/api/shops/categories/{categoryId}")
    public List<SeoulGoodShopResponse> getCategoryShops(
        @PathVariable final Long categoryId
    ){
        return displayService.getCategoryShops(categoryId);
    }

    @GetMapping("/api/shops/{shopId}")
    public List<SeoulGoodShopDetailResponse> getShopDetail(
        @PathVariable final Long shopId
    ){
        return displayService.getShopDetail(shopId);
    }

}
