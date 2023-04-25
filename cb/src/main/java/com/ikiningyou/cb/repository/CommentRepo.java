package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Comment;
import com.ikiningyou.cb.model.dto.CommentResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepo extends JpaRepository<Comment, Long> {
  @Query(
    "SELECT A.commentId as commentId, " +
    "A.content as contentId, " +
    "A.comment as comment, " +
    "B.name as writer, " +
    "A.created as created, " +
    "A.updated as updated " +
    "FROM Comment A " +
    "LEFT JOIN User B " +
    "ON A.writer = B.id " +
    "WHERE A.content = :content"
  )
  Optional<List<CommentResponse>> getCommentByContent(
    @Param("content") Long content
  );

  Long countByContent(Long content);
}
