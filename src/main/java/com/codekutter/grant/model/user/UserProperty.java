package com.codekutter.grant.model.user;

import com.codekutter.grant.model.*;
import com.codekutter.grant.model.utils.CopyUtils;
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
                .checkNotEmpty("ID.roleId", id.getUserId(), errors);
        errors = ValidationException
                .checkNotNull("ID.property", id.getProperty(), errors);
        if (errors != null) {
            throw errors;
        }
    }

    /**
     * Copy the changes from the specified source entity
     * to this instance.
     * <p>
     * All properties other than the Key will be copied.
     * Copy Type:
     * Primitive - Copy
     * String - Copy
     * Enum - Copy
     * Nested Entity - Copy Recursive
     * Other Objects - Copy Reference.
     *
     * @param source - Source instance to Copy from.
     * @return - Copied Entity instance.
     * @throws CopyException
     */
    @Override
    public IEntity<UserPropertyId> copyChanges(IEntity<UserPropertyId> source)
    throws CopyException {
        if (!(source instanceof UserProperty)) {
            throw new CopyException(String.format(
                    "Cannot copy from source : Type mismatch. [type=%s]",
                    source.getClass().getCanonicalName()));
        }
        return CopyUtils.copy(this, source);
    }
}
