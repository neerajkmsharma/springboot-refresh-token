package com.avinya.application.service;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.avinya.application.model.Product;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {

  private List<Product> productList = null;

  private final Random randomGen = new Random();

  @PostConstruct
  public void loadProductsFromDB() {

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

}
