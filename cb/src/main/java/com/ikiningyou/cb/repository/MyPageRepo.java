package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MyPageRepo extends JpaRepository<ContentMeta, Long> {
  @Query(
    "SELECT " +
    "A.contentMetaId as contentMetaId, " +
    "A.title as title, " +
    "B.name as author, " +
    "A.board as board, " +
    "A.created as created, " +
    "A.updated as updated, " +
    "A.views as views, " +
    "A.likes as likes " +
    "FROM ContentMeta A " +
    "LEFT JOIN User B " +
    "ON A.author = B.id " +
    "LEFT JOIN Like C " +
    "ON A.contentMetaId = C.contentId " +
    "WHERE C.userId = :userId " +
    "ORDER BY A.contentMetaId DESC " +
    "LIMIT 10"
  )
  Optional<List<ContentMetaResponse>> getLikedContents(
    @Param("userId") String userId,
    Pageable pageable
  );
  //   @Query(
  //     "SELECT " +
  //     "A.contentMetaId as contentMetaId, " +
  //     "A.title as title, " +
  //     "B.name as author, " +
  //     "A.board as board, " +
  //     "A.created as created, " +
  //     "A.updated as updated, " +
  //     "A.views as views, " +
  //     "A.likes as likes " +
  //     "FROM ContentMeta A " +
  //     "LEFT JOIN User B " +
  //     "ON A.author = B.id " +
  //     "LEFT JOIN Like C " +
  //     "ON A.contentMetaId = C.contentId" +
  //     "WHERE C.userId = :userId" +
  //     "ORDER BY A.contentMetaId DESC " +
  //     "LIMIT 10"
  //   )
  //   Optional<List<ContentMetaResponse>> getWrittenContents(
  //     @Param("userId") String userId,
  //     Pageable pageable
  //   );
}
