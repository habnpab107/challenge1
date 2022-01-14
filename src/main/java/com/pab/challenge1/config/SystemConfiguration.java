/* 
 * SystemConfiguration
 * @author paul
 */
package com.pab.challenge1.config;

import com.pab.challenge1.builder.ActorBuilder;
import com.pab.challenge1.builder.CatalogBuilder;
import com.pab.challenge1.builder.MovieBuilder;
import com.pab.challenge1.dao.ActorDAO;
import com.pab.challenge1.dao.ActorDAOImpl;
import com.pab.challenge1.dao.CatalogDAO;
import com.pab.challenge1.dao.CatalogDAOImpl;
import com.pab.challenge1.dao.MovieDAO;
import com.pab.challenge1.dao.MovieDAOImpl;
import com.pab.challenge1.datasource.MySQLDataSource;
import com.pab.challenge1.service.ActorService;
import com.pab.challenge1.service.ActorServiceImpl;
import com.pab.challenge1.service.CatalogService;
import com.pab.challenge1.service.CatalogServiceImpl;
import com.pab.challenge1.service.MovieService;
import com.pab.challenge1.service.MovieServiceImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.inject.Inject;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("webresources")
public class SystemConfiguration extends Application {

    private static final Logger LOGGER = Logger.getLogger(SystemConfiguration.class.getName());

    /**
     * This constructor is responsible to load all the configuration related to jersey and Bean Injection and Validations
     */
    
    public SystemConfiguration() {
        LOGGER.info(">> Started Loading resources <<");
    }

    /**
     * This Method is responsible to Auto wire the Service and DAO classes
     *
     * @param locator
     */
    @Inject
    public SystemConfiguration(ServiceLocator locator) {

        // Obtain the DynamicConfiguration object for binding a service to a contract.
        DynamicConfigurationService dcs = locator.getService(DynamicConfigurationService.class);
        DynamicConfiguration config = dcs.createDynamicConfiguration();
        config.bind(BuilderHelper.link(ActorServiceImpl.class).to(ActorService.class).build());
        config.bind(BuilderHelper.link(ActorDAOImpl.class).to(ActorDAO.class).build());
        config.bind(BuilderHelper.link(ActorBuilder.class).to(ActorBuilder.class).build());
        config.bind(BuilderHelper.link(MovieServiceImpl.class).to(MovieService.class).build());
        config.bind(BuilderHelper.link(MovieDAOImpl.class).to(MovieDAO.class).build());
        config.bind(BuilderHelper.link(MovieBuilder.class).to(MovieBuilder.class).build());
        config.bind(BuilderHelper.link(CatalogServiceImpl.class).to(CatalogService.class).build());
        config.bind(BuilderHelper.link(CatalogDAOImpl.class).to(CatalogDAO.class).build());
        config.bind(BuilderHelper.link(CatalogBuilder.class).to(CatalogBuilder.class).build());
        config.bind(BuilderHelper.link(MySQLDataSource.class).to(MySQLDataSource.class).build());
        config.commit();
    }
    
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }


    @Override
    public Map<String, Object> getProperties() {
        Map<String, Object> props = new HashMap<>();
        return props;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.pab.challenge1.resource.ActorResource.class);
    }
}