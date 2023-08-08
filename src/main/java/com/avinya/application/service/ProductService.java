package com.avinya.application.service;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.avinya.application.entity.UserInfo;
import com.avinya.application.model.Product;
import com.avinya.application.repository.UserInfoRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

  List<Product> productList = null;

  @Autowired
  private UserInfoRepository repository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @PostConstruct
  public void loadProductsFromDB() {

    final Random randomGen = new Random();

    productList = IntStream.rangeClosed(1, 100)
      .mapToObj(
        productId -> new Product(productId, "product " + productId, randomGen.nextInt(10), randomGen.nextInt(5000)))
      .toList();

  }

  public List<Product> getProducts() {
    return productList;
  }

  public Product getProduct(final int id) {
    return productList.stream()
      .filter(product -> product.productId() == id)
      .findAny()
      .orElseThrow(() -> new RuntimeException("product " + id + " not found"));
  }

  public String addUser(final UserInfo userInfo) {
    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
    repository.save(userInfo);
    return "user added to system ";
  }
}
