package com.codekutter.grant.model.user;

import com.codekutter.grant.model.IValidate;
import com.codekutter.grant.model.Tenant;
import com.codekutter.grant.model.Validate;
import com.codekutter.grant.model.ValidationExceptions;
import com.codekutter.grant.model.utils.ValidationUtils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;

/**
 * Composite ID of defining User records.
 *
 * ID is expected to be defined in the context of a tenant.
 */
@Embeddable
@Getter
@Setter
public class UserId implements Serializable, Comparable<UserId>, IValidate {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_id")
    @Validate
    private Tenant tenant;
    @Column(name = "user_id")
    @Validate
    private String userId;

    /**
     * Comparable delegate - Compares Tenant & User ID.
     *
     * @param userId - Target User ID.
     * @return - Comparison.
     */
    @Override
    public int compareTo(@Nonnull UserId userId) {
        if (userId != null) {
            int ret = tenant.compare(userId.tenant.getKey());
            if (ret == 0) {
                ret = this.userId.compareTo(userId.userId);
            }
            return ret;
        }
        return Integer.MAX_VALUE;
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
