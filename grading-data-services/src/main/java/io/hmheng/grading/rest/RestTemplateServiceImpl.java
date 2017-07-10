package io.hmheng.grading.rest;

import io.hmheng.grading.authorization.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by nandipatim on 3/23/17.
 */
@Slf4j
@Service
public class RestTemplateServiceImpl implements RestTemplateService {

  private RestTemplate rest;

  @Autowired
  public RestTemplateServiceImpl(AuthorizationService authorizationService){
    rest = new RestTemplate();
  }

  @Override
  public void postEntity(String serviceUrl, String uri, Object domainObject , HttpHeaders httpHeaders) {

    if(domainObject != null) {
      log.info("Data RestTemplateServiceImpl {}",domainObject.toString());
    }

    HttpEntity<Object> httpEntity = new HttpEntity<>(domainObject , httpHeaders);
    String url = String.format("%s"+uri, serviceUrl);
    ResponseEntity<String> response = rest.postForEntity(url , httpEntity , String.class);

    if(!response.getStatusCode().is2xxSuccessful()){
      log.error("Unable process for url {} , server returned {}", url , response.getStatusCode());
      log.error("Response body: ", response.getBody());
      throw new RuntimeException(String.format("Unable process for url %d , server returned %d ",
          url,response.getStatusCode()));

    }
  }

  @Override
  public <T> T getEntity(String serviceUrl, String uri, HttpHeaders httpHeaders , Class<T> responseType) {

    HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
    String url = String.format("%s"+uri, serviceUrl);
    ResponseEntity<T> response = rest.exchange(url , HttpMethod.GET , httpEntity, responseType);

    if(!response.getStatusCode().is2xxSuccessful()){
      log.error("Unable process for url {} , server returned {}", url , response.getStatusCode());
      log.error("Response body: ", response.getBody());
      throw new RuntimeException(String.format("Unable process for url %d , server returned %d ",
          url,response.getStatusCode()));

    }

    return response.getBody();
  }
}
