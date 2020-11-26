package com.routing;

import java.util.List;

public class Route {
 
	private Integer id ;
	
	private String uri;
	 
	private List <Form> formList;
	 
	private List <ALink> aLinkList;
	 
	private String className;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Form> getFormList() {
		return formList;
	}

	public void setFormList(List<Form> formList) {
		this.formList = formList;
	}

	public List<ALink> getaLinkList() {
		return aLinkList;
	}

	public void setaLinkList(List<ALink> aLinkList) {
		this.aLinkList = aLinkList;
	}
	
}
 
