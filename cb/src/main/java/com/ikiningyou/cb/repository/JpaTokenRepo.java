package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTokenRepo extends JpaRepository<Token, Integer> {
  Optional<Token> findTokenByIdentifier(String identifier);
}
