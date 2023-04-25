package com.ikiningyou.cb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContentMeta {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_meta_id")
  private Long contentMetaId;

  private String title;

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
}
