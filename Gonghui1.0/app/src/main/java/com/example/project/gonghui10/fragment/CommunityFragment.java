package com.example.project.gonghui10.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.activity.UserNickActivity;
import com.example.project.gonghui10.net.textuserinfo;
import com.example.project.gonghui10.util.RoundImageLoader;
import com.example.project.gonghui10.view.TitleBarView;

import org.json.JSONException;
import org.json.JSONObject;


public class CommunityFragment extends Fragment {
    private Context mContext;
    private View mBaseView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_community, null);

        return mBaseView;
    }
}
