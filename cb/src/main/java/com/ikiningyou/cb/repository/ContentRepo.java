package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Content;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentRepo extends JpaRepository<Content, Long> {
  Optional<Content> findByWriter(String writer);
  Optional<Content> findById(Long id);
  Optional<Content> findByBoard(String board);
}
