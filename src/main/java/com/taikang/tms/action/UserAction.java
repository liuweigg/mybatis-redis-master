package com.taikang.tms.action;

import java.io.PrintWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 主要用于测试
 *
 */
@Controller
@RequestMapping(value="/index")
public class UserAction {

	@RequestMapping(method=RequestMethod.GET,value="/sayHi/{name}")
	public void sayHi(@PathVariable String name,PrintWriter out) {
		
		System.out.println("name : " + name);
	}

}
