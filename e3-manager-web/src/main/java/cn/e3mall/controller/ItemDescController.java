package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.service.ItemDescService;

@Controller
@RequestMapping("/desc")
public class ItemDescController {
	
	@Autowired
	private ItemDescService itemDescService;

	@RequestMapping("/show/{id}")
	@ResponseBody
	public E3Result showDesc(@PathVariable long id){
		E3Result e3Result = itemDescService.getDescById(id);
		return e3Result;
	}
}
