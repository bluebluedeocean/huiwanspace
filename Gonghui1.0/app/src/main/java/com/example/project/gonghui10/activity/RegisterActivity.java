package com.example.project.gonghui10.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.net.MessageVerify;
import com.example.project.gonghui10.net.Register;
import com.example.project.gonghui10.sqlite.MyloginCursor;
import com.example.project.gonghui10.sqlite.MyDatabaseHelper;
import com.example.project.gonghui10.sqlite.MytabOperate;
import com.example.project.gonghui10.util.SysApplication;
import com.thinkland.sdk.sms.SMSCaptcha;
import com.thinkland.sdk.util.BaseData;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends RegisterBaseActivity implements OnClickListener,
        OnFocusChangeListener {
    private Button registerBack;
    private Button registerCheck;
    private Button registerBtn;
    private EditText registerId;
    private EditText registerPassword;
    private EditText etCheckCode;
    private EditText turePassword;
    private TextView registerBackText;
    private TextView registerIdText;
    private TextView registerPwText;
    private TextView turePwText;
    private TextView registerAuthText;
    private String isPhone, isPassword, isTruePassword, Autecode, Autecodeimg;
    private String phoneNum;
    private String messageCode;
    private String token;
    private String sessionId;
    private int flagPhone, flagPassword, flagTruePassword, flagAutecode;
    private SQLiteOpenHelper helper;
    private MytabOperate mylogin;
    private SMSCaptcha captcha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        helper = new MyDatabaseHelper(this);
        SysApplication.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        registerBack = (Button) findViewById(R.id.registerBack);
        registerBack.setOnClickListener(this);
        registerCheck = (Button) findViewById(R.id.obtainCheckCode);
        registerCheck.setOnClickListener(this);
        registerBtn = (Button) findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(this);
        registerBackText = (TextView) findViewById(R.id.registerBackText);
        registerBackText.setOnClickListener(this);

        registerId = (EditText) findViewById(R.id.registerId);
        registerId.setOnFocusChangeListener(this);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerPassword.setOnFocusChangeListener(this);
        etCheckCode = (EditText) findViewById(R.id.etCheckCode);
        etCheckCode.setOnFocusChangeListener(this);
        etCheckCode.setOnClickListener(this);
        turePassword = (EditText) findViewById(R.id.turePassword);
        turePassword.setOnFocusChangeListener(this);

        registerIdText = (TextView) findViewById(R.id.registerIdText);
        registerPwText = (TextView) findViewById(R.id.registerPwText);
        turePwText = (TextView) findViewById(R.id.turePwText);
        registerAuthText = (TextView) findViewById(R.id.registerAuthText);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerBack:
                RegisterActivity.this.finish();
                break;
            case R.id.registerBackText:
                RegisterActivity.this.finish();
                break;
            case R.id.etCheckCode:
                etCheckCode.setFocusable(true);
                etCheckCode.setFocusableInTouchMode(true);
                etCheckCode.requestFocus();
                etCheckCode.findFocus();
                break;
            case R.id.obtainCheckCode:
                phoneNum = registerId.getText().toString().trim().replaceAll("\\s*", "");
                checkPhoneNum(phoneNum);
                break;
            case R.id.registerBtn:
                etCheckCode.setFocusable(false);
                phoneNum = registerId.getText().toString().trim().replaceAll("\\s*", "");
                isPhone = registerId.getText().toString();
                messageCode = etCheckCode.getText().toString();
                sessionId = Config.getCacheSessionId(RegisterActivity.this);
             /*   if(sessionId.equals(null)||sessionId.equals("")) {
                    sessionId = "hf3bippvnkphn1ja436v6p9ju7";
                }*/

                if (flagAutecode == 1 && flagPassword == 1 && flagPhone == 1
                        && flagTruePassword == 1) {
                    mylogin = new MytabOperate(helper.getWritableDatabase());
                    mylogin.insert(isPhone, isPassword);

                    new Register(phoneNum, isPassword, messageCode, sessionId, new Register.SuccessCallBack() {
                        @Override
                        public void onSuccess(JSONObject obj) {
                            Config.cacheUserPassword(RegisterActivity.this,isPassword);
                            try {
                                String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT);
                                Intent intent = new Intent();
                                intent.putExtra(Config.KEY_PHONE_NUM,isPhone);
                                intent.putExtra(Config.KEY_PASSWORD,isPassword);
                                intent.setClass(RegisterActivity.this,LoginActivity.class);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Register.FailCallBack() {
                        @Override
                        public void onFail(String message) {
                            Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_SHORT);
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "请检查您的输入",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }


    private void checkPhoneNum(String phone) {

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, R.string.smssdk_write_mobile_phone,
                    Toast.LENGTH_SHORT).show();
            return;
        }

        showDialog(phone, "+86");

    }

    public void showDialog(final String phone, String code) {
        final String phoneNum = code + " " + splitPhoneNum(phone);
        String strContent = "我们将发送验证码短信到这个号码:"
                + phoneNum;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("注册")
                .setIcon(R.mipmap.gray_huiwan_icon)
                .setCancelable(false)
                .setMessage(strContent)
                .setPositiveButton(R.string.smssdk_ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                showDialog(getResources()
                                        .getString(
                                                R.string.smssdk_get_verification_code_content));
                                new MessageVerify(phone, new MessageVerify.SuccessCallBack() {
                                    @Override
                                    public void onSuccess(JSONObject obj) {
                                        try {
                                            String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                            sessionId = obj.getString(Config.KEY_SESSIONID);
                                            //token = obj.getString(Config.KEY_TOKEN);


                                            Config.cacheSessionId(RegisterActivity.this, sessionId);
                                           // Config.cacheToken(RegisterActivity.this, token);
                                            Log.i("info","从服务器获取的sessionId：" + sessionId);
                                            Log.i("info","从缓存中取出的sessionId:" + Config.getCacheSessionId(RegisterActivity.this));

                                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }, new MessageVerify.FailCallBack() {
                                    @Override
                                    public void onFail(String message) {
                                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                });

                                closeDialog();

                            }
                        })
                .setNegativeButton(R.string.smssdk_cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

        AlertDialog dlg = builder.create();
        dlg.show();

    }


    private String splitPhoneNum(String phone) {
        StringBuilder builder = new StringBuilder(phone);
        builder.reverse();
        for (int i = 4, len = builder.length(); i < len; i += 5) {
            builder.insert(i, ' ');
        }

        builder.reverse();
        return builder.toString();

    }

/*    private void afterCaptchaRequested() {
        String phone = registerId.getText().toString().trim()
                .replaceAll("\\s*", "");
        String code = "+86".trim();
        String fomatedPhone = code + " " + splitPhoneNum(phone);

        Toast.makeText(this, "成功!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(RegisterActivity.this, CaptchaActivity.class);
        intent.putExtra("formatedPhone", fomatedPhone);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }*/

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        isPhone = registerId.getText().toString();
        isPassword = registerPassword.getText().toString();
        isTruePassword = turePassword.getText().toString();
        Autecode = etCheckCode.getText().toString();
        //Autecodeimg = Autjcode.getInstance().getCode().toUpperCase();
        switch (v.getId()) {
            case R.id.registerId:
                if (hasFocus == false) {
                    // 手机号码的正则判断
                    Pattern pattern = Pattern.compile("^1[3,5,8]\\d{9}$");
                    Matcher matcher = pattern.matcher(isPhone);
                    if (matcher.find()) {
                        registerIdText.setVisibility(View.INVISIBLE);
                        flagPhone = 1;
                    } else {
                        if (registerId.length() != 0) {
                            registerIdText.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case R.id.registerPassword:
                if (hasFocus == false) {
                    if ((isPassword.length() < 6 || isPassword.length() > 20)
                            && isPassword.length() != 0) {
                        registerPwText.setVisibility(View.VISIBLE);
                    } else {
                        registerPwText.setVisibility(View.INVISIBLE);
                        flagPassword = 1;
                    }
                }
                break;
            case R.id.turePassword:
                if (hasFocus == false) {
                    if (isTruePassword.equals(isPassword)) {
                        turePwText.setVisibility(View.INVISIBLE);
                        flagTruePassword = 1;
                    } else {
                        if (turePassword.length() != 0) {
                            turePwText.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            case R.id.etCheckCode:
                if (hasFocus == false) {
                    // 判断验证码是否正确，toUpperCase()是不区分大小写
                    if (etCheckCode.length() != 0) {
                        registerAuthText.setVisibility(View.INVISIBLE);
                        flagAutecode = 1;

                    } else {
                        if (etCheckCode.length() == 0) {
                            registerAuthText.setVisibility(View.VISIBLE);
                        }
                    }
                }
                break;
            default:
                break;
        }
    }
}
