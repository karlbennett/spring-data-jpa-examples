package org.springframework.data.jpa.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Interface to provide global access and implementation to a load method.
 *
 * @author Karl Bennett
 */
@NoRepositoryBean // We do not want an implementation generated for this repository interface.
public interface LoadAwareRepository <T, ID extends Serializable> extends JpaRepository<T, ID> {

    /**
     * Load a persisted object and populate the provided object instance with it's data.
     *
     * @param object - the object to be populated.
     * @param id - the id of the persisted object.
     * @return the populated object.
     */
    T load(T object, ID id);
}
