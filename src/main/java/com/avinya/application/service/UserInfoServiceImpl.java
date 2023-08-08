package com.avinya.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avinya.application.entity.UserInfo;
import com.avinya.application.repository.UserInfoRepository;

@Service
public class UserInfoServiceImpl implements UserInfoService {

  @Autowired
  private UserInfoRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public String addUser(final UserInfo userInfo) {
    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    repository.save(userInfo);
    return "User is added to system ";
  }
}
