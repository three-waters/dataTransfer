package cn.threewaters.service.impl;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;

import cn.threewaters.service.GrainCodeService;
import cn.threewaters.util.FormatterUtil;
import cn.threewaters.util.SQLUtil;

@Service
@Transactional
public class GrainCodeServiceImpl extends BaseServiceImpl implements GrainCodeService {

	private static final Logger logger = LoggerFactory.getLogger(GrainCodeServiceImpl.class);

	public void execute() {
		System.out.println("开始进行============粮食类别信息============数据转换");
		System.out.println("----开始====删除目标库全部粮食类别信息");
		toGtJdbcTemplate.execute("delete from dm_crk_lspz");
		System.out.println("----结束====删除目标库全部粮食类别信息");
		System.out.println("----开始====获取源库粮食类别信息");
		List<Map<String, Object>> sourceGrainCodeResult = fromJdbcTemplate
				.queryForList("select GrainCode,GrainName from GrainCode");
		System.out.println("----结束====获取源库粮食类别信息");
		System.out.println("----开始====转换粮食类别信息");
		List<Map<String, Object>> toResult = Lists.newArrayList();
		for (Map<String, Object> soureRow : sourceGrainCodeResult) {
			Map<String, Object> toRow = Maps.newHashMap();
			// 默认字段
			toResult.add(toRow);
			toRow.put("ID", UUID.randomUUID().toString().replaceAll("-", ""));
			toRow.put("XGR", "数据转换");
			// 可对照字段
			toRow.put("BH", soureRow.get("GrainCode"));
			toRow.put("MC", FormatterUtil.trim(soureRow.get("GrainName")));
		}
		System.out.println("----结束====转换粮食类别信息");
		System.out.println("----开始====生成粮食类别信息SQL");
		List<String> insertSQL = SQLUtil.toInsertSQL(toResult, "dm_crk_lspz");
		System.out.println("----结束====生成粮食类别信息SQL");
		System.out.println("----开始====插入粮食类别信息");
		toGtJdbcTemplate.batchUpdate(insertSQL.toArray(new String[insertSQL.size()]));
		System.out.println("----结束====插入粮食类别信息");
		System.out.println("结束进行============粮食类别信息============数据转换,源表条数：" + sourceGrainCodeResult.size()
				+ "    转入目标表dm_crk_lspz条数：" + insertSQL.size());

	}

}
