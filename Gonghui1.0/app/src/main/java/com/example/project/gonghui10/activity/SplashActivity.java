package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 2000; // 延迟两秒
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                SplashActivity.this.startActivity(mainIntent);//显示主窗口
                SplashActivity.this.finish();//关闭主窗口
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
