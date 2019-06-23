package com.codekutter.grant.model.user;

/**
 * Enum defining the userState of a recorded user.
 */
public enum EUserState {
    /**
     * User State is Unknown.
     */
    Unknown,
    /**
     * User is active.
     */
    Active,
    /**
     * User has been suspended
     */
    Suspended,
    /**
     * User has been locked out.
     */
    Locked,
    /**
     * User has been deleted.
     */
    Deleted
}
