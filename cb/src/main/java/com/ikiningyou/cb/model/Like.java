package com.ikiningyou.cb.model;

import com.ikiningyou.cb.model.serializable.LikeId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(LikeId.class)
@Getter
@Table(name = "Likes")
public class Like {

  @Id
  @Column(name = "content_id")
  private Long contentId;

  @Id
  @Column(name = "user_id")
  private String userId;
}
