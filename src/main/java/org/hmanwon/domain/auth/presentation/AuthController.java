package org.hmanwon.domain.auth.presentation;

import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.auth.dto.AuthLoginResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ResponseEntity<DataBody<AuthLoginResponse>> KakaoLogin(@RequestParam String code) {
        return ResponseDTO.ok(authService.kakaoLogin(code), "카카오 로그인 완료");
    }
}
