package cn.threewaters.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import cn.threewaters.util.FormatterUtil;
import cn.threewaters.util.SQLUtil;

@Service
@Transactional
public class TypeCodeServiceImpl extends BaseServiceImpl implements TypeCodeService {

	private static final Logger logger = LoggerFactory.getLogger(TypeCodeServiceImpl.class);

	public List<Map<String, Object>> execute() {
		logger.info("开始进行============仓房类别信息============数据转换");
		logger.info("----开始====删除目标库全部仓房类别信息");
		toGtJdbcTemplate.execute("delete from dm_kdcflx");
		logger.info("----结束====删除目标库全部仓房类别信息");
		logger.info("----开始====获取源库仓房类别信息");
		List<Map<String, Object>> sourceTypeCodeResult = fromJdbcTemplate
				.queryForList("select TypeCode,HouseType from TypeCode");
		logger.info("----结束====获取源库仓房类别信息");
		logger.info("----开始====转换仓房类别信息");
		List<Map<String, Object>> toResult = Lists.newArrayList();
		for (Map<String, Object> soureRow : sourceTypeCodeResult) {
			Map<String, Object> toRow = Maps.newHashMap();
			// 默认字段
			toResult.add(toRow);
			toRow.put("ID", UUID.randomUUID().toString().replaceAll("-", ""));
			toRow.put("XGR", "数据转换");
			// 可对照字段
			toRow.put("BH", soureRow.get("TypeCode"));
			toRow.put("MC", FormatterUtil.trim(soureRow.get("HouseType")));
		}
		logger.info("----结束====转换仓房类别信息");
		logger.info("----开始====生成仓房类别信息SQL");
		List<String> insertSQL = SQLUtil.toInsertSQL(toResult, "dm_kdcflx");
		logger.info("----结束====生成仓房类别信息SQL");
		logger.info("----开始====插入仓房类别信息");
		toGtJdbcTemplate.batchUpdate(insertSQL.toArray(new String[insertSQL.size()]));
		logger.info("----结束====插入仓房类别信息");
		logger.info("结束进行============仓房类别信息============数据转换,源表条数：" + sourceTypeCodeResult.size()
				+ "    转入目标表dm_kdcflx条数：" + insertSQL.size());

		return toResult;
	}

}
