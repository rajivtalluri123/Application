package io.hmheng.grading.rest;

import org.springframework.http.HttpHeaders;

/**
 * Created by nandipatim on 3/23/17.
 */
public interface RestTemplateService {

  void postEntity(String serviceUrl, String uri, Object domainObject, HttpHeaders httpHeaders);

  <T> T getEntity(String serviceUrl, String uri, HttpHeaders httpHeaders , Class<T> responseType);
}
