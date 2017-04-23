package cn.threewaters.service.impl;

import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.threewaters.service.BaseService;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	private static final int INSERT_COUNT = 500;

	@Autowired
	@Qualifier("fromJdbcTemplate")
	public JdbcTemplate fromJdbcTemplate;

	@Autowired
	@Qualifier("toCwlwJdbcTemplate")
	public JdbcTemplate toCwlwJdbcTemplate;

	@Autowired
	@Qualifier("toGtJdbcTemplate")
	public JdbcTemplate toGtJdbcTemplate;

	public void batchExecute(JdbcTemplate jdbcTemplate, List<String> sqlList) {
		List<String> insertList = Lists.newArrayList();
		int sum = 0;
		for (int i = 1, count = sqlList.size(); i <= count; i++) {
			insertList.add(sqlList.get(i - 1));
			if (i % INSERT_COUNT == 0) {
				sum = sum + insertList.size();
				jdbcTemplate.batchUpdate(insertList.toArray(new String[insertList.size()]));
				logger.info("============成功插入" + insertList.size() + "条，完成" + sum*100 / count + "%");
				insertList = Lists.newArrayList();
			}
		}
		if (insertList.size() > 0) {
			sum = sum + insertList.size();
			jdbcTemplate.batchUpdate(insertList.toArray(new String[insertList.size()]));
			logger.info("============成功插入" + insertList.size() + "条，完成" + sum*100 / sqlList.size() + "%");
		}
	}
}
