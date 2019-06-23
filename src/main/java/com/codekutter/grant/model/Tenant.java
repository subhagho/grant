package com.codekutter.grant.model;

import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tenant")
public class Tenant extends RecordVersionedEntity<String> {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    @Validate
    private String name;
    @Column(name = "name")
    private String description;
    @AttributeOverrides({
                                @AttributeOverride(name = "updatedBy",
                                                   column = @Column(
                                                           name = "updated_by")),
                                @AttributeOverride(name = "updateTimestamp",
                                                   column = @Column(
                                                           name = "updated_timestamp"))

                        })
    @Validate
    private UpdateInfo updateInfo;

    /**
     * Get the unique Key for this entity.
     *
     * @return - Entity Key.
     */
    @Override
    public String getKey() {
        return id;
    }

    /**
     * Compare the entity key with the key specified.
     *
     * @param key - Target Key.
     * @return - Comparision.
     */
    @Override
    public int compare(String key) {
        return id.compareTo(key);
    }

    /**
     * Validate this entity instance.
     *
     * @throws ValidationExceptions - On validation failure will throw exception.
     */
    @Override
    public void validate() throws ValidationExceptions {
        ValidationExceptions errors = null;
        try {
            ValidationUtils.validate(getClass(), this);
        } catch (ValidationExceptions e) {
            errors = e;
        }
        if (errors != null) {
            throw errors;
        }
    }
}
