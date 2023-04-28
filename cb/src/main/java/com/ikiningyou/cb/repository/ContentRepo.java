package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.dto.content.ContentFullData;
import com.ikiningyou.cb.model.dto.content.ContentShortResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentRepo extends JpaRepository<Content, Long> {
  Optional<Content> findByAuthor(String author);
  Optional<Content> findByBoard(String board);
  Long countByContentIdAndAuthor(Long contentId, String author);

  // @Query("SELECT contentId, title, content FROM Content WHERE contentId = :id")
  Optional<ContentShortResponse> findByContentId(Long contentId);

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
    "ON A.author = C.id " +
    "where A.contentId = :id"
  )
  Optional<ContentFullData> getContentWithContentMeta(@Param("id") Long id);

  void deleteByContentId(Long contentId);
}
