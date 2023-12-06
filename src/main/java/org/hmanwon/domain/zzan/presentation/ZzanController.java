package org.hmanwon.domain.zzan.presentation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.zzan.application.ZzanService;
import org.hmanwon.domain.zzan.dto.ZzanItemDetailResponse;
import org.hmanwon.domain.zzan.dto.ZzanItemResponse;
import org.hmanwon.domain.zzan.exception.ZzanException;
import org.hmanwon.domain.zzan.exception.ZzanExceptionCode;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zzan")
@RequiredArgsConstructor
public class ZzanController {

    private final ZzanService zzanService;

    @GetMapping("")
    public ResponseEntity<DataBody<List<ZzanItemResponse>>> getZzanItemList(
            @RequestParam(name = "localCode", required = false) final Long localCode
    ) {
        return ResponseDTO.ok(
                        zzanService.getZzanItemList(localCode),
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
