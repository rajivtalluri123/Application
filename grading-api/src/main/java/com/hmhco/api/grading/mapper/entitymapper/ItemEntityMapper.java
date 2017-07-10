package com.hmhco.api.grading.mapper.entitymapper;

import com.hmhco.api.grading.entities.AbstractEntity;
import com.hmhco.api.grading.entities.ItemEntity;
import com.hmhco.api.grading.mapper.SingleEntityMapper;
import com.hmhco.api.grading.views.ItemsView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Created by srikanthk on 4/30/17.
 */
@Component
public class ItemEntityMapper implements SingleEntityMapper<ItemEntity, ItemsView> {

    @Override
    public ItemsView convert(ItemEntity entity) {
        ItemsView itemsView = new ItemsView();
        BeanUtils.copyProperties(entity,itemsView);
        return itemsView;
    }

    @Override
    public boolean supports(Class<? extends AbstractEntity> clazz) {
       return ItemEntity.class.equals(clazz);
    }
}
