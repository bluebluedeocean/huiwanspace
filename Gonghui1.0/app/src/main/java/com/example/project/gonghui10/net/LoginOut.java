package com.example.project.gonghui10.net;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/8.
 */
public class LoginOut {
    public LoginOut(String sessionid, final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)){
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
                                successCallBack.onSuccess(obj);
                            }
                            break;
                        default:
                            if (failCallBack!=null) {
                                String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                failCallBack.onFail(message);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBace() {
            @Override
            public void onFail() {
                String message = "链接服务器失败，请检查网络";
                failCallBack.onFail(message);
            }
        },Config.KEY_ACTION,Config.ACTION_USERLOGINOUT,Config.KEY_SESSIONID,sessionid);
    }

    public static interface SuccessCallBack {
        public void onSuccess(JSONObject obj);
    }
    public static interface FailCallBack {
        public void onFail(String message);
    }
}
