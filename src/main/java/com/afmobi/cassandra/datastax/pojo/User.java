package com.afmobi.cassandra.datastax.pojo;

public class User {

	private int uid;
	
	private String name;
	
	private String passwd;
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	@Override
	public String toString() {
		return "uid:"+uid+"    name:"+name+"    passwd:"+passwd;
	}
	
}
