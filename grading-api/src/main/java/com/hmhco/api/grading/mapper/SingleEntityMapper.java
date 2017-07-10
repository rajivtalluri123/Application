package com.hmhco.api.grading.mapper;


import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.views.AbstractView;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface SingleEntityMapper<E extends AbstractEntity, V extends AbstractView> {

  V convert(E entity);


  default List<V> convert(Collection<E> entities) {
    return entities.stream().map(this::convert).collect(Collectors.toList());
  }

  boolean supports(Class<? extends AbstractEntity> clazz);
}
