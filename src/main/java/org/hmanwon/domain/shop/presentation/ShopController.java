package org.hmanwon.domain.shop.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.shop.application.ShopService;
import org.hmanwon.domain.shop.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    @GetMapping
    public List<SeoulGoodShopResponse> getAllShops() {
        return shopService.getAllShops();
    }

    @GetMapping("/categories/{categoryId}")
    public List<SeoulGoodShopResponse> getCategoryShops(
        @PathVariable final Long categoryId
    ) {
        return shopService.getCategoryShops(categoryId);
    }

    @GetMapping("/{shopId}")
    public List<SeoulGoodShopDetailResponse> getShopDetail(
        @PathVariable final Long shopId
    ) {
        return shopService.getShopDetail(shopId);
    }

    @GetMapping("/search")
    public List<SeoulGoodShopResponse> searchShopByKeyword(
        @RequestParam(name = "keyword") final String keyword
    ) {
        return shopService.searchShopByKeyword(keyword);
    }

}
