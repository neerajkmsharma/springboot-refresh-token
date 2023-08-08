package com.avinya.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avinya.application.entity.UserInfo;
import com.avinya.application.model.Product;
import com.avinya.application.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

  @Autowired
  private ProductService service;

  @PostMapping("/signUp")
  public String addNewUser(@RequestBody final UserInfo userInfo) {
    return service.addUser(userInfo);
  }

  @GetMapping("/all")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public List<Product> getAllTheProducts() {
    return service.getProducts();
  }

  @GetMapping("/{id}")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public Product getProductById(@PathVariable final int id) {
    return service.getProduct(id);
  }

}
