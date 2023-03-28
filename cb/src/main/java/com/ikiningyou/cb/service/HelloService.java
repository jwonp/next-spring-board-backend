package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Token;
import com.ikiningyou.cb.repository.JpaTokenRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

  @Autowired
  private JpaTokenRepo jpaTokenRepo;

  public String getTokenByIdentifier(String identifier) {
    Optional<Token> token = jpaTokenRepo.findTokenByIdentifier(identifier);
    if (token.isPresent()) {
      return token.get().getToken();
    }
    return null;
  }
}
