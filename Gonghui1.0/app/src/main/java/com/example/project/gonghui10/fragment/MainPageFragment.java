package com.example.project.gonghui10.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.activity.ChoseLocationActivity;
import com.example.project.gonghui10.util.ChoseLocationUtil;
import com.example.project.gonghui10.view.TitleBarView;

/**
 * Created by 19950 on 2016/7/11.
 */
public class MainPageFragment extends Fragment {

    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private FragmentManager fragmentManager;
    private String mlocation;
    private Button location;
   // private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext=getActivity();
        mBaseView=inflater.inflate(R.layout.fragment_mainpage,null);
        Bundle bundle = getArguments();
        //token = bundle.getString(Config.KEY_TOKEN);
        findView();
        init();
        /*
            嵌套子Fragment界面，用来显示主页信息
         */
        fragmentManager = getChildFragmentManager();
        FragmentListMessage fragmentListMessage = new FragmentListMessage();
        fragmentListMessage.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.fragment_home_page,fragmentListMessage).commit();

        return mBaseView;
    }

    private void findView(){
        mTitleBarView=(TitleBarView) mBaseView.findViewById(R.id.title_bar);
        location = (Button) mBaseView.findViewById(R.id.title_btn_left);
        mlocation = Config.getCacheLocation(getActivity());
        String locationtext = mlocation;
        if (mlocation.length()>10) {
            locationtext = mlocation.substring(0,10) + "...";
        }
        Log.i("info","mLocation---------------------------->" + mlocation);
        location.setText(locationtext);
    }

    private void init(){
        mTitleBarView.setCommonTitle(View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.mainPage);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), ChoseLocationActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
