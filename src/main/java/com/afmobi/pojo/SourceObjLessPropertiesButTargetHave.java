package com.afmobi.pojo;

public class SourceObjLessPropertiesButTargetHave {
	
	private String session;
	private String btoken;
	
	private String text;
	private String purview;
	private String afid;
	
	
	public String toString() {
		return "SourceObjLessPropertiesButTargetHave [session=" + session + ", btoken=" + btoken + ", text=" + text
				+ ", purview=" + purview + ", afid=" + afid + "]";
	}
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public String getBtoken() {
		return btoken;
	}
	public void setBtoken(String btoken) {
		this.btoken = btoken;
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

}
