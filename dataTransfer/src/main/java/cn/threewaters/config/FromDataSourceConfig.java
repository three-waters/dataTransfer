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
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class FromDataSourceConfig {

	@Bean(name = "fromDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.from")
	@Primary
	public DataSource getDataSource() throws Exception {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "fromSqlSessionFactory")
	@Primary
	public SqlSessionFactory fromSqlSessionFactory(@Qualifier("fromDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "fromTransactionManager")
	@Primary
	public DataSourceTransactionManager fromTransactionManager(@Qualifier("fromDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "fromSqlSessionTemplate")
	@Primary
	public SqlSessionTemplate fromSqlSessionTemplate(
			@Qualifier("fromSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "fromJdbcTemplate")
	@Primary
	public JdbcTemplate fromJdbcTemplate(@Qualifier("fromDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
