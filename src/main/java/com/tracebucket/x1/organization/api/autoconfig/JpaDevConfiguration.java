package com.tracebucket.x1.organization.api.autoconfig;

import com.tracebucket.tron.autoconfig.NonExistingJpaDevBeans;
import com.tracebucket.tron.ddd.jpa.CustomRepositoryFactoryBean;
import com.tracebucket.tron.embedded.mysql.EmbeddedMysqlDatabaseBuilder;
import org.h2.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * User: ffl
 * Date: 4/3/14
 * Time: 4:00 PM
 */
@Profile("dev")
@Configuration
@Conditional(value = NonExistingJpaDevBeans.class)
@EnableJpaRepositories(basePackages = {"com.tracebucket.x1.**.api.repository.jpa"}, repositoryFactoryBeanClass = CustomRepositoryFactoryBean.class)
@EntityScan(basePackages = {"com.tracebucket.x1.**.api.domain", "com.tracebucket.x1.dictionary.api.domain.jpa.impl"})
@PropertySource(value = "classpath:jpa-dev.properties")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaAuditing
public class JpaDevConfiguration {
    private static final Logger log = LoggerFactory.getLogger(JpaDevConfiguration.class);

    @Value("${show_sql}")
    private String showSql;
    @Value("${generateDdl}")
    private String generateDdl;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String password;
    @Value("${db.name}")
    private String dbName;
    @Value("${user.home}")
    private String homeDirectory;

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setUrl("jdbc:h2:file:" + homeDirectory + "/" + dbName + ";AUTO_SERVER=TRUE;DB_CLOSE_DELAY=10");
        dataSource.setDriverClass(Driver.class);
        return dataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter()
    {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        jpaVendorAdapter.setShowSql(Boolean.parseBoolean(showSql));
        jpaVendorAdapter.setGenerateDdl(Boolean.parseBoolean(generateDdl));
        return jpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException
    {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws PropertyVetoException
    {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        return factoryBean;
    }
}
