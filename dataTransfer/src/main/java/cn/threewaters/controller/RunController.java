package cn.threewaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.threewaters.service.StoreHouseService;

@RestController
@RequestMapping("/")
public class RunController {

	@Autowired
	private StoreHouseService storeHouseService;

	@RequestMapping(value = "/storeHouse", method = RequestMethod.GET)
	public Object storeHouse() {
		return storeHouseService.execute();
	}

}
