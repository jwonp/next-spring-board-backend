package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.ContentMeta;
import com.ikiningyou.cb.model.Like;
import com.ikiningyou.cb.repository.ContentMetaRepo;
import com.ikiningyou.cb.repository.LikeRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeService {

  @Autowired
  private ContentMetaRepo contentMetaRepo;

  @Autowired
  private LikeRepo likeRepo;

  @Transactional
  public boolean addLikeByContentIdAndUser(Long contentId, String userId) {
    Like like = Like.builder().contentId(contentId).userId(userId).build();
    Like[] rowExisted = getLikeByContentAndUser(contentId, userId);
    if (rowExisted.length > 0) {
      return false;
    }

    try {
      likeRepo.save(like);
      Optional<ContentMeta> rowContentMeta = contentMetaRepo.findByContentMetaId(
        contentId
      );
      if (rowContentMeta.isPresent()) {
        ContentMeta contentMeta = rowContentMeta.get();
        contentMeta.setLikes(contentMeta.getLikes() + 1);
      }
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }
    return true;
  }

  @Transactional
  public boolean deleteLikeByContentIdAndUser(Long contentId, String userId) {
    try {
      likeRepo.deleteByContentIdAndUserId(contentId, userId);
      Optional<ContentMeta> rowContentMeta = contentMetaRepo.findByContentMetaId(
        contentId
      );
      if (rowContentMeta.isPresent()) {
        ContentMeta contentMeta = rowContentMeta.get();
        contentMeta.setLikes(contentMeta.getLikes() - 1);
      }
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }

    return true;
  }

  public Like[] getLikeByContent(Long content) {
    Optional<List<Like>> rowLikeList = likeRepo.findByContentId(content);
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public Like[] getLikeByUser(String user) {
    Optional<List<Like>> rowLikeList = likeRepo.findByUserId(user);
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public Like[] getLikeByContentAndUser(Long content, String user) {
    Optional<List<Like>> rowLikeList = likeRepo.findByContentIdAndUserId(
      content,
      user
    );
    if (rowLikeList.isPresent() == false) {
      return null;
    }
    Like[] likeList = rowLikeList
      .get()
      .toArray(new Like[rowLikeList.get().size()]);
    return likeList;
  }

  public int getLikeCountByContentId(Long contentId) {
    Long rowCount = likeRepo.countByContentId(contentId);
    int intCount = Long
      .valueOf(Optional.ofNullable(rowCount).orElse(0L))
      .intValue();
    return intCount;
  }

  public boolean isLikedByContentIdAndUser(Long contentId, String user) {
    Long rowCount = likeRepo.countByContentIdAndUserId(contentId, user);
    int intCount = Long
      .valueOf(Optional.ofNullable(rowCount).orElse(0L))
      .intValue();
    if (intCount > 0) {
      return true;
    }
    return false;
  }
}
