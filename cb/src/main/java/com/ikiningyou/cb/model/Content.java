package com.ikiningyou.cb.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Content {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "content_id")
  private Long content_id;

  private String title;
  private String content;
  private String writer;
  private String board;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinTable(
    name = "CONTENT_WITH_DATES",
    joinColumns = @JoinColumn(name = "content_id"),
    inverseJoinColumns = @JoinColumn(name = "content_meta_id")
  )
  private ContentMeta contentMeta;
}
