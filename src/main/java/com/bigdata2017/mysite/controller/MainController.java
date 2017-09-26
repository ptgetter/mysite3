package com.bigdata2017.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping({"/", "/main"})
	public String index() {
		return "main/index";
	}
	
	@ResponseBody
	@RequestMapping("/hello")
	public String hello() {
		return "하이";
	}
	
	@ResponseBody
	@RequestMapping("/json")
	public Map json() {
		Map map = new HashMap();
		map.put("result", "success");
		map.put("message", null);
		map.put("data", 10);
		return map;
	}
}
