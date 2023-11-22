package org.hmanwon.domain.display.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.application.DisplayService;
import org.hmanwon.domain.display.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.display.dto.SeoulGoodShopResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class DisplayController {
    private final DisplayService displayService;

    @GetMapping
    public List<SeoulGoodShopResponse> getAllShops(){
        return displayService.getAllShops();
    }

    @GetMapping("/categories/{categoryId}")
    public List<SeoulGoodShopResponse> getCategoryShops(
        @PathVariable final Long categoryId
    ){
        return displayService.getCategoryShops(categoryId);
    }

    @GetMapping("/{shopId}")
    public List<SeoulGoodShopDetailResponse> getShopDetail(
        @PathVariable final Long shopId
    ){
        return displayService.getShopDetail(shopId);
    }

    @GetMapping("/search")
    public List<SeoulGoodShopResponse> searchShopByKeyword(
        @RequestParam(name = "keyword") final String keyword
    ) {
        return displayService.searchShopByKeyword(keyword);
    }

}
