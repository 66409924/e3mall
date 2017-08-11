package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.common.pojo.TreeNode;
import cn.e3mall.content.service.ContentCatService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;
@Service
public class ContentCatServiceImpl implements ContentCatService {
	
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	@Override
	public List<TreeNode> getContentCatList(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<TreeNode> result = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			TreeNode treeNode = new TreeNode();
			treeNode.setId(tbContentCategory.getId());
			treeNode.setText(tbContentCategory.getName());
			treeNode.setState(tbContentCategory.getIsParent()?"closed":"open");
			result.add(treeNode);
		}
		return result;
	}

	@Override
	public E3Result addContentCategory(Long parentId, String name) {
		// 1、创建一个TbContentCategory对象
		TbContentCategory tbContentCategory = new TbContentCategory();
		// 2、补全属性
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setName(name);
		tbContentCategory.setStatus(1);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setIsParent(false);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		// 3、插入到表中。
		contentCategoryMapper.insert(tbContentCategory);
		// 4、需要判断父节点的isparent
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 5、如果父节点的isparent为false应该改为true
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		// 6、返回E3Result其中包含TbContentCategory对象
		
		return E3Result.ok(tbContentCategory);
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		contentCategoryMapper.updateByPrimaryKey(category);
		return E3Result.ok();
	}

	@Override
	public E3Result deleteContentCategory(Long id) {
		// 1）判断如果是父节点不允许删除。（推荐使用）
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list !=null && list.size() >0){
			E3Result result = new E3Result(500,"不能删除",null);
			return result;
		}
		// 1、根据id删除记录。（物理删除）
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = category.getParentId();
		contentCategoryMapper.deleteByPrimaryKey(id);
		// 2、判断父节点下是否有子节点。
		TbContentCategoryExample e = new TbContentCategoryExample();
		Criteria c = e.createCriteria();
		c.andParentIdEqualTo(parentId);
		List<TbContentCategory> list2 = contentCategoryMapper.selectByExample(e);
		if(list2 == null && list2.size() == 0){
			TbContentCategory parentCate = contentCategoryMapper.selectByPrimaryKey(parentId);
			// 3、如果没有子节点把父节点的isparent改为false
			parentCate.setIsParent(false);
		}
		
		return E3Result.ok();
	}

}
