package com.codekutter.grant.model.utils;

import com.codekutter.grant.model.CopyException;
import com.codekutter.grant.model.IEntity;
import com.codekutter.zconfig.common.utils.ReflectionUtils;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;

public class CopyUtils {
    public static <K> IEntity<K> copy(@Nonnull IEntity<K> target,
                                      @Nonnull IEntity<K> source) throws
                                                                  CopyException {
        Preconditions.checkArgument(source != null);
        Preconditions.checkArgument(target != null);
        Preconditions.checkArgument(
                ReflectionUtils.isSuperType(target.getClass(), source.getClass()));
        
        return target;
    }
}
