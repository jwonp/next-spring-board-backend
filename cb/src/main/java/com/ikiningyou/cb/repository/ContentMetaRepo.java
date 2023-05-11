package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.content.ContentMetaResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ContentMetaRepo extends JpaRepository<ContentMeta, Long> {
  Optional<ContentMeta> findByContentMetaId(Long contentMetaId);

  @Query(
    value = "SELECT " +
    "A.content_meta_id as contentMetaId, " +
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
    "WHERE A.board = :board",
    countQuery = "SELECT count(*) FROM ContentMeta WHERE board = :board",
    nativeQuery = true
  )
  Optional<List<ContentMetaResponse>> findByBoard(
    @Param("board") String board,
    Pageable pageable
  );

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
    "ORDER BY A.likes DESC " +
    "LIMIT 10"
  )
  Optional<List<ContentMetaResponse>> getMostLikedContentMeta();

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
    "ORDER BY A.views DESC " +
    "LIMIT 10"
  )
  Optional<List<ContentMetaResponse>> getMostViewedContentMeta();

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
    "ORDER BY A.contentMetaId DESC " +
    "LIMIT 10"
  )
  Optional<List<ContentMetaResponse>> getRecentlyContentMeta();

  @Query(
    value = "SELECT " +
    "A.content_meta_id as contentMetaId, " +
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
    "WHERE A.board = :board " +
    "AND A.title LIKE %:title%",
    countQuery = "SELECT count(*) FROM ContentMeta WHERE board = :board AND LIKE %:title%",
    nativeQuery = true
  )
  Optional<List<ContentMetaResponse>> findByBoardAndTitleContaining(
    @Param("board") String board,
    @Param("title") String title,
    Pageable pageable
  );

  Long countByBoard(String board);
  Long countByBoardAndTitleContaining(String board, String title);

  void deleteByContentMetaId(Long contentMetaId);
}
