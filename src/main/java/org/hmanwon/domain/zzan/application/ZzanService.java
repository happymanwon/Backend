package org.hmanwon.domain.zzan.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.shop.dao.LocalCodeRepository;
import org.hmanwon.domain.shop.entity.LocalCode;
import org.hmanwon.domain.shop.exception.ShopException;
import org.hmanwon.domain.shop.exception.ShopExceptionCode;
import org.hmanwon.domain.zzan.dao.ZzanItemRepository;
import org.hmanwon.domain.zzan.dto.ZzanItemDetailResponse;
import org.hmanwon.domain.zzan.dto.ZzanItemResponse;
import org.hmanwon.domain.zzan.exception.ZzanException;
import org.hmanwon.domain.zzan.exception.ZzanExceptionCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZzanService {

    private final ZzanItemRepository zzanItemRepository;
    private final LocalCodeRepository localCodeRepository;

    @Transactional(readOnly = true)
    public List<ZzanItemResponse> getZzanItemList(Long categoryId, Long localCode) {

        if (localCode == null && categoryId == null) {
            //디폴트 localCode = 1 (종로구)
            return zzanItemRepository.findZzanItemsByLocalCode(getLocalCode(1L))
                    .stream()
                    .map(ZzanItemResponse::fromEntity)
                    .collect(Collectors.toList());
        }
        if (localCode == null) {
            return zzanItemRepository.findZzanItemsByCategory(categoryId)
                    .stream()
                    .map(ZzanItemResponse::fromEntity)
                    .collect(Collectors.toList());
        }
        if (categoryId == null) {
            return zzanItemRepository.findZzanItemsByLocalCode(getLocalCode(localCode))
                    .stream()
                    .map(ZzanItemResponse::fromEntity)
                    .collect(Collectors.toList());
        }

        return zzanItemRepository.findZzanItemsByLocalCodeAndCategory(getLocalCode(localCode), categoryId)
                .stream()
                .map(ZzanItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ZzanItemDetailResponse getZzanItemDetail(Long zzanItemId) {
        return ZzanItemDetailResponse.fromEntity(zzanItemRepository.findById(zzanItemId)
                .orElseThrow(() -> new ZzanException(ZzanExceptionCode.NOT_FOUND_ITEM)));
    }

    private LocalCode getLocalCode(Long code) {
        return localCodeRepository.findById(code)
                .orElseThrow(() -> new ShopException(ShopExceptionCode.LOCAL_CODE_BAD_REQUEST));
    }
}
