package com.example.project.gonghui10.net;

import android.util.Log;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/5.
 */
public class LoadMore {
    public LoadMore(String page,final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Log.i("info","调用ImageLoad从服务器返回的数据： " + obj.toString());
                    switch(obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
                                String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                successCallBack.onSuccess(message);
                            }
                            break;
                        default:
                            if(failCallBack!=null) {
                                failCallBack.onFail();
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

            }
        },Config.KEY_ACTION,Config.ACTION_GETLISTSITUATION,Config.KEY_PAGE,page);
    }

    public static interface SuccessCallBack {
        void onSuccess(String message);
    }

    public static interface FailCallBack {
        void onFail();
    }
}
