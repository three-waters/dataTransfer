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
public class ToGtDataSourceConfig {

	@Bean(name = "toGtDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.to_gt")
	public DataSource getDataSource() throws Exception {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "toGtSqlSessionFactory")
	public SqlSessionFactory toGtSqlSessionFactory(@Qualifier("toGtDataSource") DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "toGtTransactionManager")
	public DataSourceTransactionManager toGtTransactionManager(@Qualifier("toGtDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "toGtSqlSessionTemplate")
	public SqlSessionTemplate toGtSqlSessionTemplate(
			@Qualifier("toGtSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "toGtJdbcTemplate")
	public JdbcTemplate toGtJdbcTemplate(@Qualifier("toGtDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
