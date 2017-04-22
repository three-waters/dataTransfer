package cn.threewaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.threewaters.service.StoreHouseService;
import cn.threewaters.service.TypeCodeService;

@RestController
@RequestMapping("/")
public class RunController {

	@Autowired
	private StoreHouseService storeHouseService;

	@Autowired
	private TypeCodeService typeCodeService;

	@RequestMapping(value = "/storeHouse/{lkbh}", method = RequestMethod.GET)
	public Object storeHouse(@PathVariable String lkbh) {
		return storeHouseService.execute(lkbh);
	}

	@RequestMapping(value = "/typeCode", method = RequestMethod.GET)
	public Object storeHouse() {
		return typeCodeService.execute();
	}

}
