package com.example.project.gonghui10;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Meng on 2016/7/31.
 */
public class Config {
    public static final String CHARSET = "utf-8";
    public static final String APP_ID = "huiwankongjian";

    public static final String SMSSDK_KEY = "15a9ba7e3b69e";
    public static final String SMSSDK_KEY_SECRETE = "e3af6fc5bc4cfad65cae314c3abc3f63";

    public static final String KEY_ACTION = "action";
    public static final String KEY_STATUS = "status";
    //public static final String KEY_TOKEN = "token";
    public static final String KEY_PHONE_NUM = "phone";
    public static final String KEY_PASSWORD= "password";
    public static final String KEY_LOGINID = "loginId";
    public static final String KEY_RETURN_MESSAGE = "message";
    public static final String KEY_RETURN_DATAS = "datas";
    public static final String KEY_AGENT = "agent";
    public static final String KEY_PAGE = "page";
    public static final String KEY_SESSIONID = "sessionId";
    public static final String KEY_APP = "app";
    public static final String KEY_FIRSTTIME = "firstTime";
    public static final String KEY_LASTTIME = "lastTime";
    public static final String KEY_MESSAGECODE = "messageCode";
    public static final String KEY_SID = "sId";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_LOCATION_ID = "locationId";
    public static final String KEY_LOCATION_PID = "pId";
    public static final String KEY_LOCATION_SID = "sId";
    public static final String KEY_LOCATION_CID = "cId";

    public static final String ACTION_LOGIN = "userLogin";
    public static final String ACTION_USERREG = "userRegisterByPhone";
    public static final String ACTION_GETMESSAGEVERIFY = "getMessageForReg";
    public static final String ACTION_GETLISTSITUATION = "getListSituation";
    public static final String ACTION_GETSITUATIONBYID = "getSituationById";
    public static final String ACTION_GETVERIFY = "getVerify";
    public static final String ACTION_USERLOGINOUT = "userLoginOut";
    public static final String ACTION_GETLISTPROVIENCE = "getListProvince";
    public static final String ACTION_GETCAMPUSBYSID = "getCampusBySid";
    public static final String ACTION_GETLISTSITUATIONIMAGE = "getListSituationImage";

    public static final int RESULT_STATUS_SUCCESS = 1;
    public static final int RESULT_STATUS_WRONG_PASSWORD = 2;
    public static final int RESTLT_STAUTS_FAIL = 0;
    public static final int RESULT_STATUS_INVALID_TOKEN = 12;

    //18004421903

    public static String getCacheUerPhone(Context context) {
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_PHONE_NUM,"");
    }
    public static void cacheUserPhone(Context context,String phone_num) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_PHONE_NUM,phone_num);
        e.commit();
    }

   /* public static String getCacheToken(Context context) {
        return context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).getString(KEY_TOKEN,"");
    }
    public static void cacheToken(Context context,String token) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_TOKEN,token);
        e.commit();
    }*/

    public static String getCacheUserPassword(Context context) {
        return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_PASSWORD,"");
    }
    public static void cacheUserPassword(Context context,String password) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_PASSWORD,password);
        e.commit();
    }

    public static String getCacheSessionId(Context context) {
        return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_SESSIONID,"");
    }
    public static void cacheSessionId(Context context,String sessionId) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_SESSIONID,sessionId);
        e.commit();
    }

    public static String getCacheLocation(Context context) {
        return context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_LOCATION,"全国");
    }
    public static void cacheLocation(Context context,String location) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_LOCATION,location);
        e.apply();
    }

    public static String[] getCacheLocationId(Context context) {
        String[] ids = new String[3];
        ids[0] = context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_LOCATION_PID,"");
        ids[1] = context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_LOCATION_SID,"");
        ids[2] = context.getSharedPreferences(APP_ID,context.MODE_PRIVATE).getString(KEY_LOCATION_CID,"");
        if(ids[1]!=null&&!ids[2].equals("")) {
            if(ids[1].equals(ids[2])) {
                ids[2] = "";
            }
        }
        return ids;
    }
    public static void cacheLocationId(Context context,String pId,String sId,String cId) {
        SharedPreferences.Editor e = context.getSharedPreferences(APP_ID,Context.MODE_PRIVATE).edit();
        e.putString(KEY_LOCATION_PID,pId);
        e.putString(KEY_LOCATION_SID,sId);
        e.putString(KEY_LOCATION_CID,cId);
        e.apply();
    }

     public static void clearCache(Context context) {
         cacheLocation(context,"全国");
         cacheUserPhone(context,"");
         cacheLocationId(context,"","","");
         cacheSessionId(context,"");
         cacheUserPassword(context,"");
     }
}
