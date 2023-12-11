package org.hmanwon.domain.auth.application;

import static org.hmanwon.domain.auth.exception.AuthExceptionCode.INVALID_TOKEN;
import static org.hmanwon.domain.auth.exception.AuthExceptionCode.KAKAO_NETWORK_ERROR;
import static org.hmanwon.domain.auth.exception.AuthExceptionCode.UNAUTHORIZED_TOKEN;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.jsonwebtoken.JwtException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.hmanwon.domain.auth.dto.AuthLoginResponse;
import org.hmanwon.domain.auth.exception.AuthException;
import org.hmanwon.domain.member.application.MemberService;
import org.hmanwon.domain.member.dto.response.MemberResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    @Value("${kakao.restApiKey}")
    String clientId;
    String redirectURL = "http://localhost:8080/api/auth/login/kakao";

    public AuthLoginResponse kakaoLogin(String kakaoAuthorizationCode) {
        //인가코드 받아서 카카오 토큰 발급
        String kakaoAccessToken = getKakaoToken(kakaoAuthorizationCode);

        //카카오 토큰으로 유저 정보 조회
        HashMap<String, Object> memberInfoFromKakao = getMemberInfoFromKakao(kakaoAccessToken);

        String kakaoEmail = String.valueOf(memberInfoFromKakao.get("email"));

        MemberResponse memberResponse;
        if (!memberService.checkEmail(kakaoEmail)) { //회원가입 대상
            memberResponse = memberService.addMember(memberInfoFromKakao);
        } else {
            memberResponse = memberService.findByEmail(kakaoEmail);
        }

        //서비스 토큰 발급
        String serviceAccessToken = jwtProvider.createAccessToken(kakaoEmail,
            memberResponse.memberId());
        String serviceRefreshToken = jwtProvider.createRefreshToken();

        return AuthLoginResponse.builder()
            .accessToken(serviceAccessToken)
            .refreshToken(serviceRefreshToken)
            .memberId(memberResponse.memberId())
            .nickname(memberResponse.nickname())
            .build();
    }

    public String getKakaoToken(String code) {

        String accessToken = "";
        String refreshToken = "";
        String requestUrl = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + clientId);
            sb.append("&redirect_uri=" + redirectURL);
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            accessToken = element.getAsJsonObject().get("access_token").getAsString();
            refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("accessToken = " + accessToken);
            System.out.println("refreshToken = " + refreshToken);

            br.close();
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthException(KAKAO_NETWORK_ERROR);
        }

        return accessToken;
    }


    public HashMap<String, Object> getMemberInfoFromKakao(String accessToken) {
        HashMap<String, Object> memberInfo = new HashMap<String, Object>();

        String requestURL = "https://kapi.kakao.com/v2/user/me";

        try {
            URL url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("responseBody = " + result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account")
                .getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

            memberInfo.put("nickname", nickname);
            memberInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
            throw new AuthException(KAKAO_NETWORK_ERROR);
        }
        return memberInfo;
    }

    public Long findMemberIdFromTokenWithValidation(String headerToken) {
        //예외처리 필요
        String token = headerToken;
        Long memberId = -1L;
        if (StringUtils.hasText(headerToken)) {
            throw new AuthException(INVALID_TOKEN);
        }
        if (headerToken.startsWith("Bearer")) {
            token = headerToken.substring(7);
        }
        try {
            if (jwtProvider.validateToken(token)) {
                memberId = jwtProvider.getMemberIdFromToken(token);
            }
        } catch (JwtException e) {
            throw new AuthException(UNAUTHORIZED_TOKEN);
        }
        return memberId;
    }
}
