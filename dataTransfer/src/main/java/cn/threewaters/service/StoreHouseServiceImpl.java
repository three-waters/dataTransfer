package cn.threewaters.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StoreHouseServiceImpl extends BaseServiceImpl implements StoreHouseService {

	public List<Map<String, Object>> execute() {
		List<Map<String, Object>> queryForList = fromJdbcTemplate.queryForList("select * from StoreHouse");
		return queryForList;
	}

}
