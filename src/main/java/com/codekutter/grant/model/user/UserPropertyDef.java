package com.codekutter.grant.model.user;

import com.codekutter.grant.model.RecordVersionedEntity;
import com.codekutter.grant.model.Validate;
import com.codekutter.grant.model.ValidationException;
import com.codekutter.grant.model.ValidationExceptions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User Property Definition - Persisted Property definition for Users.
 */
@Getter
@Setter
@Entity
@Table(name = "user_property_definitions")
public class UserPropertyDef extends RecordVersionedEntity<String> {
    /**
     * Unique Property ID
     */
    @Id
    @Column(name = "id")
    @Validate
    private String id;
    /**
     * Property name.
     */
    @Column(name = "property")
    @Validate
    private String name;
    /**
     * Is this property mandatory?
     */
    @Column(name = "required")
    private boolean required = true;
    /**
     * Is this property value encrypted.
     */
    @Column(name = "encrypted")
    private boolean encrypted = false;

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
        errors = ValidationException.checkNotEmpty("ID", id, errors);
        errors = ValidationException.checkNotEmpty("Name", name, errors);

        if (errors != null) {
            throw errors;
        }
    }
}
