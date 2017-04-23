package cn.threewaters.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.threewaters.service.TestService;

@Service
@Transactional
public class TestServiceImpl extends BaseServiceImpl implements TestService {

	private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

	public boolean checkDB() {
		boolean flag = true;
		try {
			System.out.println("===========正在检测SQLSERVER源数据库连接=================");
			fromJdbcTemplate.queryForList("select 1");
			fromJdbcTemplate.queryForList("select count(1) from StoreHouse");
			System.out.println("====================StoreHouse检测通过===================");
			fromJdbcTemplate.queryForList("select count(1) from PointInfor");
			System.out.println("====================PointInfor检测通过===================");
			fromJdbcTemplate.queryForList("select count(1) from TestData");
			System.out.println("====================TestData检测通过=====================");
			fromJdbcTemplate.queryForList("select count(1) from GrainCode");
			System.out.println("====================GrainCode检测通过=====================");
			fromJdbcTemplate.queryForList("select count(1) from TypeCode");
			System.out.println("====================TypeCode检测通过=====================");
			System.out.println("===========SQLSERVER源数据库连接检测通过==================");
		} catch (Exception e) {
			flag = false;
			logger.error("============检测未通过================");
		}
		try {
			System.out.println("===========正在检测目标库CWLW数据库连接=================");
			toCwlwJdbcTemplate.queryForList("select 1");
			toCwlwJdbcTemplate.queryForList("select count(1) from cwlw_storehouse");
			System.out.println("====================cwlw_storehouse检测通过===================");
			toCwlwJdbcTemplate.queryForList("select count(1) from cwlw_wsjc_lqjbxx");
			System.out.println("====================cwlw_wsjc_lqjbxx检测通过===================");
			toCwlwJdbcTemplate.queryForList("select count(1) from cwlw_wsjc_cfjcwd");
			System.out.println("====================cwlw_wsjc_cfjcwd检测通过=====================");
			System.out.println("===========目标库CWLW数据库连接检测通过==================");
		} catch (Exception e) {
			flag = false;
			logger.error("============检测未通过================");
		}
		try {
			System.out.println("===========正在检测目标库GT数据库连接=================");
			toGtJdbcTemplate.queryForList("select 1");
			toGtJdbcTemplate.queryForList("select count(1) from dm_crk_lspz");
			System.out.println("====================dm_crk_lspz检测通过===================");
			toGtJdbcTemplate.queryForList("select count(1) from dm_kdcflx");
			System.out.println("====================dm_kdcflx检测通过===================");
			System.out.println("====================目标库GT数据库连接检测通过=====================");
		} catch (Exception e) {
			flag = false;
			logger.error("============检测未通过================");
		}
		return flag;
	}

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
