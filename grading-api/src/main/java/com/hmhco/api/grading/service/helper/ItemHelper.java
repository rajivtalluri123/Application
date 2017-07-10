package com.hmhco.api.grading.service.helper;

import com.hmhco.api.grading.dao.readwrite.ItemEntityRepository;
import com.hmhco.api.grading.dao.readwrite.QuestionEntityRepository;
import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.mapper.viewmapper.ItemsViewMapper;
import com.hmhco.api.grading.views.ItemsView;
import com.hmhco.api.grading.views.QuestionsView;
import io.hmheng.grading.utils.BeanPropertyUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

/**
 * Created by nandipatim on 5/15/17.
 */
@Component
public class ItemHelper {

  @Autowired
  private ItemsViewMapper itemsViewMapper;

  @Autowired
  private ItemEntityRepository itemEntityRepository;

  @Autowired
  private QuestionHelper questionHelper;

  @Autowired
  private QuestionEntityRepository questionEntityRepository;

  public List<ItemEntity> createorUpdateItemEntities(List<ItemsView> itemView){


    List<ItemEntity> itemEntityList = itemsViewMapper.convert(itemView);
    Set<String> itemReferences = new HashSet<>();
    List<QuestionsView> questionsViewList = new ArrayList<>();
    List<ItemEntity> itemEntitiesDB ;

    itemView.stream().forEach(view->{
      itemReferences.add(view.getItemReference());
      questionsViewList.addAll(view.getQuestions());
    });

    itemEntitiesDB = itemEntityRepository.findByItemReferenceIn(new ArrayList<>(itemReferences));
    if(itemEntityList.size() == 0){
      return itemEntitiesDB;
    }

    Set<ItemEntity> itemEntities = new HashSet<>(itemEntityList);

    List<ItemEntity> newEntries = ListUtils.subtract(new ArrayList<>(itemEntities), itemEntitiesDB);

    itemEntitiesDB.stream().forEach(item -> {
      int indexOfItem = itemEntityList.indexOf(item);
      ItemEntity itemEntity = null;
      if (indexOfItem != -1) {
        itemEntity = itemEntityList.get(indexOfItem);
      }
      String itemReference = item.getItemReference();
      if (itemEntity != null) {

        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(itemEntity, item);
        item.setItemReference(itemReference);
      }

    });

    questionEntityRepository.save(questionHelper.createorUpdateQuestions(questionsViewList));


    if(!CollectionUtils.isEmpty(newEntries)) {
      itemEntitiesDB.addAll(newEntries);
    }

    return itemEntitiesDB;
  }

}
