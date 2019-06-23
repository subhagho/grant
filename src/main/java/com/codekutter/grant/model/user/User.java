package com.codekutter.grant.model.user;

import com.codekutter.grant.model.*;
import com.codekutter.grant.model.utils.CopyUtils;
import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
public class User extends StatefulEntity<UserId>
        implements Principal, TenantEntity {
    /**
     * Unique User ID.
     */
    @EmbeddedId
    @Validate
    private UserId userId;
    /**
     * User name.
     */
    @Column(name = "user_name")
    @Validate
    private String userName;
    /**
     * State of this user record.
     */
    @Column(name = "user_state")
    @Enumerated(EnumType.STRING)
    @Validate
    private EUserState userState = EUserState.Unknown;
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
     * Set of user properties.
     */
    @OneToMany(fetch = FetchType.EAGER)
    private Set<UserProperty> properties;

    /**
     * Get this username (User Principal)
     *
     * @return - User ID.
     */
    @Override
    public String getName() {
        return userId.getUserId();
    }

    /**
     * Get the tenant this Entity definition belongs to.
     *
     * @return - Owner Tenant.
     */
    @Override
    public Tenant getTenant() {
        return userId.getTenant();
    }

    /**
     * Check if this user handle is a proxy for the
     * passed subject.
     *
     * @param subject - Subject instance.
     * @return - Is Proxy?
     */
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
    public UserId getKey() {
        return userId;
    }

    /**
     * Compare the entity key with the key specified.
     *
     * @param key - Target Key.
     * @return - Comparision.
     */
    @Override
    public int compare(UserId key) {
        return userId.compareTo(key);
    }

    /**
     * Validate the derived entity.
     *
     * @param errors - Errors handle.
     */
    @Override
    public ValidationExceptions validate(ValidationExceptions errors) {
        try {
            ValidationUtils.validate(getClass(), this);
            userId.validate();
            updateInfo.validate();
        } catch (ValidationExceptions e) {
            if (errors == null)
                errors = e;
            else {
                errors.addAll(e.getErrors());
            }
        }
        if (userState == EUserState.Unknown) {
            errors = ValidationExceptions.add(new ValidationException(
                    String.format("Invalid Property Value : State = %s",
                                  userState.name())), errors);
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
        return errors;
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
    public IEntity<UserId> copyChanges(IEntity<UserId> source)
    throws CopyException {
        if (!(source instanceof User)) {
            throw new CopyException(String.format(
                    "Cannot copy from source : Type mismatch. [type=%s]",
                    source.getClass().getCanonicalName()));
        }
        return CopyUtils.copy(this, source);
    }
}
