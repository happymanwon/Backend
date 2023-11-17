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

    @GetMapping("/api/stores")
    public List<SeoulGoodShopResponse> getAllStores(){
        return displayService.getAllStores();
    }

    //지금은 Category가 Long이지만 나중엔 Category 클래스로 뺄거임
    @GetMapping("/api/stores/categories/{categoryId}")
    public List<SeoulGoodShopResponse> getCategoryStores(
        @PathVariable final Long categoryId
    ){
        return displayService.getCategoryStores(categoryId);
    }

    @GetMapping("/api/stores/{storeId}")
    public List<SeoulGoodShopDetailResponse> getStoreDetail(
        @PathVariable final Long storeId
    ){
        return displayService.getStoreDetail(storeId);
    }

}
