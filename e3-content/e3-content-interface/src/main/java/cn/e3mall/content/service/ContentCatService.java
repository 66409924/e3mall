package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;

public interface ContentCatService {
	List<TreeNode> getContentCatList(Long parentId);
	E3Result addContentCategory(Long parentId,String name);
	E3Result updateContentCategory(Long id,String name);
	E3Result deleteContentCategory(Long id);
}
