package cn.threewaters.service;

import java.util.List;
import java.util.Map;

public interface StoreHouseService extends BaseService {

	public List<Map<String, Object>> execute(String lkbh);
}
