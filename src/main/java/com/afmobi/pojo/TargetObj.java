package com.afmobi.pojo;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.afmobi.util.Util;


public class TargetObj {
	
	private String title;
	private String text;
	private String purview;
	private String afid;
	
	private List<String> list;
	
	private Map<String, Object> map;
	
	private Set<String> set;
	
	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getPurview() {
		return purview;
	}

	public void setPurview(String purview) {
		this.purview = purview;
	}

	public String getAfid() {
		return afid;
	}

	public void setAfid(String afid) {
		this.afid = afid;
	}

	@Override
	public String toString() {
		return Util.BeanToJson(this);
	}
	

}
