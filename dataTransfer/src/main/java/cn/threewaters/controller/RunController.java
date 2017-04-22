package cn.threewaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.threewaters.service.DataService;
import cn.threewaters.service.GrainCodeService;
import cn.threewaters.service.StoreHouseService;
import cn.threewaters.service.TypeCodeService;

@RestController
@RequestMapping("/")
public class RunController {

	@Autowired
	private StoreHouseService storeHouseService;

	@Autowired
	private TypeCodeService typeCodeService;

	@Autowired
	private GrainCodeService grainCodeService;

	@Autowired
	private DataService dataService;

	@RequestMapping(value = "/storeHouse/{lkbh}", method = RequestMethod.GET)
	public Object storeHouse(@PathVariable String lkbh) {
		try {
			storeHouseService.execute(lkbh);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/typeCode", method = RequestMethod.GET)
	public Object typeCode() {
		try {
			typeCodeService.execute();
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/grainCode", method = RequestMethod.GET)
	public Object grainCode() {
		try {
			grainCodeService.execute();
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	@RequestMapping(value = "/testData/{lkbh}", method = RequestMethod.GET)
	public Object testData(@PathVariable String lkbh) {
		try {
			dataService.execute(lkbh);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

}
