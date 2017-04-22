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
public class ToDataSourceConfig {

	@Bean(name = "toDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.to")
	public DataSource getDataSource() throws Exception {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "toSqlSessionFactory")
	public SqlSessionFactory toSqlSessionFactory(@Qualifier("toDataSource") DataSource dataSource) throws Exception {
		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);
		return bean.getObject();
	}

	@Bean(name = "toTransactionManager")
	public DataSourceTransactionManager toTransactionManager(@Qualifier("toDataSource") DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "toSqlSessionTemplate")
	public SqlSessionTemplate toSqlSessionTemplate(
			@Qualifier("toSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean(name = "toJdbcTemplate")
	public JdbcTemplate toJdbcTemplate(@Qualifier("toDataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
