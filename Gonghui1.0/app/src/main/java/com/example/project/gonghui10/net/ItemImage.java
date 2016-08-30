package com.example.project.gonghui10.net;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/20.
/*
 */

public class ItemImage {
    public ItemImage(String sid, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject obj = new JSONObject(result);
                    switch (obj.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack!=null) {
                                JSONObject data = obj.getJSONObject(Config.KEY_RETURN_DATAS);
                                successCallBack.onSuccess(data);
                            }
                            break;
                        case Config.RESTLT_STAUTS_FAIL:
                            if(failCallBack!=null) {
                                String msg = obj.getString(Config.KEY_RETURN_MESSAGE);
                                failCallBack.onFail(msg);
                            }
                            break;
                        default:break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new NetConnection.FailCallBace() {
            @Override
            public void onFail() {

            }
        }, Config.KEY_ACTION,Config.ACTION_GETSITUATIONBYID,"sId",sid);
    }

    public interface SuccessCallBack {
        void onSuccess(JSONObject obj);
    }
    public interface FailCallBack {
        void onFail(String msg);
    }
}
