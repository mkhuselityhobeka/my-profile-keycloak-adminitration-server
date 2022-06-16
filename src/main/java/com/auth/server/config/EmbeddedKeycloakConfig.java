package com.auth.server.config;

import org.keycloak.platform.Platform;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.naming.*;
import javax.naming.spi.NamingManager;
import javax.sql.DataSource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class EmbeddedKeycloakConfig {


    @Bean
    public FilterRegistrationBean filterRegistrationBean(KeyCloakServerProperties keyCloakServerProperties){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setName("Keycloak Session Management");
        registrationBean.setFilter(new KeycloakRegistrationFilter());
        registrationBean.addUrlPatterns(keyCloakServerProperties.getContextPath() + "/*");
        return registrationBean;
    }
   public void jndiEnvironment(DataSource dataSource) throws NamingException {
       NamingManager.setInitialContextFactoryBuilder(env -> environment -> new InitialContext(){
           @Override
           public Object lookup(Name name){
               return lookup(name.toString());
           }

           @Override
           public Object lookup(String name){
               if ("spring/datasource".equals(name)) {
                   return dataSource;
               } else if (name.startsWith("java:jboss/ee/concurrency/executor/")) {
                   return fixedThreadPool();
               }
               return null;
           }
           @Override
           public NameParser getNameParser(String name) {
               return CompositeName::new;
           }

           @Override
           public void close() {
           }
       });
   }


    @Bean("fixedThreadPool")
    public ExecutorService fixedThreadPool(){
       return Executors.newFixedThreadPool(5);
    }
    @Bean
    @ConditionalOnMissingBean
    public SimplePlatformProvider simplePlatformProvider(){
        return (SimplePlatformProvider)Platform.getPlatform();
    }

}
