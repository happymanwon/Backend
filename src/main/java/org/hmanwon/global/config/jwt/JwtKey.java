//package org.hmanwon.global.config.jwt;
//
//import io.jsonwebtoken.security.Keys;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Map;
//public class JwtKey {
//    private static final Map<String, String> SECRET_KEY_SET = Map.of(
//        "key1", "SpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFunSpringSecurityJWTPracticeProjectIsSoGoodAndThisProjectIsSoFun",
//        "key2", "GoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurityGoodSpringSecurityNiceSpringSecurity",
//        "key3", "HelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurityHelloSpringSecurity"
//    );
//
//    public static Key getKey(String kid) {
//        String key = SECRET_KEY_SET.getOrDefault(kid, null);
//        if (key == null)
//            return null;
//        return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
//    }
//}
