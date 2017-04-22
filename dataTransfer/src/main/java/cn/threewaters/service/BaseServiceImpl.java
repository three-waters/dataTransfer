package cn.threewaters.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseServiceImpl implements BaseService {

	@Autowired
	@Qualifier("fromJdbcTemplate")
	public JdbcTemplate fromJdbcTemplate;

	@Autowired
	@Qualifier("toJdbcTemplate")
	public JdbcTemplate toJdbcTemplate;

	public List<Map<String, Object>> test1() {
		List<Map<String, Object>> queryForList = fromJdbcTemplate.queryForList("select * from StoreHouse");
		return queryForList;
	}

	public List<Map<String, Object>> test2() {
		List<Map<String, Object>> queryForList = toJdbcTemplate.queryForList("select * from cwlw_storehouse");
		return queryForList;
	}

}
