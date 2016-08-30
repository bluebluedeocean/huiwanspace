package com.example.project.gonghui10.service;

import com.example.project.gonghui10.model.CampusModel;
import com.example.project.gonghui10.model.CityModel;
import com.example.project.gonghui10.model.DistrictModel;
import com.example.project.gonghui10.model.ProvinceModel;
import com.example.project.gonghui10.model.SchoolModel;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlParserHandler extends DefaultHandler {

	/**
	 * 存储所有的解析对象
	 */
	private List<ProvinceModel> provinceList = new ArrayList<ProvinceModel>();

	public XmlParserHandler() {

	}

	public List<ProvinceModel> getDataList() {
		return provinceList;
	}

	@Override
	public void startDocument() throws SAXException {
		// 当读到第一个开始标签的时候，会触发这个方法
	}

	ProvinceModel provinceModel = new ProvinceModel();
	SchoolModel schoolModel = new SchoolModel();
	CampusModel campusModel = new CampusModel();

	@Override
	public void startElement(String uri, String localName, String qName,
							 Attributes attributes) throws SAXException {
		// 当遇到开始标记的时候，调用这个方法
		if (qName.equals("province")) {
			provinceModel = new ProvinceModel();
			provinceModel.setName(attributes.getValue(0));
			provinceModel.setId(attributes.getValue(1));
			provinceModel.setSchoolList(new ArrayList<SchoolModel>());
		} else if (qName.equals("school")) {
			schoolModel = new SchoolModel();
			schoolModel.setId(attributes.getValue(0));
			schoolModel.setName(attributes.getValue(1));
			schoolModel.setPid(attributes.getValue(2));
			schoolModel.setCampusList(new ArrayList<CampusModel>());
		} else if (qName.equals("campus")) {
			campusModel = new CampusModel();
			campusModel.setId(attributes.getValue(0));
			campusModel.setName(attributes.getValue(1));
			campusModel.setpId(attributes.getValue(2));
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// 遇到结束标记的时候，会调用这个方法
		if (qName.equals("campus")) {
			schoolModel.getCampusList().add(campusModel);
		} else if (qName.equals("school")) {
			provinceModel.getSchoolList().add(schoolModel);
		} else if (qName.equals("province")) {
			provinceList.add(provinceModel);
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
	}
}
