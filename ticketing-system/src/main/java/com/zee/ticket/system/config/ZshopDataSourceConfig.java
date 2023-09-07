package com.zee.ticket.system.config;

import java.util.HashMap;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "zshop.db")     
@EnableConfigurationProperties
@EnableJpaRepositories(basePackages = {"com.zee.ticket.system.repository"}, entityManagerFactoryRef = "zshopDataSourceEntityManager", transactionManagerRef = "zshopDataSourceTransactionManager")
public class ZshopDataSourceConfig {

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	private static final String HIBERNATE_DIALECT = "org.hibernate.dialect.MySQLInnoDBDialect";

	@Bean
	public LocalContainerEntityManagerFactoryBean zshopDataSourceEntityManager() {

		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	//	em.setDataSource(dataSource());
		em.setDataSource(dataSource());
		em.setPackagesToScan("com.zee.ticket.system.entity");
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<String, Object>();
		properties.put("hibernate.dialect", HIBERNATE_DIALECT);
		properties.put("show_sql", true);
		properties.put("hibernate.enable_lazy_load_no_trans", true);
		em.setJpaPropertyMap(properties);

		return em;
	}

	@Bean
	public javax.sql.DataSource dataSource() {
		HikariConfig jdbcConfig = new HikariConfig();
		jdbcConfig.setPoolName("HikariPoolZZZ");
		jdbcConfig.setMaximumPoolSize(5);
		jdbcConfig.setJdbcUrl(url);
		jdbcConfig.setUsername(username);
		jdbcConfig.setPassword(password);
		jdbcConfig.setConnectionTimeout(20000l);
		return new HikariDataSource(jdbcConfig);

	}

	@Bean
	public PlatformTransactionManager zshopDataSourceTransactionManager() {

		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(zshopDataSourceEntityManager().getObject());

		return transactionManager;
	}
}