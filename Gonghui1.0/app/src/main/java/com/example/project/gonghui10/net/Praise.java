package com.example.project.gonghui10.net;

import android.util.Log;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/3.
 */
public class Praise {
    public String sessionId;
    public Praise(String sId,String sessionId, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Url.app_url,HttpMethod.POST,new NetConnection.SuccessCallBack() {

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch(obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
                                String msg = obj.getString(Config.KEY_RETURN_MESSAGE);
                                successCallBack.onSuccess(msg);
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
        },Config.KEY_ACTION,Config.ACTION_PRISE,Config.KEY_SESSIONID,sessionId,Config.KEY_SID,sId);
    }
    public static interface SuccessCallBack {
        void onSuccess(String message);
    }
    public static interface FailCallBack {
        void onFail(String message);
    }
}
