package com.example.project.gonghui10.net;

import android.util.Log;
import android.widget.Switch;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/3.
 */
public class Login {
    public Login(String phone, String securite_key_md5, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        Log.i("info","Login传入的参数：" + phone + securite_key_md5 );
        new NetConnection(Url.app_url,HttpMethod.POST,new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    String sessionId = obj.getString(Config.KEY_SESSIONID);
                    Log.i("info","调用Login从服务器返回的数据： " + obj.toString());
                    switch(obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
//                                String token = obj.getString(Config.KEY_TOKEN);
                                successCallBack.onSuccess(result);
                            }
                            break;
                        default:
                            if(failCallBack!=null) {
                                String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                failCallBack.onFail(message);
                            }
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBace() {

            @Override
            public void onFail() {
                if(failCallBack!=null) {
                    String message = "链接服务器失败，请检查网络";
                    failCallBack.onFail(message);
                }
            }
        },Config.KEY_ACTION,Config.ACTION_LOGIN,Config.KEY_LOGINID,phone,Config.KEY_PASSWORD,securite_key_md5);
    }
    public static interface SuccessCallBack {
        void onSuccess(String token);
    }
    public static interface FailCallBack {
        void onFail(String message);
    }
}
