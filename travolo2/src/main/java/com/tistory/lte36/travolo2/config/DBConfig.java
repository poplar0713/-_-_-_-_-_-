package com.tistory.lte36.travolo2.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

@Configuration
@EnableTransactionManagement	//트랜잭션과 관련된 설정을 자동으로 해준다.
public class DBConfig implements TransactionManagementConfigurer {
	//사용자와 트랜잭션 사이의 처리를 위한 PlatFormTransactionManager를 설정하기 위해서
	//TransaxtionManagerConfigurer를 구현한다.
	private String driverClassName = "com.mysql.jdbc.Driver";

	private String url = "jdbc:mysql://localhost/jsdb?serverTimezone=Asia/Seoul&useSSL=false&useUnicode=true&characterEncoding=utf-8";
	private String username = "client";
	private String password = "cjs0701^^";
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(driverClassName);
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		return dataSource;
	}

	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManger();		//트랜잭션을 처리할 객체를 반환
	}

	@Bean
	public PlatformTransactionManager transactionManger() {
		return new DataSourceTransactionManager(dataSource());
	}
}