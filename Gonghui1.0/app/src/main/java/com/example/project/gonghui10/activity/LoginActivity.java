package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.net.Login;
import com.example.project.gonghui10.sqlite.MyDatabaseHelper;
import com.example.project.gonghui10.tools.MD5Tool;
import com.example.project.gonghui10.url.Url;
import com.example.project.gonghui10.util.BitmapToRound;
import com.example.project.gonghui10.util.SysApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;


public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText loginId;
    private EditText loginPassword;
    private Button loginBtn;
    private Button loginMissps;
    private Button loginNewUser;
    private Button loginTourist;
    private ImageView logoUser;
    private CheckBox remenberPw;
    private String id, ps;
    private String register_to_this_ps;
    private String register_to_this_ph_num;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        helper = new MyDatabaseHelper(this);
        SysApplication.getInstance().addActivity(this);
        Intent intent = getIntent();
        register_to_this_ps = intent.getStringExtra(Config.KEY_PHONE_NUM);
        register_to_this_ph_num = intent.getStringExtra(Config.KEY_PASSWORD);

        if(register_to_this_ph_num!=null&&register_to_this_ps!=null) {
            loginPassword.setText(register_to_this_ps);
            loginId.setText(register_to_this_ph_num);
        }

        initView();

        /*Intent i = super.getIntent();
        String Id = i.getStringExtra("myId");
        loginId.setText(Id);*/
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
//                isId = loginId.getText().toString();
//                isPs = loginPassword.getText().toString();
//                if (new MyloginCursor(
//                        LoginActivity.this.helper.getReadableDatabase()).find(isId)
//                        .size() == 0) {
//                    // Toast弹窗
//                    Toast.makeText(LoginActivity.this, "手机号未注册，请注册后登录",
//                            Toast.LENGTH_SHORT).show();
//                } else {
//                    String lph = new MyloginCursor(
//                            LoginActivity.this.helper.getReadableDatabase()).find(
//                            isId).toString();
//                    // 对查询出来的数据进行拆分
//                    String result[] = lph.split(",");
//                    if (result[1].equals(isId) && result[2].equals(isPs)) {
//                        Toast.makeText(LoginActivity.this, "登录成功",
//                                Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                    } else {
//                        Toast.makeText(LoginActivity.this, "用户名或密码错误",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                }
                final String id = loginId.getText().toString().trim();
                final String ps = loginPassword.getText().toString().trim();
                if (!id.equals("") && !ps.equals("")) {
                    if (ps.length() > 5 && ps.length() < 20) {
                        Config.cacheUserPhone(LoginActivity.this, loginId.getText().toString().trim());

                        new Login(id, ps, new Login.SuccessCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                String token = null;
                                String sessionId = null;
                                JSONObject obj = null;
                                try {
                                    obj = new JSONObject(result);
                                    sessionId = obj.getString(Config.KEY_SESSIONID);
                                    Config.cacheSessionId(LoginActivity.this,sessionId);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Config.cacheUserPhone(LoginActivity.this, id);
                                if (remenberPw.isChecked()) {
                                    Config.cacheUserPassword(LoginActivity.this, ps);
                                } else
                                Config.cacheUserPassword(LoginActivity.this, "");
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(Config.KEY_PHONE_NUM, loginId.getText().toString());
                                intent.putExtra(Config.KEY_SESSIONID, sessionId);
                                startActivity(intent);
Log.i("info","登录成功");
                                finish();
                            }
                        }, new Login.FailCallBack() {
                            @Override
                            public void onFail(String message) {
                                Log.i("info","登录失败" + message);
                                if (remenberPw.isChecked()) {
                                    Config.cacheUserPassword(LoginActivity.this, ps);
                                } else
                                    Config.cacheUserPassword(LoginActivity.this, "");
                                Toast.makeText(LoginActivity.this,message, Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (ps.length() <= 5) {
                        Toast.makeText(LoginActivity.this, R.string.password_is_too_short, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(LoginActivity.this, R.string.password_is_too_long, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(LoginActivity.this, R.string.phone_num_or_password_can_not_be_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.loginNewUser:
                Intent a = new Intent(LoginActivity.this,
                        RegisterActivity.class);
                startActivity(a);
                break;
            case R.id.touristlogin:
                Config.cacheLocation(this,"全国");
                Config.clearCache(LoginActivity.this);
                Intent in = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(in);
                break;
            case R.id.loginMissps:
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                break;
            default:
                break;
        }
    }

    /**
     * 提交用户信息
     *
     * @param country
     * @param phone
     */
    public void subMitUserInfo(String country, String phone) {
        Random r = new Random();
        String uid = Math.abs(r.nextInt()) + "";
        String nickName = "test";
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }

    private SQLiteOpenHelper helper;



    // 控件的初始化
    private void initView() {
        loginId = (EditText) findViewById(R.id.loginId);
        loginId.clearFocus();
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginPassword.clearFocus();
        logoUser = (ImageView) findViewById(R.id.logoUser);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(this);
        loginMissps = (Button) findViewById(R.id.loginMissps);
        loginMissps.setOnClickListener(this);
        loginTourist = (Button) findViewById(R.id.touristlogin);
        loginTourist.setOnClickListener(this);
        loginNewUser = (Button) findViewById(R.id.loginNewUser);
        loginNewUser.setOnClickListener(this);
        remenberPw = (CheckBox) findViewById(R.id.RemenberPw);
        remenberPw.setOnClickListener(this);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
        bitmap = BitmapToRound.toRoundBitmap(bitmap);
        logoUser.setImageBitmap(bitmap);

        if (!Config.getCacheUerPhone(LoginActivity.this).equals("")) {
            id = Config.getCacheUerPhone(LoginActivity.this) + "";
            Log.i("info", "获得用户名：" + id);
            loginId.setText(id);
        }
        if (!Config.getCacheUserPassword(LoginActivity.this).equals("")) {
            remenberPw.setChecked(true);
            ps = Config.getCacheUserPassword(LoginActivity.this) + "";
            Log.i("info", "获得密码：" + ps);
            loginPassword.setText(ps);
        } else
            remenberPw.setChecked(false);
        Log.i("info", "获得用户名和密码：" + id + ps);
    }
}
