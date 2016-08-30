package com.example.project.gonghui10.net;

import android.util.Log;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Meng on 2016/8/5.
 */
public class ImageLoad {
    public ImageLoad(String[] ids,String firstTime, String lastTime,String sessionId,final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    Log.i("info","调用ImageLoad从服务器返回的数据： " + obj.toString());
                    switch(obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
                                String data = obj.getString(Config.KEY_RETURN_DATAS);
                                String message = obj.getString(Config.KEY_RETURN_MESSAGE);
                                successCallBack.onSuccess(data,message);
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
                    failCallBack.onFail("链接失败");
                }
                Log.i("info","NetConnection.FailCallBace 失败onFail： ");
            }
        },Config.KEY_ACTION,Config.ACTION_GETLISTSITUATION,Config.KEY_FIRSTTIME,firstTime,Config.KEY_LASTTIME,lastTime,Config.KEY_SESSIONID,sessionId,
        Config.KEY_LOCATION_PID,ids[0],Config.KEY_LOCATION_SID,ids[1],Config.KEY_LOCATION_CID,ids[2]);
    }

    public static interface SuccessCallBack {
        void onSuccess(String data,String message);
    }

    public static interface FailCallBack {
        void onFail(String message);
    }
}
