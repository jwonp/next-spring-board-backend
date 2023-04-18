package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Content;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepo extends JpaRepository<Content, Long> {
  Optional<Content> findByWriter(String writer);
  Optional<Content> findByBoard(String board);

  @Query(
    "SELECT A.content_id, A.title, A.content, A.board, B.author, B.views, B.likes, B.updated FROM Content A LEFT JOIN ContentMeta B where A.board =:board AND A.content_id =:id"
  )
  public Optional<Object> getContentWithContentMeta(
    @Param("id") Long id,
    @Param("board") String board
  );
}
