package com.codekutter.grant.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Key for the User Properties
 */
@Embeddable
@Getter
@Setter
public class UserPropertyId implements Serializable, Comparable<UserPropertyId> {
    /**
     * User ID this property belongs to.
     */
    @Column(name = "user_id")
    private String userId;
    /**
     * Property definition reference.
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private UserPropertyDef property;

    /**
     * Comparable - compare the user ID/property name.
     *
     * @param o - Target instance to compare with.
     * @return - Comparision.
     */
    @Override
    public int compareTo(UserPropertyId o) {
        int ret = Integer.MIN_VALUE;
        if (o != null) {
            ret = userId.compareTo(o.userId);
            if (ret == 0) {
                ret = property.getKey().compareTo(o.property.getKey());
            }
        }
        return ret;
    }
}
