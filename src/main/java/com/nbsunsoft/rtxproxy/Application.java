package com.nbsunsoft.rtxproxy;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;

import com.nbsunsoft.rtxproxy.util.PropertiesUtils;

@SpringBootApplication
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer
        implements WebApplicationInitializer, EmbeddedServletContainerCustomizer {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        Thread rtxProxySendThread = context.getBean(RTXProxySendThread.class);
        rtxProxySendThread.start();
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {
        try {
            String serverPort = PropertiesUtils.getProperty("rtxproxy.serverPort");
            container.setPort(Integer.valueOf(serverPort));
        } catch (Exception e) {
            LOG.error("{}", e);
            container.setPort(8080);
        }
    }
}
