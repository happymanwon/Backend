//package org.hmanwon.global.config.jwt;
//
//import java.io.IOException;
//import java.util.Arrays;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Cookie;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.hmanwon.global.config.util.JwtTokenUtils;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//@Slf4j
//@RequiredArgsConstructor
//public class JwtTokenFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(
//        HttpServletRequest request,
//        HttpServletResponse response,
//        FilterChain filterChain
//    ) throws ServletException, IOException {
//        String token = null;
//        try {
//            // cookie 에서 JWT token을 가져옵니다.
//            token = Arrays.stream(request.getCookies())
//                .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
//                .map(Cookie::getValue)
//                .orElse(null);
//        } catch (Exception ignored) {
//        }
//        if (token != null) {
//            try {
//                Authentication authentication = getUsernamePasswordAuthenticationToken(token);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } catch (Exception e) {
//                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
//                cookie.setMaxAge(0);
//                response.addCookie(cookie);
//            }
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private Authentication getUsernamePasswordAuthenticationToken(String token) {
//        String userName = JwtTokenUtils.getUsername(token);
//        if (userName != null) {
//            // 유저를 유저명으로 찾습니다.
//            return new UsernamePasswordAuthenticationToken(
//                null, // 유저로 바꿉니다
//                null,
//                null // user.getAuthorities()
//            );
//        }
//        return null;
//    }
//}
