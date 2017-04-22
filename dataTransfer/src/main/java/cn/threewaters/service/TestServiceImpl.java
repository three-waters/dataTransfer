package cn.threewaters.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestServiceImpl extends BaseServiceImpl implements TestService {

	public List<Map<String, Object>> test1() {
		List<Map<String, Object>> queryForList = fromJdbcTemplate.queryForList("select * from StoreHouse");
		return queryForList;
	}

	public List<Map<String, Object>> test2() {
		List<Map<String, Object>> queryForList = toCwlwJdbcTemplate.queryForList("select * from cwlw_storehouse");
		return queryForList;
	}

	public List<Map<String, Object>> test3() {
		List<Map<String, Object>> queryForList = toGtJdbcTemplate.queryForList("select * from dm_crk_lspz");
		return queryForList;
	}

}
