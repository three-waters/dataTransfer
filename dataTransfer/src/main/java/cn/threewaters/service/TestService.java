package cn.threewaters.service;

import java.util.List;
import java.util.Map;

public interface TestService extends BaseService {

	public List<Map<String, Object>> test1();

	public List<Map<String, Object>> test2();

	public List<Map<String, Object>> test3();

	public boolean checkDB();
}
