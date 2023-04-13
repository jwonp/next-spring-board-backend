package com.ikiningyou.cb.repository;

import com.ikiningyou.cb.model.ContentMeta;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContentMetaRepo extends JpaRepository<ContentMeta, Long> {
  @Query("select * from contnet")
  List<ContentMeta> findByBoard(Pageable pageable);
}
