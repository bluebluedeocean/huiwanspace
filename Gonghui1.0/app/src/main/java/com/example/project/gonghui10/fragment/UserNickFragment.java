package com.example.project.gonghui10.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by Yu on 2016/8/28 0028.
 */
public class UserNickFragment extends android.support.v4.app.Fragment {

    private Context mContext;
     View mView;
    LinearLayout backbtn;

    ImageView headpic_iv;
    TextView userName_tv;
    TextView eamil_tv;
    TextView phone_tv;
    Button gotoModify_btn;

    TextView cityName_tv;
    TextView schoolName_tv;
    TextView campusName_tv;
    TextView sex_tv;
    TextView about_tv;
    SwipeRefreshLayout refreshLayout;

    String head;
    String userName;
    String eamil;
    String phone;
    String cityName;
    String schoolName;
    String campusName;
    String sex;
    String about;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mView = inflater.inflate(R.layout.usernick_layout,null);
        findView();
        initView();
        refresh();
        gotoModify();
        return mView;
    }

    private void gotoModify() {
        gotoModify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().addToBackStack(null).
                        replace(R.id.fragment_page,new ModifyUserInfoFragment()).commit();
            }
        });

    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void setHead(JSONObject obj) throws JSONException {
        ImageLoader imageLoader = new ImageLoader(mContext);
        head = obj.getString("face");
        imageLoader.DisplayImage(head,headpic_iv);
    }

    private void setContent(JSONObject obj) throws JSONException {
        userName = obj.getString("username");
        eamil = obj.getString("email");
        phone = obj.getString("phone");
        schoolName = obj.getString("sName");
        campusName = obj.getString("cName");
        sex = obj.getString("sex");
        about = obj.getString("about");


        userName_tv.setText(userName);
        eamil_tv.setText(eamil);
        phone_tv.setText(phone);
        schoolName_tv.setText(schoolName);
        campusName_tv.setText(campusName);
        sex_tv.setText(sex);
        about_tv.setText(about);

    }

    private void findView() {
        headpic_iv = (ImageView) mView.findViewById(R.id.headpic_item);
        userName_tv = (TextView) mView.findViewById(R.id.usersetting_nick);
        eamil_tv = (TextView) mView.findViewById(R.id.usersetting_email);
        phone_tv = (TextView) mView.findViewById(R.id.usersetting_mobile);
        cityName_tv = (TextView) mView.findViewById(R.id.usersetting_city);
        schoolName_tv = (TextView) mView.findViewById(R.id.usersetting_school);
        campusName_tv = (TextView) mView.findViewById(R.id.usersetting_college);
        sex_tv = (TextView) mView.findViewById(R.id.usersetting_sex);
        about_tv = (TextView) mView.findViewById(R.id.usersetting_about);
        refreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.modify_refresher);
        gotoModify_btn = (Button) mView.findViewById(R.id.modify_userinfo_btn);
        backbtn = (LinearLayout) mView.findViewById(R.id.backbtn1);
    }

    private void initView() {
        new textuserinfo(Config.getCacheSessionId(mContext), new textuserinfo.SuccessCallBack() {
            @Override
            public void onSuccess(org.json.JSONObject obj) {
                try {
                    setHead(obj);
                    setContent(obj);
                    System.out.println(Config.getCacheSessionId(mContext));
                    System.out.println(obj.getString("face")+obj.getString("username"));
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

}
