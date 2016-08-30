package com.example.project.gonghui10.net;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/10.
 */
public class Campus {
    public Campus(String sId, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null){
                                JSONArray jsonArray = obj.getJSONArray("datas");
                                successCallBack.onSuccess(jsonArray);
                            }
                            break;
                        default:
                            if(failCallBack!=null) {
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
                failCallBack.onFail("网络连接失败");
            }
        }, Config.KEY_ACTION,Config.ACTION_GETCAMPUSBYSID,Config.KEY_SID,sId);
    }
    public static interface SuccessCallBack{
        void onSuccess(JSONArray jarray);
    }

    public static interface FailCallBack {
        void onFail(String message);
    }
}
