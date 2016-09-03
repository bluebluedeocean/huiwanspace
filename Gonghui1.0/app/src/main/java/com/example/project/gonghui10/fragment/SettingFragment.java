package com.example.project.gonghui10.fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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



public class SettingFragment extends Fragment {


    String face;
    String userName;
    String pubNumber;
    String attention;
    String follower;
    String about;


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
    private TextView headtext_nick;
    private ImageView headpic;
    private TextView attention_num;
    private TextView follower_num;
    private TextView activity_num;
    private TextView about_tv;
    private SwipeRefreshLayout refreshLayout;

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
        gotoNickAct();
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

    private void setHeadpic(JSONObject object) throws JSONException {
        RoundImageLoader roundImageLoader = new RoundImageLoader(getActivity());
        face = object.getString("face");
        roundImageLoader.DisplayImage(face,headpic);
    }

    private void setContent(JSONObject object) throws JSONException {
        userName = object.getString("username");
        about = object.getString("about");
        pubNumber = object.getString("pubNumber");
        attention = object.getString("attention");
        follower = object.getString("follower");

        headtext_nick.setText(userName);
        about_tv.setText("个人简介:"+about);
        activity_num.setText(pubNumber);
        attention_num.setText(attention);
        follower_num.setText(follower);

    }

    private void gotoNickAct() {
        userNickAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getActivity().getApplicationContext();
                Intent intent = new Intent(context,UserNickActivity.class);
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
        headtext_nick = (TextView) mBaseView.findViewById(R.id.headtext_nick);
        headpic = (ImageView) mBaseView.findViewById(R.id.headpic);
        about_tv = (TextView) mBaseView.findViewById(R.id.about_tv);
        attention_num = (TextView) mBaseView.findViewById(R.id.head_item_following_number);
        follower_num = (TextView) mBaseView.findViewById(R.id.head_item_follower_number);
        activity_num = (TextView) mBaseView.findViewById(R.id.head_item_act_number);
        refreshLayout = (SwipeRefreshLayout) mBaseView.findViewById(R.id.refresher);
    }

    private void init() {
        mTitleBarView.setCommonTitle(View.GONE, View.VISIBLE, View.GONE, View.GONE);
        mTitleBarView.setTitleText(R.string.mime);

        new textuserinfo(Config.getCacheSessionId(getActivity()), new textuserinfo.SuccessCallBack() {
            @Override
            public void onSuccess(org.json.JSONObject obj) {
                try {
                    setHeadpic(obj);
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

}
