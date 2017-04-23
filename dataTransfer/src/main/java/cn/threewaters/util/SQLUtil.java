package cn.threewaters.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

public class SQLUtil {

	public static List<String> toInsertSQL(List<Map<String, Object>> resultList, String tableName) {
		List<String> sqlList = Lists.newArrayList();

		for (Map<String, Object> map : resultList) {
			StringBuilder sb = new StringBuilder();
			StringBuilder column_sb = new StringBuilder();
			StringBuilder value_sb = new StringBuilder();
			sb.append("insert into ");
			sb.append(tableName);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				column_sb.append(entry.getKey());
				column_sb.append(",");

				if (entry.getValue() == null) {
					value_sb.append(entry.getValue());
				} else if (entry.getValue() instanceof Date) {
					value_sb.append("'").append(entry.getValue()).append("'");
				} else if (entry.getValue() instanceof String) {
					if (((String) (entry.getValue())).equals("") || ((String) (entry.getValue())).equals("NULL")) {
						value_sb.append("").append("NULL").append("");
					} else {
						value_sb.append("'").append(entry.getValue()).append("'");
					}
				} else {
					value_sb.append("'").append(entry.getValue()).append("'");
				}
				value_sb.append(",");
			}
			column_sb.delete(column_sb.length() - 1, column_sb.length());
			value_sb.delete(value_sb.length() - 1, value_sb.length());
			sb.append("(").append(column_sb).append(")");
			sb.append(" values ");
			sb.append("(").append(value_sb).append(")");
			sqlList.add(sb.toString());
		}

		return sqlList;
	}

}
