package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.Like;
import com.ikiningyou.cb.model.serializable.LikeId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like, LikeId> {
  Optional<List<Like>> findByUserId(String userId);
  Optional<List<Like>> findByContentId(Long contentId);
  Optional<List<Like>> findByContentIdAndUserId(Long contentId, String userId);

  Long countByContentId(Long contentId);
}
