package cn.threewaters.service.impl;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import cn.threewaters.service.StoreHouseService;
import cn.threewaters.util.FormatterUtil;
import cn.threewaters.util.SQLUtil;

@Service
@Transactional
public class StoreHouseServiceImpl extends BaseServiceImpl implements StoreHouseService {

	private static final Logger logger = LoggerFactory.getLogger(StoreHouseServiceImpl.class);

	public void execute(String lkbh) {
		logger.info("开始进行============仓房信息============数据转换");
		logger.info("----开始====删除粮库编号为" + lkbh + "的仓房信息");
		toCwlwJdbcTemplate.execute("delete from cwlw_storehouse where LKBH = '" + lkbh + "'");
		logger.info("----结束====删除粮库编号为" + lkbh + "的仓房信息");
		logger.info("----开始====获取源库仓房信息");
		List<Map<String, Object>> sourceStoreHouseResult = fromJdbcTemplate
				.queryForList("select 仓房编号,仓房名称,保管员,入仓日期,仓房类型编码,粮食性质,粮食品种标准编码  from StoreHouse");
		logger.info("----结束====获取源库仓房信息");
		logger.info("----开始====获取源库电缆信息");
		List<Map<String, Object>> sourcePointInforResult = fromJdbcTemplate.queryForList(
				"select 仓房编号,min(电缆编号) 最小电缆编号,max(电缆编号) 最大电缆编号,count(distinct 行编号) 电缆行数,count(distinct 列编号) 电缆列数,count(distinct 层编号) 电缆层数 from PointInfor group by 仓房编号");
		logger.info("----结束====获取源库电缆信息");
		logger.info("----开始====转换仓房信息");
		List<Map<String, Object>> toResult = Lists.newArrayList();
		for (Map<String, Object> soureRow : sourceStoreHouseResult) {
			Map<String, Object> toRow = Maps.newHashMap();
			// 默认字段
			toRow.put("LKBH", lkbh);
			toResult.add(toRow);
			// 可对照字段
			toRow.put("CFBH", soureRow.get("仓房编号"));
			toRow.put("HouseName", FormatterUtil.trim(soureRow.get("仓房名称")));
			toRow.put("KeeperName", FormatterUtil.trim(soureRow.get("保管员")));
			toRow.put("DateOfIn", soureRow.get("入仓日期"));
			toRow.put("TYPE", soureRow.get("仓房类型编码"));
			toRow.put("CLXX", soureRow.get("粮食性质"));
			toRow.put("GrainCode", soureRow.get("粮食品种标准编码"));
			// 电缆信息
			for (Map<String, Object> sourcePointInforRow : sourcePointInforResult) {
				if (String.valueOf(soureRow.get("仓房编号")).equals(String.valueOf(sourcePointInforRow.get("仓房编号")))) {
					toRow.put("DLHS", sourcePointInforRow.get("电缆行数"));
					toRow.put("DLLS", sourcePointInforRow.get("电缆列数"));
					toRow.put("DLCS", sourcePointInforRow.get("电缆层数"));
					toRow.put("QSDLBH", sourcePointInforRow.get("最小电缆编号"));
					toRow.put("JSDLBH", sourcePointInforRow.get("最大电缆编号"));
				}
			}
		}
		logger.info("----结束====转换仓房信息");
		logger.info("----开始====生成仓房信息SQL");
		List<String> insertSQL = SQLUtil.toInsertSQL(toResult, "cwlw_storehouse");
		logger.info("----结束====生成仓房信息SQL");
		logger.info("----开始====插入仓房信息");
		toCwlwJdbcTemplate.batchUpdate(insertSQL.toArray(new String[insertSQL.size()]));
		logger.info("----结束====插入仓房信息");
		logger.info("结束进行============仓房信息============数据转换,源表条数：" + sourceStoreHouseResult.size()
				+ "    转入目标表cwlw_storehouse条数：" + insertSQL.size());

	}

}
