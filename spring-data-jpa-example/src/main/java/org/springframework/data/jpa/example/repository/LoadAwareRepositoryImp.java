package org.springframework.data.jpa.example.repository;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * An implementation of the LoadAwareRepository, this extends the default CRUD functionality supplied by the
 * {@link org.springframework.data.jpa.repository.support.SimpleJpaRepository} and adds the implementation for the load method.
 *
 * This class will then replace the <code>SimpleJpaRepository</code> as the default implementation for all repository interfaces. The code that
 * carries out this replacement can be found in the {@link org.springframework.data.jpa.example.factory.CustomRepositoryFactoryBean}.
 *
 * @author karl
 */
public class LoadAwareRepositoryImp<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements
        LoadAwareRepository<T, ID> {

    private EntityManager entityManager;

    public LoadAwareRepositoryImp(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        this.entityManager = entityManager;
    }

    @Override
    public T load(T object, ID id) {

        ((Session)entityManager.getDelegate()).load(object, id);

        return object;
    }
}
