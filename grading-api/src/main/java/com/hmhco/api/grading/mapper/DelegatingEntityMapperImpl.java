package com.hmhco.api.grading.mapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.mapper.viewmapper.SingleViewMapper;
import com.hmhco.api.grading.views.AbstractView;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
@Primary
public class DelegatingEntityMapperImpl implements EntityMapper {

  private static final Logger logger = LoggerFactory.getLogger(DelegatingEntityMapperImpl.class);

  @Autowired
  private List<SingleEntityMapper<? extends AbstractEntity, ? extends AbstractView>> mappers = new ArrayList<>();

  @Autowired
  private List<SingleViewMapper<? extends AbstractView, ? extends AbstractEntity>> viewMappers = new ArrayList<>();

  @Autowired
  @Qualifier("genericEntityMapper")
  private EntityMapper genericEntityMapper;

  @Override
  public <V extends AbstractView, E extends AbstractEntity> V convert(E entity) {
    Class<? extends AbstractEntity> clazz = entity.getClass();
    logger.debug("converting object of class {}", clazz);

    SingleEntityMapper<E, V> mapper = findMapper(clazz);
    if (mapper != null) {
      return mapper.convert(entity);
    }

    logger.debug("didn't find mapper for class {}, attempting generic approach", clazz);
    return genericEntityMapper.convert(entity);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities) {
    if (CollectionUtils.isEmpty(entities)) {
      return new ArrayList<>();
    }

    Class<? extends AbstractEntity> clazz = entities.get(0).getClass();
    SingleEntityMapper<E, V> mapper = findMapper(clazz);
    if (mapper != null) {
      return mapper.convert(entities);
    }

    logger.debug("didn't find mapper for class {}, attempting generic approach", clazz);
    return genericEntityMapper.convert(entities);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> E convert(V view) {
    Class<? extends AbstractView> clazz = view.getClass();
    logger.debug("converting object of class {}", clazz);

    SingleViewMapper<V, E> mapper = findViewMapper(clazz);
    if (mapper != null) {
      return mapper.convert(view);
    }

    logger.debug("didn't find mapper for class {}, attempting generic approach", clazz);
    return genericEntityMapper.convert(view);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> V convert(E entity, Class<V> targetClazz) {
    return genericEntityMapper.convert(entity, targetClazz);
  }

  @Override
  public <V extends AbstractView, E extends AbstractEntity> List<V> convert(List<E> entities, Class<V> targetClazz) {
    return genericEntityMapper.convert(entities, targetClazz);
  }

  @SuppressWarnings("unchecked")
  private <V extends AbstractView, E extends AbstractEntity> SingleEntityMapper<E, V> findMapper(
      Class<? extends AbstractEntity> clazz) {
    return (SingleEntityMapper<E, V>) mappers.stream().filter(m -> m.supports(clazz)).findFirst().orElse(null);
  }

  @SuppressWarnings("unchecked")
  private <E extends AbstractEntity, V extends AbstractView> SingleViewMapper<V, E> findViewMapper(
      Class<? extends AbstractView> clazz) {
    return (SingleViewMapper<V, E>) viewMappers.stream().filter(m -> m.supports(clazz)).findFirst().orElse(null);
  }

}

