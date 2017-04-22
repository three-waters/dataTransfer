package cn.threewaters.util;

public class FormatterUtil {

	public static Object trim(Object value) {
		if (value != null && value instanceof String) {
			value = ((String) value).trim();
			if (value.equals("")) {
				return null;
			} else {
				return value;
			}
		} else {
			return value;
		}

	}

}
