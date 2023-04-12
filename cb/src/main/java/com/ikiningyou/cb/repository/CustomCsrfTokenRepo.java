package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;

@Slf4j
public class CustomCsrfTokenRepo implements CsrfTokenRepository {

  @Autowired
  private JpaTokenRepo jpaTokenRepo;

  @Override
  public CsrfToken generateToken(HttpServletRequest request) {
    log.info("generateToken");
    String uuid = UUID.randomUUID().toString();
    return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", uuid);
  }

  @Override
  public void saveToken(
    CsrfToken csrfToken,
    HttpServletRequest request,
    HttpServletResponse response
  ) {
    log.info("saveToken");

    String identifier = request.getHeader("X-IDENTIFIER");

    log.info("identifier is {}", identifier);
    Optional<Token> existingToken = jpaTokenRepo.findTokenByIdentifier(
      identifier
    );
    if (existingToken.isPresent()) {
      Token token = existingToken.get();
      token.setToken(csrfToken.getToken());
    } else {
      Token token = new Token();
      token.setToken(csrfToken.getToken());
      token.setIdentifier(identifier);
      jpaTokenRepo.save(token);
    }
  }

  @Override
  public CsrfToken loadToken(HttpServletRequest request) {
    log.info("loadToken");
    String identifier = request.getHeader("X-IDENTIFIER");
    Optional<Token> existingToken = jpaTokenRepo.findTokenByIdentifier(
      identifier
    );

    if (existingToken.isPresent()) {
      Token token = existingToken.get();
      log.info(" IDer is {} ", identifier);
      return new DefaultCsrfToken("X-CSRF-TOKEN", "_csrf", token.getToken());
    }
    return null;
  }
}
