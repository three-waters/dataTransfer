package cn.threewaters.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class ToCwlwDataSourceConfig {

	@Bean(name = "toCwlwDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.to_cwlw")
	public DataSource getDataSource() throws Exception {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "toCwlwSqlSessionFactory")
	public SqlSessionFactory toCwlwSqlSessionFactory(@Qualifier("toCwlwDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "toCwlwTransactionManager")
	public DataSourceTransactionManager toCwlwTransactionManager(@Qualifier("toCwlwDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "toCwlwSqlSessionTemplate")
	public SqlSessionTemplate toCwlwSqlSessionTemplate(
			@Qualifier("toCwlwSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "toCwlwJdbcTemplate")
	public JdbcTemplate toCwlwJdbcTemplate(@Qualifier("toCwlwDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
