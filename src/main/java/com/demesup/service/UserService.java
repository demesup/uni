package com.demesup.service;


import com.demesup.api.dto.request.UserRequest;
import com.demesup.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

  List<User> findAll();

  Optional<User> findById(Long id);

  User save(User user);

  User create(UserRequest request);

  User update(User user, UserRequest request);

  void delete(User user);

  Optional<Long> findIdByEmail(String email);

  Optional<Long> findIdByPhone(String phone);

  List<User> findAllOwners();
}
