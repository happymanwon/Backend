package org.hmanwon.domain.display.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.display.dto.SeoulGoodShopDetailResponse;
import org.hmanwon.domain.display.dto.SeoulGoodShopResponse;
import org.hmanwon.domain.display.init.dto.SeoulGoodShopDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisplayService {
    private final SeoulGoodShopRepository seoulGoodShopRepository;

    @Transactional(readOnly = true)
    public List<SeoulGoodShopResponse> getAllStores() {
        return seoulGoodShopRepository.findAll()
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopResponse> getCategoryStores(Long category) {
        return seoulGoodShopRepository.findByCategory(category)
            .stream().map(SeoulGoodShopResponse::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopDetailResponse> getStoreDetail(Long storeId) {
        return seoulGoodShopRepository.findById(storeId)
            .stream().map(SeoulGoodShopDetailResponse::fromEntity)
            .collect(Collectors.toList());
    }
}
