package com.example.project.gonghui10.net;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Switch;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.url.Url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Meng on 2016/8/2.
 */
public class ItemImages {
    public ItemImages(String imagesId, final SuccessCallBack successCallBack, final FailCallBack failCallBack) {
        new NetConnection(Url.app_url, HttpMethod.POST, new NetConnection.SuccessCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jObject = new JSONObject(result);
                    switch (jObject.getInt(Config.KEY_STATUS)) {
                        case Config.RESULT_STATUS_SUCCESS:
                            if (successCallBack != null) {
                                successCallBack.onSuccess(jObject.getJSONArray(Config.KEY_RETURN_DATAS).toString());
                            }
                            break;
                        case Config.RESULT_STATUS_INVALID_TOKEN:
                            if (failCallBack != null)
                                failCallBack.onFail(jObject.get(Config.KEY_RETURN_MESSAGE).toString());
                            break;
                        default:
                            if (failCallBack != null) {
                                failCallBack.onFail(jObject.get(Config.KEY_RETURN_MESSAGE).toString());
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
        }, Config.KEY_ACTION, Config.ACTION_GETLISTSITUATIONIMAGE, Config.KEY_SID, imagesId);
    }

    public static interface SuccessCallBack {
        Void onSuccess(String result);
    }

    public interface FailCallBack {
        Void onFail(String message);
    }
}
