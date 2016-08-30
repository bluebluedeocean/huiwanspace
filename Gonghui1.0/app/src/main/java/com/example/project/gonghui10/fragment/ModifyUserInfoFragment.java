package com.example.project.gonghui10.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.gonghui10.R;

/**
 * Created by Yu on 2016/8/28 0028.
 */
public class ModifyUserInfoFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.modifyuserinfo_layout,container,false);
        return root;
    }
}
