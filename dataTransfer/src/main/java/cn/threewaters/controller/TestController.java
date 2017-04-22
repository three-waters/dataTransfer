package cn.threewaters.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.threewaters.service.TestService;

@RestController
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService testService;

	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public Object begin1() {
		return testService.test1();
	}

	@RequestMapping(value = "/2", method = RequestMethod.GET)
	public Object begin2() {
		return testService.test2();
	}

	@RequestMapping(value = "/3", method = RequestMethod.GET)
	public Object begin3() {
		return testService.test3();
	}
}
