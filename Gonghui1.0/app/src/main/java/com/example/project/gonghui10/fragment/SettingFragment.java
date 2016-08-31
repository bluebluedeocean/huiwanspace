package com.example.project.gonghui10.fragment;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.activity.UserNickActivity;
import com.example.project.gonghui10.view.TitleBarView;


public class SettingFragment extends Fragment {

    private Context mContext;
    private View mBaseView;
    private TitleBarView mTitleBarView;
    private ProgressDialog progressDialog;

    private LinearLayout joinedAct;
    private LinearLayout likedAct;
    private LinearLayout praisedAct;
    private LinearLayout userNickAct;
    private LinearLayout headact;
    private LinearLayout headfollow;
    private LinearLayout headfans;
    private LinearLayout setting;
    private LinearLayout grade;



    public void showDialog(String s) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(s);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void closeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        mBaseView = inflater.inflate(R.layout.fragment_mine, null);
        findView();
        init();
        gotoUserNick();
        return mBaseView;
    }


    private void gotoUserNick() {
        userNickAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,UserNickActivity.class);
                startActivity(intent);
            }
        });

    }

    private void findView() {
        mTitleBarView = (TitleBarView) mBaseView.findViewById(R.id.title_bar);
        joinedAct = (LinearLayout) mBaseView.findViewById(R.id.item_joinactvity);
        likedAct = (LinearLayout) mBaseView.findViewById(R.id.item_collectedactivity);
        praisedAct = (LinearLayout) mBaseView.findViewById(R.id.item_praisedactivity);
        headact = (LinearLayout) mBaseView.findViewById(R.id.head_item_act);
        headfollow = (LinearLayout) mBaseView.findViewById(R.id.head_item_following);
        headfans = (LinearLayout) mBaseView.findViewById(R.id.head_item_follower);
        setting = (LinearLayout) mBaseView.findViewById(R.id.item_usersetting);
        grade = (LinearLayout) mBaseView.findViewById(R.id.item_grade);
        userNickAct = (LinearLayout) mBaseView.findViewById(R.id.head);
    }

    private void init() {
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.mime);
    }




}
