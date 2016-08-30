package com.example.project.gonghui10.model;

public class CampusModel {
	private String name;
	private String id;
	private String address;
	private String pId;

	public CampusModel() {
		super();
	}

	public CampusModel(String name, String id, String address) {
		super();
		this.name = name;
		this.id = id;
		this.address = address;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "CampusModel{" +
				"name='" + name + '\'' +
				", id='" + id + '\'' +
				", address='" + address + '\'' +
				'}';
	}
}
