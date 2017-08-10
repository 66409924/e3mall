package cn.e3mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.E3Result;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {
	
	@Autowired
	private TbItemDescMapper itemDescMapper;

	@Override
	public E3Result getDescById(Long id) {
		//System.out.println(id);
		TbItemDesc tbItemDesc = itemDescMapper.selectByPrimaryKey(id);
		E3Result e3Result = new E3Result(tbItemDesc);
		return e3Result;
	}

}
