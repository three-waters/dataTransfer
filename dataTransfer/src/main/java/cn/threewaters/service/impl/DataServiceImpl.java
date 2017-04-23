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

import cn.threewaters.bean.FloorTemp;
import cn.threewaters.service.DataService;
import cn.threewaters.util.SQLUtil;

@Service
@Transactional
public class DataServiceImpl extends BaseServiceImpl implements DataService {

	private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

	public void execute(String lkbh) {
		logger.info("开始进行============检测数据============数据转换");
		logger.info("----开始====删除粮库编号为" + lkbh + "的检测数据");
		toCwlwJdbcTemplate.execute("delete from cwlw_wsjc_cfjcwd where LK_BH = '" + lkbh + "'");
		toCwlwJdbcTemplate.execute("delete from cwlw_wsjc_lqjbxx where lkbh = '" + lkbh + "'");
		logger.info("----结束====删除粮库编号为" + lkbh + "的检测数据");
		logger.info("----开始====获取源库检测数据");
		List<Map<String, Object>> sourceTestDataResult = fromJdbcTemplate.queryForList(
				"select 仓房编号,仓房温度最大值,仓房温度最小值,仓房温度平均值,仓房外温,仓房内温,仓房外湿,仓房内湿,粮食水分,检测日期,温度值集合,(select 粮食品种标准编码 from StoreHouse where StoreHouse.[仓房编号] = TestData.[仓房编号]) as 粮食品种 from TestData order by 仓房编号");
		logger.info("----结束====获取源库检测数据");
		logger.info("----开始====获取源库传感器数据");
		List<Map<String, Object>> sourcePointerResult = fromJdbcTemplate
				.queryForList("select 仓房编号,电缆编号,行编号,列编号,层编号 from PointInfor order by 仓房编号,行编号 desc,列编号 desc,层编号 desc");
		logger.info("----结束====获取源库传感器数据");
		logger.info("----开始====转换检测数据");
		List<Map<String, Object>> toLQJBXXResult = Lists.newArrayList();
		List<Map<String, Object>> toCFJCWDResult = Lists.newArrayList();
		for (Map<String, Object> soureRow : sourceTestDataResult) {
			String temps = "=" + (String) soureRow.get("温度值集合") + "=";
			temps = temps.replaceAll("NULL", "=NULL=").replaceAll(" ", "=").replaceAll("-", "=-");
			temps = temps.replaceAll("==", "=").replaceAll("===", "=").replaceAll("====", "=").replaceAll("\r", "")
					.replaceAll("\n", "");
			temps = temps.substring(1, temps.length() - 1);
			String[] tempArray = temps.split("=");
			Map<String, FloorTemp> tempMap = Maps.newHashMap();
			int index = 0;
			// 循环预留
			for (Map<String, Object> sourcePointerRow : sourcePointerResult) {
				if ((String.valueOf(soureRow.get("仓房编号"))).equals(String.valueOf(sourcePointerRow.get("仓房编号")))) {
					Map<String, Object> toCFJCWDRow = Maps.newHashMap();
					toCFJCWDRow.put("WS_BH", UUID.randomUUID().toString());
					toCFJCWDRow.put("LK_BH", lkbh);
					toCFJCWDRow.put("CFBH", sourcePointerRow.get("仓房编号"));
					toCFJCWDRow.put("JCCS", sourcePointerRow.get("层编号"));
					toCFJCWDRow.put("JCHH", sourcePointerRow.get("行编号"));
					toCFJCWDRow.put("JCBHS", sourcePointerRow.get("列编号"));
					String temp = tempArray[index++];
					if (temp != null && !temp.equals("NULL") && !temp.equals("null") && !temp.equals("")) {
						temp = String.valueOf(Double.parseDouble(temp) / 10.00);
					}
					toCFJCWDRow.put("jcwd", temp);
					toCFJCWDRow.put("JCRQ", soureRow.get("检测日期"));
					toCFJCWDRow.put("DLBH", sourcePointerRow.get("电缆编号"));

					toCFJCWDResult.add(toCFJCWDRow);

					if (!tempMap.containsKey(String.valueOf(sourcePointerRow.get("层编号")))) {
						FloorTemp ft = new FloorTemp();
						ft.add(temp);
						tempMap.put(String.valueOf(sourcePointerRow.get("层编号")), ft);
					} else {
						FloorTemp floorTemp = tempMap.get(String.valueOf(sourcePointerRow.get("层编号")));
						floorTemp.add(temp);
					}
				}
			}
			Map<String, Object> toLQJBXXRow = Maps.newHashMap();
			toLQJBXXRow.put("bh", UUID.randomUUID().toString());
			toLQJBXXRow.put("cfbh", soureRow.get("仓房编号"));
			toLQJBXXRow.put("lkbh", lkbh);
			toLQJBXXRow.put("zgwd", soureRow.get("仓房温度最大值"));
			toLQJBXXRow.put("zdwd", soureRow.get("仓房温度最小值"));
			toLQJBXXRow.put("pjwd", soureRow.get("仓房温度平均值"));
			toLQJBXXRow.put("cwwd", soureRow.get("仓房外温"));
			toLQJBXXRow.put("cnwd", soureRow.get("仓房内温"));
			toLQJBXXRow.put("cwsd", soureRow.get("仓房外湿"));
			toLQJBXXRow.put("cnsd", soureRow.get("仓房内湿"));
			toLQJBXXRow.put("lssf", soureRow.get("粮食水分"));
			toLQJBXXRow.put("lspz", soureRow.get("粮食品种"));
			toLQJBXXRow.put("jcsj", soureRow.get("检测日期"));

			FloorTemp floorTemp1 = tempMap.get("1");
			if (floorTemp1 == null) {
				floorTemp1 = new FloorTemp();
			}

			FloorTemp floorTemp2 = tempMap.get("2");
			if (floorTemp2 == null) {
				floorTemp2 = new FloorTemp();
			}

			FloorTemp floorTemp3 = tempMap.get("3");
			if (floorTemp3 == null) {
				floorTemp3 = new FloorTemp();
			}

			FloorTemp floorTemp4 = tempMap.get("4");
			if (floorTemp4 == null) {
				floorTemp4 = new FloorTemp();
			}

			FloorTemp floorTemp5 = tempMap.get("5");
			if (floorTemp5 == null) {
				floorTemp5 = new FloorTemp();
			}

			toLQJBXXRow.put("pjwd1", floorTemp1.getPjwd());
			toLQJBXXRow.put("pjwd2", floorTemp2.getPjwd());
			toLQJBXXRow.put("pjwd3", floorTemp3.getPjwd());
			toLQJBXXRow.put("pjwd4", floorTemp4.getPjwd());
			toLQJBXXRow.put("pjwd5", floorTemp5.getPjwd());

			toLQJBXXRow.put("zgwd1", floorTemp1.getZgwd());
			toLQJBXXRow.put("zgwd2", floorTemp2.getZgwd());
			toLQJBXXRow.put("zgwd3", floorTemp3.getZgwd());
			toLQJBXXRow.put("zgwd4", floorTemp4.getZgwd());
			toLQJBXXRow.put("zgwd5", floorTemp5.getZgwd());

			toLQJBXXRow.put("zdwd1", floorTemp1.getZdwd());
			toLQJBXXRow.put("zdwd2", floorTemp2.getZdwd());
			toLQJBXXRow.put("zdwd3", floorTemp3.getZdwd());
			toLQJBXXRow.put("zdwd4", floorTemp4.getZdwd());
			toLQJBXXRow.put("zdwd5", floorTemp5.getZdwd());

			toLQJBXXResult.add(toLQJBXXRow);
		}
		logger.info("----结束====转换检测数据");
		logger.info("----开始====生成检测数据SQL");
		List<String> insertSQL_toLQJBXXResult = SQLUtil.toInsertSQL(toLQJBXXResult, "cwlw_wsjc_lqjbxx");
		List<String> insertSQL_toCFJCWDResult = SQLUtil.toInsertSQL(toCFJCWDResult, "cwlw_wsjc_cfjcwd");
		logger.info("----结束====生成检测数据SQL");
		logger.info("----开始====插入检测数据,预估数据量   LQJBXX:" + insertSQL_toLQJBXXResult.size() + "    CFJCWD:"
				+ insertSQL_toCFJCWDResult.size());
		batchExecute(toCwlwJdbcTemplate, insertSQL_toLQJBXXResult);
		batchExecute(toCwlwJdbcTemplate, insertSQL_toCFJCWDResult);
		logger.info("----结束====插入检测数据");
		logger.info("结束进行============检测数据============数据转换,源表条数：" + sourceTestDataResult.size()
				+ "    转入目标表cwlw_wsjc_lqjbxx条数：" + insertSQL_toLQJBXXResult.size() + "    转入目标表cwlw_wsjc_cfjcwd条数："
				+ insertSQL_toCFJCWDResult.size());
	}
}
