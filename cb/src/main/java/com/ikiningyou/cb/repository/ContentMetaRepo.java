package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.ContentMeta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentMetaRepo extends JpaRepository<ContentMeta, Long> {
  Optional<List<ContentMeta>> findByBoard(String board, Pageable pageable);
  Optional<List<ContentMeta>> findByBoardAndTitleContaining(
    String board,
    String title,
    Pageable pageable
  );
  Long countByBoard(String board);
  Long countByBoardAndTitleContaining(String board, String title);
}
