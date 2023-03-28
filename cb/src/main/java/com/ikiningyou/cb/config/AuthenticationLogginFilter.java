package com.ikiningyou.cb.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationLogginFilter implements Filter {

  @Override
  public void doFilter(
    ServletRequest request,
    ServletResponse response,
    FilterChain chain
  ) throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String requestId = httpRequest.getHeader("Request-Id");
    log.info("request id is {}", requestId);
    chain.doFilter(request, response);
    // throw new UnsupportedOperationException("Unimplemented method 'doFilter'");
  }
}
