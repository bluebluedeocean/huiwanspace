package com.example.project.gonghui10.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.adapter.ActivityFragmentAdapter;
import com.example.project.gonghui10.view.TitleBarView;


import java.util.ArrayList;
import java.util.List;


public class ActivitysFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private ViewPager viewPager;
    private SwipeRefreshLayout refreshLayout;
    private ActivityFragment01 activityFragment01;
    private ActivityFragment02 activityFragment02;
    private ActivityFragment03 activityFragment03;
    private LinearLayout[] linearLayouts;
    private TextView[] textViews;
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_activity, null);
        setlinearLayouts();
        settextview();
        findView();
        init();
        refresh();
        return mBaseView;
    }

    private void refresh() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void findView() {
        viewPager = (ViewPager) mBaseView.findViewById(R.id.activity_viewpager);
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        refreshLayout = (SwipeRefreshLayout) mBaseView.findViewById(R.id.refresher);

        activityFragment01 = new ActivityFragment01();
        activityFragment02 = new ActivityFragment02();
        activityFragment03 = new ActivityFragment03();
        fragmentList.add(activityFragment01);
        fragmentList.add(activityFragment02);
        fragmentList.add(activityFragment03);

        //设置显示哪页
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(new ActivityFragmentAdapter(getChildFragmentManager(),fragmentList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                resetlaybg();
                linearLayouts[arg0]
                        .setBackgroundResource(R.drawable.linearlayout01s);
                textViews[arg0].setTextColor(getResources().getColor(
                        R.color.textcolor));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });
    }

    private void init() {
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.activity);
    }

    /** 初始化linerlayout */
    public void setlinearLayouts() {
        linearLayouts = new LinearLayout[3];
        linearLayouts[0] = (LinearLayout) mBaseView.findViewById(R.id.lay1);
        linearLayouts[1] = (LinearLayout) mBaseView.findViewById(R.id.lay2);
        linearLayouts[2] = (LinearLayout) mBaseView.findViewById(R.id.lay3);
        linearLayouts[0].setBackgroundResource(R.drawable.linearlayout01s);

        linearLayouts[0].setOnClickListener(this);
        linearLayouts[1].setOnClickListener(this);
        linearLayouts[2].setOnClickListener(this);
    }

    /** 初始化textview */
    public void settextview() {
        textViews = new TextView[3];
        textViews[0] = (TextView) mBaseView.findViewById(R.id.fratext1);
        textViews[1] = (TextView) mBaseView.findViewById(R.id.fratext2);
        textViews[2] = (TextView) mBaseView.findViewById(R.id.fratext3);
        textViews[0].setTextColor(getResources()
                .getColor(R.color.textcolor));
    }

    /** 点击linerlayout实现切换fragment的效果 */
    public void LayoutOnclick(View v) {
        // 每次点击都重置linearLayouts的背景、textViews字体颜色
        switch (v.getId()) {
            case R.id.lay1:
                resetlaybg();
                viewPager.setCurrentItem(0);
                linearLayouts[0].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[0].setTextColor(getResources().getColor(
                        R.color.textcolor));
                break;

            case R.id.lay2:
                resetlaybg();
                viewPager.setCurrentItem(1);
                linearLayouts[1].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[1].setTextColor(getResources().getColor(
                        R.color.textcolor));

                break;
            case R.id.lay3:
                resetlaybg();
                viewPager.setCurrentItem(2);
                linearLayouts[2].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[2].setTextColor(getResources().getColor(
                        R.color.textcolor));

                break;
        }
    }

    /** 重置linearLayouts、textViews */
    public void resetlaybg() {
        for (int i = 0; i < 3; i++) {
            // linearLayouts[i].setBackgroundResource(R.drawable.ai);
            textViews[i].setTextColor(getResources().getColor(R.color.black));
            linearLayouts[i].setBackgroundResource(R.drawable.linearlayout01);
        }

    }

    @Override
    public void onClick(View v) {
        // 每次点击都重置linearLayouts的背景、textViews字体颜色
        switch (v.getId()) {
            case R.id.lay1:
                resetlaybg();
                viewPager.setCurrentItem(0);
                linearLayouts[0].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[0].setTextColor(getResources().getColor(
                        R.color.textcolor));
                break;

            case R.id.lay2:
                resetlaybg();
                viewPager.setCurrentItem(1);
                linearLayouts[1].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[1].setTextColor(getResources().getColor(
                        R.color.textcolor));

                break;
            case R.id.lay3:
                resetlaybg();
                viewPager.setCurrentItem(2);
                linearLayouts[2].setBackgroundResource(R.drawable.linearlayout01s);
                textViews[2].setTextColor(getResources().getColor(
                        R.color.textcolor));

                break;
        }
    }
}
