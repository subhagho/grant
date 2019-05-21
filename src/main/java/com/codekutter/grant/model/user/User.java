package com.codekutter.grant.model.user;

import com.codekutter.grant.model.RecordVersionedEntity;
import com.codekutter.grant.model.Validate;
import com.codekutter.grant.model.ValidationException;
import com.codekutter.grant.model.ValidationExceptions;
import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

/**
 * User Definition Class - Implements a security principal.
 */
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends RecordVersionedEntity<String> implements Principal {
    @Column(name = "user_id")
    @Validate
    private String userId;
    @Column(name = "user_name")
    @Validate
    private String userName;
    @Column(name = "state")
    @Validate
    private EUserState state = EUserState.Unknown;
    private Set<UserProperty> properties;

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public boolean implies(Subject subject) {
        if (subject != null) {
            Set<Principal> principals = subject.getPrincipals();
            if (principals != null && !principals.isEmpty()) {
                for (Principal principal : principals) {
                    if (principal instanceof User) {
                        User user = (User) principal;
                        if (userId.compareTo(user.userId) == 0) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Get the unique Key for this entity.
     *
     * @return - Entity Key.
     */
    @Override
    public String getKey() {
        return userId;
    }

    /**
     * Compare the entity key with the key specified.
     *
     * @param key - Target Key.
     * @return - Comparision.
     */
    @Override
    public int compare(String key) {
        return userId.compareTo(key);
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
        if (state == EUserState.Unknown) {
            errors = ValidationExceptions.add(new ValidationException(
                    String.format("Invalid Property Value : State = %s",
                                  state.name())), errors);
        }
        if (properties != null && !properties.isEmpty()) {
            for (UserProperty property : properties) {
                try {
                    property.validate();
                } catch (ValidationExceptions ve) {
                    errors = ValidationExceptions.copy(ve, errors);
                }
            }
        }
        if (errors != null) {
            throw errors;
        }
    }
}
