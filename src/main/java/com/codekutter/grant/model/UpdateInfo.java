package com.codekutter.grant.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

/**
 * Struct to define update information.
 */
@Embeddable
@Getter
@Setter
public class UpdateInfo {
    /**
     * Updated By User ID
     */
    private String updatedBy;
    /**
     * Updated At timestamp.
     */
    private long updateTimestamp;
}
