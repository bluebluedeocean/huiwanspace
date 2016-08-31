package com.example.project.gonghui10.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.project.gonghui10.model.CampusModel;
import com.example.project.gonghui10.model.CityModel;
import com.example.project.gonghui10.model.DistrictModel;
import com.example.project.gonghui10.model.ProvinceModel;
import com.example.project.gonghui10.model.SchoolModel;
import com.example.project.gonghui10.net.Campus;
import com.example.project.gonghui10.service.XmlParserHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChoseLocationUtil extends Activity {


    protected String[] mProvinceDatas;

    protected String[] mProvinceIds;

    protected Map<String, String[]> mSchoolDatasMap = new HashMap<>();
    protected Map<String, String[]> mCampusDatasMap = new HashMap<>();
    protected Map<String, String[]> mSchoolIdsMap = new HashMap<>();
    protected Map<String, String[]> mCampusIdsMap = new HashMap<>();

    protected String mCurrentProviceName;
    protected String mCurrentSchoolName;
    protected String mCurrentCampusName;

    protected String pId,sId,cId;


    protected void initSchoolDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = getAssets();
        try {
            InputStream input = this.getClass().getClassLoader().getResourceAsStream("assets/"+"schools_data.xml");
            //InputStream input = asset.open("school_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                pId = provinceList.get(0).getId();
                List<SchoolModel> schoolList = provinceList.get(0).getSchoolList();
                if (schoolList != null && !schoolList.isEmpty()) {
                    mCurrentSchoolName = schoolList.get(0).getName();
                    sId = schoolList.get(0).getId();
                    List<CampusModel> campusList = schoolList.get(0).getCampusList();
                    if (campusList != null && !campusList.isEmpty()) {
                        mCurrentCampusName = campusList.get(0).getName();
                        cId = campusList.get(0).getId();
                       // mCurrentZipCode = districtList.get(0).getZipcode();
                    }
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            mProvinceIds = new String[provinceList.size()];
            Log.i("info","choseLacationUtil mProvince.toString:" + mProvinceDatas.toString());
            Log.i("info","choseLacationUtil provinceList.toString:" + provinceList.toString());

            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                mProvinceIds[i] = provinceList.get(i).getId();
                List<SchoolModel> schoolList = provinceList.get(i).getSchoolList();
                String[] schoolNames = new String[schoolList.size()];
                String[] schoolIds = new String[schoolList.size()];
                for (int j = 0; j < schoolList.size(); j++) {
                    // 遍历省下面的所有学校的数据
                    schoolNames[j] = schoolList.get(j).getName();
                    schoolIds[j] = schoolList.get(j).getId();
                    List<CampusModel> campusList = schoolList.get(j).getCampusList();
                    String[] campusNameAray = new String[campusList.size()];
                    String[] campusIdsAray = new String[campusList.size()];
                    //CampusModel[] campusArray = new CampusModel[campusList.size()];
                    for (int k = 0; k < campusList.size(); k++) {
                        // 遍历学校下面所有校区的数据
                        CampusModel campusModel = new CampusModel(campusList.get(k).getName(), campusList.get(k).getId(), campusList.get(k).getAddress());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        //mZipcodeDatasMap.put(campusList.get(k).getName(), districtList.get(k).getZipcode());
                        //campusArray[k] = campusModel;
                        campusNameAray[k] = campusModel.getName();
                        campusIdsAray[k] = campusModel.getId();
                    }
                    // 学校-校区的数据，mCampusDatasMap
                    mCampusDatasMap.put(schoolNames[j], campusNameAray);
                    mCampusIdsMap.put(schoolNames[j], campusIdsAray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mSchoolDatasMap.put(provinceList.get(i).getName(), schoolNames);
                mSchoolIdsMap.put(provinceList.get(i).getName(), schoolIds);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }


}
