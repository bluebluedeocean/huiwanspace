package com.example.project.gonghui10.net;
import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yu on 2016/8/28 0028.
 */
public class ModifyUserInfo {

    public  ModifyUserInfo(String sessionId, final SuccessCallBack successCallBack, final FailCallBack failCallBack){
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    switch (jsonObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack != null) {
                                JSONObject returnObj = jsonObject.getJSONObject("datas");
                                successCallBack.onSuccess(returnObj);
                            }
                            break;
                        default:
                            if (failCallBack != null) {
                                String msg = jsonObject.getString(Config.KEY_RETURN_MESSAGE);
                                failCallBack.onFail(msg);
                            }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new NetConnection.FailCallBace() {
                    @Override
                    public void onFail() {

                    }
                },Config.KEY_ACTION,Config.ACTION_MODIFYUSERINFO,Config.KEY_SESSIONID,sessionId);
    }

    public interface SuccessCallBack {
        void onSuccess(JSONObject object);
    }
    public interface FailCallBack {
        void onFail(String object);
    }
}
