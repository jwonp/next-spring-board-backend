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
import org.hibernate.annotations.ColumnDefault;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContentMeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_meta_id")
  private Long content_meta_id;

  private String title;

  @Column(insertable = false, updatable = false)
  private String author;

  private String board;

  @Column(name = "created")
  private Date created;

  @Column(name = "updated")
  private Date updated;

  @PrePersist
  void getDate() {
    this.created = new Date();
    this.updated = new Date();
  }

  @ColumnDefault("0")
  private int views;

  @ColumnDefault("0")
  private int likes;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author")
  private User user;
}
