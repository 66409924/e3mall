package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;

@Controller
@RequestMapping("/content/category")
public class ContentCatController {
	
	@Autowired
	private ContentCatService contentCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<TreeNode> getContentCatList(@RequestParam(value="id",defaultValue="0")Long parentId) {
		List<TreeNode> catList = contentCatService.getContentCatList(parentId);
		return catList;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public E3Result addContentCategory(Long parentId,String name) {
		E3Result e3Result = contentCatService.addContentCategory(parentId, name);
		return e3Result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public E3Result updateContentCategory(Long id,String name) {
		E3Result e3Result = contentCatService.updateContentCategory(id, name);
		return e3Result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result deleteContentCategory(Long id) {
		E3Result e3Result = contentCatService.deleteContentCategory(id);
		return e3Result;
	}
}
