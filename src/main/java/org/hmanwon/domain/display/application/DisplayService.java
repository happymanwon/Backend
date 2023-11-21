package org.hmanwon.domain.display.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.display.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.display.dto.SeoulGoodShopResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisplayService {
    private final SeoulGoodShopRepository seoulGoodShopRepository;

    @Transactional(readOnly = true)
    public List<SeoulGoodShopResponse> getAllShops() {
        return seoulGoodShopRepository.findAll()
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getCategoryShops(Long category) {
        return seoulGoodShopRepository.findByCategory(category)
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopDetailResponse> getShopDetail(Long shopId) {
        return seoulGoodShopRepository.findById(shopId)
            .stream().map(SeoulGoodShopDetailResponse::fromEntity)
            .collect(Collectors.toList());
    }
}
