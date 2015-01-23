package com.afmobi.test.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.afmobi.pojo.SourceObjLessPropertiesButTargetHave;
import com.afmobi.pojo.SourceObj;
import com.afmobi.pojo.TargetObj;

public class CopyPojoTest {

	@Test
	/**
	 * 在pojo中的属性字段名称一致时可用
	 */
	public void copyAlikeProperties() throws CloneNotSupportedException{
		
		TargetObj target = new TargetObj();
		SourceObj source = (SourceObj) makeSourceData();
		
		BeanUtils.copyProperties(source.clone(), target);
		
		System.out.println("转化后的target对象数据 : "+target.toString());
		
		target.getMap().put("tag", "value111");//修改复制之后的target数据
		
		System.out.println("修改target对象后source数据 : "+source.toString());
		
		//相等，浅复制
		Assert.assertEquals("value111", source.getMap().get("tag"));
		
	}
	
	@Test
	public void sourceObjLessPropertiesButTargetHave(){
		SourceObjLessPropertiesButTargetHave sourceObjLessPropertiesButTargetHave = new SourceObjLessPropertiesButTargetHave();
		sourceObjLessPropertiesButTargetHave.setText("mytext");
		
		TargetObj target = new TargetObj();
		
		BeanUtils.copyProperties(sourceObjLessPropertiesButTargetHave, target);
		System.out.println("转化后的target对象数据 : "+target.toString());
		Assert.assertEquals("mytext", target.getText());
		
	}
	
	private Object makeSourceData(){
		SourceObj source = new SourceObj();
		source.setAfid("abcd1234");
		source.setBtoken("1123");
		source.setPurview("dfdfafa");
		source.setSession("122.522.52");
		source.setText("This is test!");
		source.setTitle("this is title!");
		List<String> list = new ArrayList<String>();
		list.add("abcd123");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", "12233456");
		Set<String> set  = new HashSet<String>();
		set.add("52486248");
		source.setList(list);
		source.setMap(map);
		source.setSet(set);
		System.out.println("新生成的source对象数据："+source.toString());
		return source;
	}
	
	
}
