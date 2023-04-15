package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.Content;
import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.dto.ContentRequest;
import com.ikiningyou.cb.model.dto.SearchResponse;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.ContentRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

  @Autowired
  private ContentRepo contentRepo;

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  public Optional<List<ContentMeta>> getContentListByBoard(
    String board,
    Pageable pageable
  ) {
    Optional<List<ContentMeta>> contentMetaList = contentMetaRepo.findByBoard(
      board,
      pageable
    );
    return contentMetaList;
  }

  public Boolean addContent(ContentRequest contentRequest) {
    Content content = Content
      .builder()
      .title(contentRequest.getTitle())
      .content(contentRequest.getContent())
      .writer(contentRequest.getWriter())
      .board(contentRequest.getBoard())
      .build();
    ContentMeta contentMeta = ContentMeta
      .builder()
      .board(contentRequest.getBoard())
      .title(contentRequest.getTitle())
      .author(contentRequest.getWriter())
      .build();
    try {
      contentRepo.save(content);
      contentMetaRepo.save(contentMeta);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
  }

  public Long getSizeByBoard(String board) {
    Long size = contentMetaRepo.countByBoard(board);
    return size;
  }

  public List<SearchResponse> searchByBoard(String board, String search) {
    Optional<List<ContentMeta>> searchResult = contentMetaRepo.findByBoardAndTitleContaining(
      board,
      search
    );
    List<ContentMeta> searchList;
    List<SearchResponse> responseList = new ArrayList<SearchResponse>();
    if (searchResult.isPresent()) {
      searchList = searchResult.get();
      searchList.forEach(item ->
        responseList.add(
          SearchResponse
            .builder()
            .id(item.getId())
            .title(item.getTitle())
            .build()
        )
      );

      return responseList;
    }
    return null;
  }
}
