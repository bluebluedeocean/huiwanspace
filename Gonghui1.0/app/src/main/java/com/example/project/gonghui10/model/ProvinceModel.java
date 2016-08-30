package com.example.project.gonghui10.model;

import java.util.List;

public class ProvinceModel {
	private String name;
	/**
	 *
	 */
	private String id;
	private List<SchoolModel> schoolList;


	private List<CityModel> cityList;
	
	public ProvinceModel() {
		super();
	}

	/**
	 *
	 * @param name
	 * @param id
	 * @param schoolList
     */
	public ProvinceModel(String name,String id,List<SchoolModel> schoolList) {
		super();
		this.name = name;
		this.id = id;
		this.schoolList = schoolList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<SchoolModel> getSchoolList() {
		return schoolList;
	}

	public void setSchoolList(List<SchoolModel> schoolList) {
		this.schoolList = schoolList;
	}

	public ProvinceModel(String name, List<CityModel> cityList) {
		super();
		this.name = name;
		this.cityList = cityList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CityModel> getCityList() {
		return cityList;
	}

	public void setCityList(List<CityModel> cityList) {
		this.cityList = cityList;
	}

	@Override
	public String toString() {
		return "ProvinceModel [name=" + name + ", cityList=" + cityList + "]";
	}
	
}
