package org.hmanwon.domain.zzan.zzanItem.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.shop.exception.ShopException;
import org.hmanwon.domain.shop.exception.ShopExceptionCode;
import org.hmanwon.domain.zzan.zzanItem.application.ZzanService;
import org.hmanwon.domain.zzan.zzanItem.dto.ZzanItemDetailResponse;
import org.hmanwon.domain.zzan.zzanItem.dto.ZzanItemResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zzan-items")
@RequiredArgsConstructor
public class ZzanController {

    private final ZzanService zzanService;

    @GetMapping("")
    public ResponseEntity<DataBody<List<ZzanItemResponse>>> getZzanItemList(
            @RequestParam(name = "categoryId", required = false) final Long categoryId,
            @RequestParam(name = "localCode", required = false) final Long localCode
    ) {
        if (categoryId != null && categoryId > 8) {
            throw new ShopException(ShopExceptionCode.CATEGORY_BAD_REQUEST);
        }

        return ResponseDTO.ok(
            zzanService.getZzanItemList(categoryId, localCode),
                "짠처리 아이템 조회 완료"
        );
    }

    @GetMapping("/{zzanItemId}")
    public ResponseEntity<DataBody<ZzanItemDetailResponse>> getZzanItemDetail(
            @PathVariable final Long zzanItemId
    ) {
        return ResponseDTO.ok(
            zzanService.getZzanItemDetail(zzanItemId),
                "짠처리 아이템 조회 완료"
        );
    }

}
