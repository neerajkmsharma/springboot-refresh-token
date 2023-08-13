package com.avinya.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avinya.application.entity.City;
import com.avinya.application.service.CacheService;

@RestController
public class CacheController {

  @Autowired
  CacheService cacheService;

  @GetMapping("/city")
  public City getCityByName(@RequestParam("name") final String name) {
    return cacheService.getZipCode(name);

  }

  @GetMapping("/cache")
  public Cache getCacheDetails(@RequestParam("name") final String name) {
    return cacheService.getCacheByName(name);
  }
}
