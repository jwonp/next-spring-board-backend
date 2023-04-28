package com.ikiningyou.cb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "comment_id")
  private Long commentId;

  @Column(name = "content_id", nullable = false)
  private Long contentId;

  @Column(name = "comment", nullable = false)
  private String comment;

  @Column(name = "writer", nullable = false)
  private String writer;

  @Column(name = "created", nullable = false)
  private Date created;

  @Column(name = "updated", nullable = false)
  private Date updated;

  @PrePersist
  void getDate() {
    this.created = new Date();
    this.updated = new Date();
  }
}
