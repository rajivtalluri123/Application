package com.hmhco.api.grading.mapper.viewmapper;


import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.views.AbstractView;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface SingleViewMapper<V extends AbstractView, E extends AbstractEntity> {

  E convert(V view);

  default List<E> convert(Collection<V> views) {
    return views.stream().map(this::convert).collect(Collectors.toList());
  }

  boolean supports(Class<? extends AbstractView> clazz);
}