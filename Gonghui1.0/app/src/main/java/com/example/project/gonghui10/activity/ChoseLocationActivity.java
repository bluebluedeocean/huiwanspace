package com.example.project.gonghui10.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.util.ChoseLocationUtil;
import com.example.project.gonghui10.util.SysApplication;

/**
 * Created by Meng on 2016/8/8.
 */
public class ChoseLocationActivity extends ChoseLocationUtil{
    private Spinner provinceSpinner;
    private Spinner schoolSpinner;
    private Spinner campusSpinner;
    private TextView tv_result;
    private Button sure_location;
    private String location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_chose);
        SysApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        provinceSpinner = (Spinner) this.findViewById(R.id.province);
        schoolSpinner = (Spinner) this.findViewById(R.id.city);
        campusSpinner = (Spinner) this.findViewById(R.id.district);
        tv_result = (TextView) this.findViewById(R.id.txt);
        sure_location = (Button) this.findViewById(R.id.sure_location);
        initSchoolDatas();
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mProvinceDatas);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCurrentProviceName = mProvinceDatas[position];
                pId = mProvinceIds[position];
                String[] schools = mSchoolDatasMap.get(mCurrentProviceName);
                if (schools == null) {
                    schools = new String[] { "" };
                }
                ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(ChoseLocationActivity.this, android.R.layout.simple_list_item_1, schools);
                schoolSpinner.setAdapter(schoolAdapter);
                mCurrentSchoolName = mSchoolDatasMap.get(mCurrentProviceName)[0];
                sId = mSchoolIdsMap.get(mCurrentProviceName)[0];
                String[] campus = mCampusDatasMap.get(mCurrentSchoolName);
                mCurrentCampusName = campus[0];
                cId = mCampusIdsMap.get(mCurrentSchoolName)[0];
                if (campus == null) {
                    campus = new String[] { "" };
                }
                ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(ChoseLocationActivity.this, android.R.layout.simple_list_item_1, campus);
                campusSpinner.setAdapter(campusAdapter);
                tv_result.setText(mCurrentProviceName+","+mCurrentSchoolName+","+mCurrentCampusName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCurrentSchoolName = mSchoolDatasMap.get(mCurrentProviceName)[position];
                sId = mSchoolIdsMap.get(mCurrentProviceName)[position];
                String[] areas = mCampusDatasMap.get(mCurrentSchoolName);
                mCurrentCampusName = areas[0];
                cId = mCampusIdsMap.get(mCurrentSchoolName)[0];
                if (areas == null) {
                    areas = new String[] { "" };
                }
                ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(ChoseLocationActivity.this, android.R.layout.simple_list_item_1, areas);
                campusSpinner.setAdapter(campusAdapter);
                tv_result.setText(mCurrentProviceName+","+mCurrentSchoolName+","+mCurrentCampusName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner)parent;
                mCurrentCampusName = (String) sp.getItemAtPosition(position);
                cId = mCampusIdsMap.get(mCurrentSchoolName)[position];
                tv_result.setText(mCurrentProviceName+","+mCurrentSchoolName+","+mCurrentCampusName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sure_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = mCurrentCampusName;
                if(mCurrentProviceName.equals("全国")) {
                    location = mCurrentProviceName;
                }
                Config.cacheLocationId(ChoseLocationActivity.this,pId,sId,cId);
                Config.cacheLocation(ChoseLocationActivity.this,location);
                Intent in = new Intent();
                in.setClass(ChoseLocationActivity.this,MainActivity.class);
                startActivity(in);
                finish();
            }
        });
    }

}
