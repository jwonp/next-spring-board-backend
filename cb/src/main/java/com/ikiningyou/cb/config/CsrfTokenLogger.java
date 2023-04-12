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
    Object o = request.getAttribute("_csrf");
    CsrfToken token = (CsrfToken) o;
    log.info("CsrfTokenLogger CSRF token : {}", token.getToken());
    chain.doFilter(request, response);
    // throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
  }
}
