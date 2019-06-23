package com.codekutter.grant.model.roles;

import com.codekutter.grant.model.*;
import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "role_type")
public abstract class AbstractRole extends StatefulEntity<RoleId> implements
                                                                  TenantEntity {
    @EmbeddedId
    @Validate
    private RoleId id;
    @Column(name = "role_name")
    @Validate
    private String name;
    @Column(name = "description")
    @Validate
    private String description;
    @AttributeOverrides({
                                @AttributeOverride(name = "updatedBy",
                                                   column = @Column(
                                                           name = "updated_by")),
                                @AttributeOverride(name = "updateTimestamp",
                                                   column = @Column(
                                                           name = "updated_timestamp"))

                        })
    private UpdateInfo updateInfo;

    /**
     * Validate the derived entity.
     *
     * @param errors - Errors handle.
     */
    @Override
    public ValidationExceptions validate(ValidationExceptions errors) {
        try {
            ValidationUtils.validate(getClass(), this);
            id.validate();
            updateInfo.validate();
        } catch (ValidationExceptions e) {
            if (errors == null)
                errors = e;
            else {
                errors.addAll(e.getErrors());
            }
        }
        return errors;
    }

    /**
     * Get the unique Key for this entity.
     *
     * @return - Entity Key.
     */
    @Override
    public RoleId getKey() {
        return id;
    }

    /**
     * Compare the entity key with the key specified.
     *
     * @param key - Target Key.
     * @return - Comparision.
     */
    @Override
    public int compare(RoleId key) {
        return id.compareTo(key);
    }

    /**
     * Get the tenant this Entity definition belongs to.
     *
     * @return - Owner Tenant.
     */
    @Override
    public Tenant getTenant() {
        return id.getTenant();
    }
}
