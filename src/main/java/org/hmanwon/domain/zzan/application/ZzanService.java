package org.hmanwon.domain.zzan.application;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

    @Transactional(readOnly = true)
    public List<ZzanItemResponse> getZzanItemList(Long localCode) {
        return zzanItemRepository.findZzanItemsByLocalCode(localCode)
                .stream()
                .map(ZzanItemResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ZzanItemDetailResponse getZzanItemDetail(Long zzanItemId) {
        return ZzanItemDetailResponse.fromEntity(zzanItemRepository.findById(zzanItemId)
                .orElseThrow(() -> new ZzanException(ZzanExceptionCode.NOT_FOUND_ITEM)));
    }
}
