package com.example.project.gonghui10.model;

import java.util.List;

public class SchoolModel {
	private String name;
	private String id;
	private String pid;
	private List<CampusModel> campusList;

	public SchoolModel() {
		super();
	}

	public SchoolModel(String name,String id,String pid, List<CampusModel> campusList) {
		super();
		this.name = name;
		this.id = id;
		this.pid = pid;
		this.campusList = campusList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getPid() {
		return pid;
	}

	public List<CampusModel> getCampusList() {
		return campusList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public void setCampusList(List<CampusModel> campusList) {
		this.campusList = campusList;
	}

	@Override
	public String toString() {
		return "SchoolModel{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", pid='" + pid + '\'' +
				", campusList=" + campusList +
				'}';
	}
}
