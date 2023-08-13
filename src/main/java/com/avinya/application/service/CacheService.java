package com.avinya.application.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.avinya.application.entity.City;

@Service
public class CacheService {

  @Autowired
  private CacheManager cacheManager;

  private final Map<String, City> cityZipCodeMap;

  CacheService() {
    cityZipCodeMap = new HashMap<>();
    cityZipCodeMap.put("Hiteccity", City.builder()
      .cityName("Hiteccity")
      .zipCode("500081")
      .build());
    cityZipCodeMap.put("SrNagar", City.builder()
      .cityName("SrNagar")
      .zipCode("500038")
      .build());
    cityZipCodeMap.put("Miyapur", City.builder()
      .cityName("Miyapur")
      .zipCode("500049")
      .build());
    cityZipCodeMap.put("Waverock", City.builder()
      .cityName("Waverock")
      .zipCode("500095")
      .build());
    cityZipCodeMap.put("GachiWali", City.builder()
      .cityName("GachiWali")
      .zipCode("500032")
      .build());
  }

  @Cacheable(
    value = "city-zip-cache")
  public City getZipCode(final String cityName) {

    System.out.println("getZipCode Method Called");
    return cityZipCodeMap.get(cityName);
  }

  public Cache getCacheByName(final String cacheName) {
    return cacheManager.getCache(cacheName);
  }

  @CachePut(
    value = "city-zip-cache",
    key = "#city.cityName")
  public City addCity(final City city) {
    cityZipCodeMap.put(city.getCityName(), city);
    return city;

  }

  @CacheEvict(
    value = "city-zip-cache",
    allEntries = true)
  public String removeCache() {
    return "Cache removed Successfully";
  }

  @Caching(
    cacheable = { @Cacheable(
      cacheNames = "city-zip-cache",
      key = "#city.cityName",
      condition = "!#forceRefresh") },
    put = { @CachePut(
      cacheNames = "jobs",
      key = "#id",
      condition = "#forceRefresh") })
  public String getJob(final String id, final boolean forceRefresh) {
    /*
     * In case forceRefresh is true, Spring cache won't be activated because of the
     * condition condition = "!#forceRefresh". Hence, the cache value won't be
     * updated.
     * 
     * You need to explicitly tell Spring to update the cache value with @CachePut
     * in case forceRefresh is true:
     */
    return "";
  }

}
