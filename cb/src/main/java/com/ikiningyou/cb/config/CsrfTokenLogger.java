package com.ikiningyou.cb.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;

// import org.springframework.security.web.server.csrf.CsrfToken;

@Slf4j
public class CsrfTokenLogger implements Filter {

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    try {
      log.info("X-IDENTIFIER {}", request.getAttribute("X-IDENTIFIER"));
    } catch (Exception e) {
      log.info("NO X-IDENTIFIER");
    }

    Object o = request.getAttribute("_csrf");
    request.getRemoteHost();
    CsrfToken token = (CsrfToken) o;
    try {
      log.info("CsrfTokenLogger CSRF token : {}", token.getToken());
    } catch (Exception e) {
      log.info("No token");
    }
    chain.doFilter(request, response);
    // throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
  }
}
