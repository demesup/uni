package com.kpop.demesup.service.impl;

import com.kpop.demesup.api.dto.request.UserRequest;
import com.kpop.demesup.domain.User;
import com.kpop.demesup.repository.UserRepository;
import com.kpop.demesup.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  PasswordEncoder passwordEncoder;

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

  @Override
  public User create(UserRequest request) {
    return save(User.create(request, passwordEncoder.encode(request.getPassword())));
  }

  @Override
  public User update(User user, UserRequest request) {
    user.update(request);
    return save(user);
  }

  @Override
  public void delete(User user) {
    user.delete();
    save(user);
  }

  @Override
  public Optional<Long> findIdByEmail(String email) {
    return userRepository.findIdByEmail(email);
  }

  @Override
  public Optional<Long> findIdByPhone(String phone) {
    return userRepository.findIdByPhone(phone);
  }

  @Override
  public List<User> findAllOwners() {
    return userRepository.findAllOwners();
  }

}
