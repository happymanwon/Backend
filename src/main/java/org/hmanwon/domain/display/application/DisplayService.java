package org.hmanwon.domain.display.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.display.dao.SeoulGoodShopRepository;
import org.hmanwon.domain.display.init.dto.SeoulGoodShopDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DisplayService {
    private final SeoulGoodShopRepository seoulGoodShopRepository;

    @Transactional(readOnly = true)
    public List<SeoulGoodShopDTO> getAllStores() {
        return seoulGoodShopRepository.findAll()
            .stream().map(SeoulGoodShopDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public List<SeoulGoodShopDTO> getCategoryStores(Long category) {
        return seoulGoodShopRepository.findByShopCategoryId(category)
            .stream().map(SeoulGoodShopDTO::fromEntity)
            .collect(Collectors.toList());
    }
}
