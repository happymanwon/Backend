package org.hmanwon.domain.shop.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.shop.dao.LocalCodeRepository;
import org.hmanwon.domain.shop.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.shop.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.shop.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.shop.entity.LocalCode;
import org.hmanwon.domain.shop.entity.SeoulGoodShop;
import org.hmanwon.domain.shop.exception.ShopException;
import org.hmanwon.domain.shop.exception.ShopExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final SeoulGoodShopRepository seoulGoodShopRepository;
    private final LocalCodeRepository localCodeRepository;

    @Transactional(readOnly = true)
    public List<SeoulGoodShopResponse> getAllShops() {
        return seoulGoodShopRepository.findAll()
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopDetailResponse> getShopDetail(Long shopId) {
        return seoulGoodShopRepository.findById(shopId)
            .stream().map(SeoulGoodShopDetailResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> searchShopByKeyword(String keyword) {
        return seoulGoodShopRepository.findByNameContains(keyword)
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByCategoryAndLocalCode(
            Long categoryId, Long localCode
    ) {
        if (localCode == null && categoryId == null) {
            return getShopsByCategory(1L); //default
        }
        if (localCode == null) {
            return getShopsByCategory(categoryId);
        }
        if (categoryId == null) {
            return getShopsByLocalCode(localCode);
        }

        LocalCode location = getLocalCode(localCode);
        return location.getSeoulGoodShopList()
                .stream().filter(shop -> shop.getCategory() == categoryId)
                .map(SeoulGoodShopResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByCategory(Long categoryId) {
        return seoulGoodShopRepository.findByCategory(categoryId)
                .stream().map(SeoulGoodShopResponse::fromEntity)
                .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getShopsByLocalCode(Long localCode) {
        LocalCode location = getLocalCode(localCode);
        return location.getSeoulGoodShopList()
                .stream()
                .map(SeoulGoodShopResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private LocalCode getLocalCode(Long localCode) {
        return localCodeRepository.findById(localCode).orElseThrow(
                () -> new ShopException(ShopExceptionCode.LOCAL_CODE_BAD_REQUEST)
        );
    }
}
