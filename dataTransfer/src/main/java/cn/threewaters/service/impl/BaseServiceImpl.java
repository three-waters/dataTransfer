package cn.threewaters.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.threewaters.service.BaseService;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

	@Autowired
	@Qualifier("fromJdbcTemplate")
	public JdbcTemplate fromJdbcTemplate;

	@Autowired
	@Qualifier("toCwlwJdbcTemplate")
	public JdbcTemplate toCwlwJdbcTemplate;

	@Autowired
	@Qualifier("toGtJdbcTemplate")
	public JdbcTemplate toGtJdbcTemplate;

}
