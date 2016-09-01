package com.example.project.gonghui10.activity;
import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.net.textuserinfo;
import com.example.project.gonghui10.util.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Yu on 2016/8/23 0023.
 *
 */
public class UserNickActivity extends Activity {

    private ImageView faceIv;
    private TextView nickTv;
    private TextView emailTv;
    private TextView phonetv;
    private TextView schoolTv;
    private TextView campusTv;
    private TextView sextv;
    private TextView abouttv;

    private String face;
    private String nick;
    private String email;
    private String phone;
    private String school;
    private String campus;
    private String sex;
    private String about;

    private SwipeRefreshLayout refreshlayout;




    public Button gotoBtn;
    public LinearLayout backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usernick_layout);
        findView();
        init();
        gotomodify();
        goback();
        refresh();
    }

    private void refresh() {
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refreshlayout.setRefreshing(false);
            }
        });
    }

    private void setContent(JSONObject obj) throws JSONException {
        ImageLoader il = new ImageLoader(this);
        face = obj.getString("face");
        il.DisplayImage(face,faceIv);

        nick = obj.getString("username");
        email = obj.getString("email");
        phone = obj.getString("phone");
        school = obj.getString("sName");
        campus = obj.getString("cName");
        sex = obj.getString("sex");
        about = obj.getString("about");

        nickTv.setText(nick);
        emailTv.setText(email);
        phonetv.setText(phone);
        schoolTv.setText(school);
        campusTv.setText(campus);
        sextv.setText(sex);
        abouttv.setText(about);
    }

    private void init() {
        new textuserinfo(Config.getCacheSessionId(this), new textuserinfo.SuccessCallBack() {
            @Override
            public void onSuccess(org.json.JSONObject obj) {
                try {
                    setContent(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new textuserinfo.FailCallBack() {
            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void findView() {
        faceIv = (ImageView) findViewById(R.id.headpic_item);
        nickTv = (TextView) findViewById(R.id.usersetting_nick);
        emailTv = (TextView) findViewById(R.id.usersetting_email);
        phonetv = (TextView) findViewById(R.id.usersetting_mobile);
        schoolTv = (TextView) findViewById(R.id.usersetting_school);
        campusTv = (TextView) findViewById(R.id.usersetting_college);
        sextv = (TextView) findViewById(R.id.usersetting_sex);
        abouttv = (TextView) findViewById(R.id.usersetting_about);
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.refresher);
    }

    private void gotomodify() {
        gotoBtn = (Button) findViewById(R.id.modify_userinfo_btn);
        gotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserNickActivity.this,ModifyUserInfoActivity.class);
                startActivity(i);
            }
        });

    }

    private void goback() {
        backBtn = (LinearLayout) findViewById(R.id.backbtn1);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
