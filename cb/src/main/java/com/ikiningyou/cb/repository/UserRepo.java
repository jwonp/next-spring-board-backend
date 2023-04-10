package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
  Optional<User> findByIdAndProvider(String id, String provider);
  // Optional<User> findByName
}
