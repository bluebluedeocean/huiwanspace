package com.example.project.gonghui10.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.example.project.gonghui10.R;

public class MeActivity extends Activity {

    private LinearLayout joinedAct;
    private LinearLayout likedAct;
    private LinearLayout praisedAct;
    private LinearLayout userNickAct;
    private LinearLayout headact;
    private LinearLayout headfollow;
    private LinearLayout headfans;
    private LinearLayout setting;
    private LinearLayout grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_mine);

//        参加的活动点击进入子界面
        joinedAct = (LinearLayout) findViewById(R.id.item_joinactvity);
        joinedAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, JoinedActActivity.class);
                startActivity(intent);
            }
        });

//         收藏的活动点击进入子界面
        likedAct = (LinearLayout) findViewById(R.id.item_collectedactivity);
        likedAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, LikedActActivity.class);
                startActivity(intent);
            }
        });

//        赞过的活动点击进入子界面
        praisedAct = (LinearLayout) findViewById(R.id.item_praisedactivity);
        praisedAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, PraisedActActivity.class);
                startActivity(intent);
            }
        });

//        点击头像栏进入用户信息设置
        userNickAct = (LinearLayout) findViewById(R.id.head);
        userNickAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, UserNickActivity.class);
                startActivity(intent);
            }
        });

//        头像栏活动按钮点击进入子界面
        headact = (LinearLayout) findViewById(R.id.head_item_act);
        headact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, ActHeadItemActivity.class);
                startActivity(intent);
            }
        });

//        头像栏关注点击进入子界面
        headfollow = (LinearLayout) findViewById(R.id.head_item_following);
        headfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, FollowHeadItemActivity.class);
                startActivity(intent);
            }
        });

//        头像栏粉丝点击进入子界面
        headfans = (LinearLayout) findViewById(R.id.head_item_follower);
        headfans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, FansHeadItemActivity.class);
                startActivity(intent);
            }
        });



//        等级栏点击量进入子界面
        grade = (LinearLayout) findViewById(R.id.item_grade);
        grade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, GradeActivity.class);
                startActivity(intent);
            }
        });


//        设置栏点击进入子界面
        setting = (LinearLayout) findViewById(R.id.item_usersetting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MeActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }
}
