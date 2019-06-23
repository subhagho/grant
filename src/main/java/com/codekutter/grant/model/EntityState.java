package com.codekutter.grant.model;

import com.codekutter.zconfig.common.AbstractState;

/**
 * Class for defining states for Primary Entities.
 */
public class EntityState extends AbstractState<EEntityState> {
    /**
     * Default Constructor - Initializes userState to unknown.
     */
    public EntityState() {
        setState(EEntityState.Unknown);
    }
}
