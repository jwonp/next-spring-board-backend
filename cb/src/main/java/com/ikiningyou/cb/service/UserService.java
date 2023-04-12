package com.ikiningyou.cb.service;

import com.ikiningyou.cb.model.User;
import com.ikiningyou.cb.model.dto.UserRequest;
import com.ikiningyou.cb.repository.UserRepo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public boolean isUserRegisted(String id, String provider) {
    Optional<User> user = userRepo.findByIdAndProvider(id, provider);
    return user.isPresent();
  }

  /**
   * 로그인 한 유저의 정보를 DB에 조회해서 새로운 유저라면 유저 정보를 저장
   * @param userRequest 받아온 유저 데이터
   * @return 저장에 성공했으면 true, 이미 존재하는 유저였으면 false;
   */
  public boolean addUser(UserRequest userRequest) {
    Optional<User> existedUser = userRepo.findByIdAndProvider(
      userRequest.getId(),
      userRequest.getProvider()
    );
    if (existedUser.isPresent()) return false;

    User user = User
      .builder()
      .id(userRequest.getId())
      .name(userRequest.getName())
      .email(userRequest.getEmail())
      .image(userRequest.getImage())
      .provider(userRequest.getProvider())
      .build();

    try {
      userRepo.save(user);
    } catch (IllegalArgumentException e) {
      e.getStackTrace();
      return false;
    } catch (OptimisticLockingFailureException e) {
      e.getStackTrace();
      return false;
    }

    return true;
  }
}
