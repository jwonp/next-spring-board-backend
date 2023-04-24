package com.ikiningyou.cb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
  private Long comment_id;

  private Long content;

  private String comment;

  @Column(insertable = false, updatable = false)
  private String writer;

  @Column(name = "created")
  private Date created;

  @Column(name = "updated")
  private Date updated;

  @PrePersist
  void getDate() {
    this.created = new Date();
    this.updated = new Date();
  }
}
