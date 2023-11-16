//package org.hmanwon.global.config;
//
//import lombok.RequiredArgsConstructor;
//import org.hmanwon.global.config.jwt.JwtProperties;
//import org.hmanwon.global.config.exception.CustomAuthenticationEntryPoint;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class AuthorizationConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.httpBasic().disable();
//        http.csrf().disable();
//        http.sessionManagement()
//            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        http
//            /*나중에 repository를 만들면 사용*/
////            .addFilterBefore(
////                new JwtAuthorizationFilter(userRepository),
////                BasicAuthenticationFilter.class
////            )
//            .exceptionHandling()
//            .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
//
//        http.authorizeRequests()
//            // /와 /home은 모두에게 허용
//            .antMatchers("/", "/home", "/signup").permitAll()
//
////            커스텀 해야 함
////            .antMatchers("/note").hasRole("USER")
////            .antMatchers("/admin").hasRole("ADMIN")
////            .antMatchers(HttpMethod.POST, "/notice").hasRole("ADMIN")
////            .antMatchers(HttpMethod.DELETE, "/notice").hasRole("ADMIN")
//
//            .anyRequest().authenticated();
//
//        // login
//        http.formLogin()
//            .loginPage("/login")
//            .defaultSuccessUrl("/")
//            .permitAll();
//
//        // logout
//        http.logout()
//            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//            .logoutSuccessUrl("/")
//            .invalidateHttpSession(true)
//            .deleteCookies(JwtProperties.COOKIE_NAME);
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        //정적 자원과 이상한 요청 차단: 잘 안 되면 둘 중 하나만 적용
//        web.ignoring()
//            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
//            .regexMatchers("^(?!/api/).*")
//            .antMatchers(HttpMethod.POST, "/api/*/users/join", "/api/*/users/login");
//    }
//
//}
