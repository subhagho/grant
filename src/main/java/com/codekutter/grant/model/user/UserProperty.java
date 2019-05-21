package com.codekutter.grant.model.user;

import com.codekutter.grant.model.RecordVersionedEntity;
import com.codekutter.grant.model.Validate;
import com.codekutter.grant.model.ValidationException;
import com.codekutter.grant.model.ValidationExceptions;
import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * User Property type - All attributes for Users are
 * stored as property values.
 */
@Getter
@Setter
@Entity
@Table(name = "user_properties")
public class UserProperty extends RecordVersionedEntity<UserPropertyId> {
    /**
     * Composite User Property ID
     */
    @EmbeddedId
    @Validate
    private UserPropertyId id;
    /**
     * Property Value
     */
    @Validate
    private String value;
    /**
     * Is the value encrypted?
     */
    private boolean encrypted = false;

    /**
     * Get the Unique Key for this User Property.
     *
     * @return - Property Key
     */
    @Override
    public UserPropertyId getKey() {
        return id;
    }

    /**
     * Compare key with the target key.
     *
     * @param key - Target Key.
     * @return - Comparison.
     */
    @Override
    public int compare(UserPropertyId key) {
        int ret = Integer.MIN_VALUE;
        if (key != null) {
            ret = id.compareTo(key);
        }
        return ret;
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

        errors = ValidationException
                .checkNotEmpty("ID.userId", id.getUserId(), errors);
        errors = ValidationException
                .checkNotNull("ID.property", id.getProperty(), errors);
        if (errors != null) {
            throw errors;
        }
    }
}
