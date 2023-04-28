package com.ikiningyou.cb.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.ikiningyou.cb.model.dto.content.ContentRequest;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
import com.ikiningyou.cb.service.BoardService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
public class ContentMetaTest {

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  @Test
  void testContentMetaGeneration() {
    ContentMeta contentMeta = ContentMeta
      .builder()
      .board("Board One")
      .title("title")
      .author("joowon")
      .build();

    contentMetaRepo.save(contentMeta);
    // Optional<List<ContentMeta>> list = contentMetaRepo.findByBoard("Board One");

    // assertEquals(list.isEmpty(), false);
  }
}
