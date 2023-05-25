package com.ikiningyou.cb.model;

import com.ikiningyou.cb.service.BoardService;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContentTest {

  @Autowired
  private BoardService boardService;

  @Test
  void testContent() {}
}
