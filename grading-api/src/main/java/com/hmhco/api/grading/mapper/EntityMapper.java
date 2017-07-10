package com.hmhco.api.grading.mapper;


import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.views.AbstractView;
import java.util.List;

public interface EntityMapper {

  <V extends AbstractView, E extends AbstractEntity> V convert(E entity);

  <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities);

  <V extends AbstractView, E extends AbstractEntity> E convert(V view);

  <V extends AbstractView, E extends AbstractEntity> V convert(E entity, Class<V> targetClazz);

  <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities, Class<V> targetClazz);
}
