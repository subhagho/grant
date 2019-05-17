package com.codekutter.grant.model;

/**
 * Interface for defining entities.
 *
 * @param <K> - Entity Unique Key type.
 */
public interface IEntity<K> extends IValidate {
    /**
     * Get the unique Key for this entity.
     *
     * @return - Entity Key.
     */
    K getKey();

    /**
     * Compare the entity key with the key specified.
     *
     * @param key - Target Key.
     * @return - Comparision.
     */
    int compare(K key);
}
