package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {
  Optional<List<Comment>> findByContent(Long content);
  Long countByContent(Long content);
}
