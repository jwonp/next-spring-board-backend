package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.dto.ContentFullData;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepo extends JpaRepository<Content, Long> {
  Optional<Content> findByWriter(String writer);
  Optional<Content> findByBoard(String board);

  @Query(
    "SELECT " +
    "A.contentId as id, " +
    "A.title as title, " +
    "A.content as content, " +
    "A.board as board, " +
    "C.name as author, " +
    "B.views as views, " +
    "B.likes as likes, " +
    "B.updated as updated " +
    "FROM Content A " +
    "LEFT JOIN ContentMeta B " +
    "ON A.contentId = B.contentMetaId " +
    "LEFT JOIN User C " +
    "ON A.writer = C.id " +
    "where A.contentId = :id"
  )
  public Optional<ContentFullData> getContentWithContentMeta(
    @Param("id") Long id
  );
}
