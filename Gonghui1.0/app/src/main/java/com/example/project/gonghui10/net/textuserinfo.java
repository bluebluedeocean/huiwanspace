package com.example.project.gonghui10.net;
import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yu on 2016/8/27 0027.
 */


public class textuserinfo {
    public textuserinfo(String sessionId, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if(successCallBack!=null) {
                                JSONObject returnObj = obj.getJSONObject("datas");
                                successCallBack.onSuccess(returnObj);
                            }
                            break;
                        default:
                            if (failCallBack!=null){
                                String msg = obj.getString(Config.KEY_RETURN_MESSAGE);
                                failCallBack.onFail(msg);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBace() {
            @Override
            public void onFail() {

            }
        }, Config.KEY_ACTION,Config.ACTION_GETUSERINFO,Config.KEY_SESSIONID,sessionId);
    }

    public interface SuccessCallBack {
        void onSuccess(JSONObject obj);
    }

    public interface FailCallBack {
        void onFail(String msg);
    }
}
