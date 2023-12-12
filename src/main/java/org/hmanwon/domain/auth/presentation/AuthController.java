package org.hmanwon.domain.auth.presentation;

import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.application.AuthService;
import org.hmanwon.domain.auth.dto.AuthLoginResponse;
import org.hmanwon.domain.auth.dto.TokenValidationResponse;
import org.hmanwon.global.common.dto.ResponseDTO;
import org.hmanwon.global.common.dto.ResponseDTO.DataBody;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ResponseEntity<DataBody<AuthLoginResponse>> KakaoLogin(@RequestParam String code) {
        return ResponseDTO.ok(authService.kakaoLogin(code), "카카오 로그인 완료");
    }

    @GetMapping("/token/validation")
    public ResponseEntity<DataBody<TokenValidationResponse>> validateToken(@RequestParam @NotBlank(message = "토큰을 입력해주세요.") String token) {
        return ResponseDTO.ok(authService.validateToken(token), "토큰의 유효성 조회 완료");
    }
}
