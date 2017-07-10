package com.hmhco.api.grading.mapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.views.AbstractView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class GenericEntityMapperImpl implements EntityMapper {

  private Map<Class<? extends AbstractEntity>, Class<? extends AbstractView>> entityToViewMapping = new HashMap<>();
  private Map<Class<? extends AbstractView>, Class<? extends AbstractEntity>> viewToEntityMapping = new HashMap<>();

  @Override
  public <V extends AbstractView, E extends AbstractEntity> V convert(E entity) {

    Class<? extends AbstractEntity> clazz = entity.getClass();
    Class<V> viewClazz = findViewClass(clazz);

    return createViewInstance(viewClazz, entity);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities) {

    if (CollectionUtils.isEmpty(entities)) {
      return new ArrayList<>();
    }

    Class<? extends AbstractEntity> clazz = entities.get(0).getClass();
    Class<V> viewClazz = findViewClass(clazz);

    return entities.stream().map(e -> createViewInstance(viewClazz, e)).collect(Collectors.toList());
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> E convert(V view) {

    Class<? extends AbstractView> clazz = view.getClass();
    Class<E> entityClazz = findEntityClass(clazz);

    return createEntityInstance(entityClazz, view);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> V convert(E entity, Class<V> targetClazz) {
    return createViewInstance(targetClazz, entity);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities, Class<V> targetClazz) {
    return entities.stream().map(e -> createViewInstance(targetClazz, e)).collect(Collectors.toList());
  }

  public void addClassMapping(Class<? extends AbstractEntity> entityClazz, Class<? extends AbstractView> viewClazz) {
    entityToViewMapping.put(entityClazz, viewClazz);
    viewToEntityMapping.put(viewClazz, entityClazz);
  }

  @SuppressWarnings("unchecked")
  private <V extends AbstractView, E extends AbstractEntity> Class<V> findViewClass(Class<E> entityClazz) {

    Class<V> viewClazz = (Class<V>) entityToViewMapping.get(entityClazz);

    if (viewClazz == null) {
      throw new IllegalArgumentException("no mapping configuration was found for class " + entityClazz);
    }
    return viewClazz;
  }

  @SuppressWarnings("unchecked")
  private <V extends AbstractView, E extends AbstractEntity> Class<E> findEntityClass(Class<V> viewClazz) {

    Class<E> entityClazz = (Class<E>) viewToEntityMapping.get(viewClazz);

    if (entityClazz == null) {
      throw new IllegalArgumentException("no mapping configuration was found for class " + viewClazz);
    }
    return entityClazz;
  }

  private <V extends AbstractView, E extends AbstractEntity> V createViewInstance(Class<V> viewClazz, E entity) {
    V view = BeanUtils.instantiate(viewClazz);
    BeanPropertyUtils.copyPropertiesWithOnlyPopulated(entity, view);
    return view;
  }

  private <V extends AbstractView, E extends AbstractEntity> E createEntityInstance(Class<E> entityClazz, V view) {
    E entity = BeanUtils.instantiate(entityClazz);
    BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, entity);
    return entity;
  }
}
