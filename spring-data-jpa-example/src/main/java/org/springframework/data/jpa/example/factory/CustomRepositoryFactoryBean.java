package org.springframework.data.jpa.example.factory;

import org.springframework.data.jpa.example.repository.LoadAwareRepository;
import org.springframework.data.jpa.example.repository.LoadAwareRepositoryImp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Using this custom repository factory in place of the default on will cause all the repository interfaces to receive the
 * {@link org.springframework.data.jpa.example.repository.LoadAwareRepositoryImp} their basic CRUD implementation, thus providing access
 * to the implementation of it's load method.
 *
 * @author karl
 */
public class CustomRepositoryFactoryBean<R extends JpaRepository<T, ID>, T, ID extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, ID> {

    @Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new CustomRepositoryFactory(entityManager);
    }

    private static class CustomRepositoryFactory<T, ID extends Serializable> extends JpaRepositoryFactory {

        private EntityManager entityManager;

        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);

            this.entityManager = entityManager;
        }

        @Override
        protected Object getTargetRepository(RepositoryMetadata metadata) {

            return new LoadAwareRepositoryImp<T, ID>((Class<T>) metadata.getDomainClass(), entityManager);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

            // The RepositoryMetadata can be safely ignored, it is used by the JpaRepositoryFactory to check for QueryDslJpaRepository's
            // which is out of scope.
            return LoadAwareRepository.class;
        }
    }
}