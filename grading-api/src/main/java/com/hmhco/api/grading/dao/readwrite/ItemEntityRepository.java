package com.hmhco.api.grading.dao.readwrite;

import com.hmhco.api.grading.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by srikanthk on 4/26/17.
 */
public interface ItemEntityRepository  extends JpaRepository<ItemEntity, String>{


    ItemEntity findByItemReference(String itemReference);

    List<ItemEntity> findByItemReferenceIn(List<String> itemReference);
}
