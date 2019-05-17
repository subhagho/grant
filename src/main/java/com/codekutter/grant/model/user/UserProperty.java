package com.codekutter.grant.model.user;

import com.codekutter.grant.model.RecordVersionedEntity;
import com.codekutter.grant.model.ValidationException;
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
    @EmbeddedId
    private UserPropertyId id;
    private String value;
    private boolean encrypted = false;

    @Override
    public UserPropertyId getKey() {
        return id;
    }

    @Override
    public int compare(UserPropertyId key) {
        int ret = Integer.MIN_VALUE;
        if (key != null) {
            ret = id.compareTo(key);
        }
        return ret;
    }

    @Override
    public void validate() throws ValidationException {
        ValidationException.checkNotNull("ID", id);
        // ValidationException.checkNotEmpty("ID.userId", id.);
    }
}
