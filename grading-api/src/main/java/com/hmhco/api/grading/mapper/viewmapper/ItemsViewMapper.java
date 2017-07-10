package com.hmhco.api.grading.mapper.viewmapper;

import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.views.AbstractView;
import com.hmhco.api.grading.views.ItemsView;

import io.hmheng.grading.utils.BeanPropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class ItemsViewMapper implements SingleViewMapper<ItemsView, ItemEntity> {

    @Autowired
    private QuestionsViewMapper questionsViewMapper;

    @Override
    public ItemEntity convert(ItemsView view) {
        ItemEntity itemEntity = new ItemEntity();
        BeanPropertyUtils.copyPropertiesWithOnlyPopulated(view, itemEntity);

   return itemEntity;
    }



    @Override
    public boolean supports(Class<? extends AbstractView> clazz) {

        return ItemsView.class.equals(clazz);
    }


}
