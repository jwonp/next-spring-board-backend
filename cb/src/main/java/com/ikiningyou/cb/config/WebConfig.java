package com.ikiningyou.cb.config;

import com.ikiningyou.cb.repository.CustomCsrfTokenRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class WebConfig {

  @Bean
  public CsrfTokenRepository customTokenRepository() {
    return new CustomCsrfTokenRepo();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    return http
      // .addFilterBefore(
      //   new AuthenticationLogginFilter(),
      //   BasicAuthenticationFilter.class
      // )
      .addFilterAfter(new CsrfTokenLogger(), CsrfFilter.class)
      .httpBasic(c -> c.disable())
      .csrf(c -> {
        c.csrfTokenRepository(customTokenRepository());
      })
      .cors(c -> {
        CorsConfigurationSource source = request -> {
          CorsConfiguration config = new CorsConfiguration();
          config.setAllowedOrigins(List.of("http://localhost:3000"));
          config.setAllowedMethods(
            List.of("GET", "POST", "PUT", "PATCH", "DELETE")
          );
          config.setAllowedHeaders(List.of("X-IDENTIFIER"));
          return config;
        };
        c.configurationSource(source);
      })
      .authorizeHttpRequests(req ->
        req
          .requestMatchers(HttpMethod.POST, "/api")
          .permitAll()
          .anyRequest()
          .permitAll()
      )
      .build();
  }
  // @Bean
  // HttpSessionCsrfTokenRepository sessionCsrfRepository() {
  //   HttpSessionCsrfTokenRepository csrfRepository = new HttpSessionCsrfTokenRepository();

  //   // HTTP 헤더에서 토큰을 인덱싱하는 문자열 설정
  //   csrfRepository.setHeaderName("X-CSRF-TOKEN");
  //   // URL 파라미터에서 토큰에 대응되는 변수 설정
  //   csrfRepository.setParameterName("_csrf");
  //   // 세션에서 토큰을 인덱싱 하는 문자열을 설정. 기본값이 무척 길어서 오버라이딩 하는 게 좋아요.
  //   // 기본값: "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"
  //   csrfRepository.setSessionAttributeName("CSRF_TOKEN");

  //   return csrfRepository;
  // }
  // @Bean
  //   CookieCsrfTokenRepository cookieCsrfRepository() {
  //     CookieCsrfTokenRepository csrfRepository = new CookieCsrfTokenRepository();

  //     csrfRepository.setCookieHttpOnly(false);
  //     csrfRepository.setHeaderName("X-CSRF-TOKEN");
  //     csrfRepository.setParameterName("_csrf");
  //     csrfRepository.setCookieName("XSRF-TOKEN");
  //     //csrfRepository.setCookiePath("..."); // 기본값: request.getContextPath()

  //     return csrfRepository;
  //   }
}
