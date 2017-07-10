package com.hmhco.api.grading.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Inheritance;
import java.util.List;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * Created by srikanthk on 4/25/17.
 */

@Data
@NoArgsConstructor
@Entity
@Table(name ="item")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ItemEntity extends AuditableEntity {

    private static final long serialVersionUID = -7554092157124319108L;

    @Id
    @Column(name = "item_reference")
    private String itemReference ;

    @Column(name = "organisation_id")
    private Integer organizationId;

    @Column(name = "item_pool_id")
    private String itemPoolId;

    @Column(name = "type")
    private String type;


    @Override
    public String toString() {
        return "ItemEntity [itemReference=" + itemReference + ", organizationId=" + organizationId
                + ", itemPoolId=" + itemPoolId + ", type=" + type+ "]";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemEntity that = (ItemEntity) o;

        return !(itemReference != null ? !itemReference.equals(that.itemReference) : that.itemReference != null);

    }

    @Override
    public int hashCode() {
        return itemReference != null ? itemReference.hashCode() : 0;
    }
}
