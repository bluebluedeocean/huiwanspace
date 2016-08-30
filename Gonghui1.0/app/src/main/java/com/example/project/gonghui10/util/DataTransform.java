package com.example.project.gonghui10.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by meng on 2016/7/20.
 */
public class DataTransform {
    private  Context context;
    public DataTransform(Context context) {
        this.context = context;
    }
    public static String timeStamp2Date(String data,String format) {
        if(data == null || data.isEmpty() || data.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()) format = "yyyy年MM月dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(data+"000")));
    }
    public static String timeStamp2Date(String data) {
        if(data == null || data.isEmpty() || data.equals("null")){
            return "";
        }
        String format = "yyyy年MM月dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(data+"000")));
    }

    public static String date2TimeStamp(String date_str,String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     * @return
     */
    public static String timeStamp(){
        long time = System.currentTimeMillis();
        String t = String.valueOf(time/1000);
        return t;
    }
}
