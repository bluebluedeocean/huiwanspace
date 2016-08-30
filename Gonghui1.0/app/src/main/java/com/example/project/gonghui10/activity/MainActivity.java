package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.fragment.MainPageFragment;
import com.example.project.gonghui10.fragment.SettingFragment;
import com.example.project.gonghui10.util.SysApplication;

import java.util.HashMap;

public class MainActivity extends Activity {
    protected static final String TAG = "MainActivity";
    private Context mContext;
    private ImageButton mMain,mConstact,mDeynaimic,mSetting;
    private View mPopView;
    private View currentButton;



    private TextView app_cancle;
    private TextView app_exit;
    private TextView app_change;

    private int mLevel=1;
    private PopupWindow mPopupWindow;
    private LinearLayout buttomBarGroup;
    //private String token;
    private String sessionId;
    private Fragment mfragment;
    MainPageFragment mainPageFragment;
    SettingFragment settingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionId = Config.getCacheSessionId(this);
        SysApplication.getInstance().addActivity(this);
        mContext=this;

        findView();
        init();

    }






    private void findView(){
        mPopView= LayoutInflater.from(mContext).inflate(R.layout.app_exit, null);
        buttomBarGroup=(LinearLayout) findViewById(R.id.buttom_bar_group);
        mMain=(ImageButton) findViewById(R.id.buttom_main);
        mConstact=(ImageButton) findViewById(R.id.buttom_constact);
        mDeynaimic=(ImageButton) findViewById(R.id.buttom_deynaimic);
        mSetting=(ImageButton) findViewById(R.id.buttom_setting);

        app_cancle=(TextView) mPopView.findViewById(R.id.app_cancle);
        app_change=(TextView) mPopView.findViewById(R.id.app_change_user);
        app_exit=(TextView) mPopView.findViewById(R.id.app_exit);
    }

    private void init(){
        mMain.setOnClickListener(mainPageOnClickListener);
        mConstact.setOnClickListener(constactOnClickListener);
        mDeynaimic.setOnClickListener(deynaimicOnClickListener);
        mSetting.setOnClickListener(settingOnClickListener);

        mfragment = new Fragment();
        mainPageFragment=new MainPageFragment();
        settingFragment=new SettingFragment();

        mMain.performClick();

        mPopupWindow=new PopupWindow(mPopView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);

        app_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        app_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                ((Activity)mContext).overridePendingTransition(R.anim.activity_up, R.anim.fade_out);
                finish();
            }
        });

        app_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private OnClickListener mainPageOnClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mfragment!=mainPageFragment) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                if(!mainPageFragment.isAdded()) {
                    ft.hide(mfragment).add(R.id.fl_content,mainPageFragment).commit();
                }else {
                    ft.hide(mfragment).show(mainPageFragment).commit();
                }
                mfragment = mainPageFragment;
                //ft.replace(R.id.fl_content, mainPageFragment,MainActivity.TAG);
                setButton(v);
            }

        }
    };

    private View.OnClickListener constactOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mfragment!=settingFragment) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();

                if(!settingFragment.isAdded()) {
                    ft.hide(mfragment).add(R.id.fl_content,settingFragment).commit();
                }else {
                    ft.hide(mfragment).show(settingFragment).commit();
                }
                mfragment = settingFragment;
                //ft.replace(R.id.fl_content, mainPageFragment,MainActivity.TAG);
                setButton(v);
            }

           /* FragmentManager fm=getFragmentManager();
            FragmentTransaction ft=fm.beginTransaction();

            ft.replace(R.id.fl_content, settingFragment,MainActivity.TAG);
            setButton(v);
            ft.commit();*/
        }
    };

    private View.OnClickListener deynaimicOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mfragment!=settingFragment) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();

                if(!settingFragment.isAdded()) {
                    ft.hide(mfragment).add(R.id.fl_content,settingFragment).commit();
                }else {
                    ft.hide(mfragment).show(settingFragment).commit();
                }
                mfragment = settingFragment;
                //ft.replace(R.id.fl_content, mainPageFragment,MainActivity.TAG);
                setButton(v);
            }
        }
    };

    private View.OnClickListener settingOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mfragment!=settingFragment) {
                FragmentManager fm=getFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();

                if(!settingFragment.isAdded()) {
                    ft.hide(mfragment).add(R.id.fl_content,settingFragment).commit();
                }else {
                    ft.hide(mfragment).show(settingFragment).commit();
                }
                mfragment = settingFragment;
                //ft.replace(R.id.fl_content, mainPageFragment,MainActivity.TAG);
                setButton(v);
            }
        }
    };

    private void setButton(View v){
        if(currentButton!=null&&currentButton.getId()!=v.getId()){
            currentButton.setEnabled(true);
        }
        v.setEnabled(false);
        currentButton=v;
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_MENU){
            mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b0000000")));
            mPopupWindow.showAtLocation(buttomBarGroup, Gravity.BOTTOM, 0, 0);
            mPopupWindow.setAnimationStyle(R.style.app_pop);
            mPopupWindow.setOutsideTouchable(true);
            mPopupWindow.setFocusable(true);
            mPopupWindow.update();
        }
        else if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {

            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                SysApplication.getInstance().exit();// 可直接关闭所有的Acitivity并退出应用程序。
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
