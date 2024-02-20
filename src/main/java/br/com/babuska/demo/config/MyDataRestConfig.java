package br.com.babuska.demo.config;

import br.com.babuska.demo.model.Country;
import br.com.babuska.demo.model.Product;
import br.com.babuska.demo.model.ProductCategory;
import br.com.babuska.demo.model.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private final EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod[] unsupportedActions = {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE};
        Class<?>[] classes = {Product.class, ProductCategory.class, Country.class, State.class};

        // Enable readonly actions and methods with CORS for each entity class.
        for (Class<?> entityClass : classes) {
            disableHttpMethods(config, entityClass, unsupportedActions);
        }

        exposeIds(config);
    }

    private static void disableHttpMethods(RepositoryRestConfiguration config, Class<?> entityClass, HttpMethod[] unsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(entityClass)
                .withItemExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods.disable(unsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        List<Class<?>> entityClasses = new ArrayList<>();

        for (EntityType<?> tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        Class<?>[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}
