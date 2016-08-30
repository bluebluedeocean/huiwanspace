package com.example.project.gonghui10.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.Window;
import android.widget.Gallery;
import android.widget.LinearLayout;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.adapter.ImageApapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by meng on 2016/7/21.
 */
public class Images extends Activity{
    JSONArray  imageArray;
    private Gallery gallery;
    private ImageApapter imageAdapter;
    ArrayList<String> imagesArray = new ArrayList<>();
    public static LruCache<String,Bitmap> mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.images);
        Intent intent = getIntent();
        gallery = (Gallery) findViewById(R.id.gallery);

        Bundle bundle = intent.getExtras();
        String imgUrl = bundle.getString("imagesUrl");
Log.i("info","imgUrl多个图片的URL字符串:" + imgUrl);
        try {
            imageArray = new JSONArray(imgUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //计算缓存大小
        int MaxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = MaxMemory/4;
        mCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
Log.i("info","sizeOf 已经执行，缓存已经开辟");
                return value.getByteCount();
            }
        };


        loadImages(imageArray);
        imageAdapter = new ImageApapter(imagesArray,this);
        gallery.setAdapter(imageAdapter);
    }

    private void loadImages(JSONArray imageArray) {
Log.i("info","imgUrl多个图片的URL长度:" + imageArray.length());
        imagesArray = new ArrayList<String>();
        if(imageArray.length()<1) {
            return;
        }else if(imageArray.length()==1) {
            JSONObject obj;
            try {
                obj = imageArray.getJSONObject(0);
                String url = obj.getString("albumPath");
Log.i("info","imgUrl只有一张图片时的的URL:" + url);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            for(int i=0;i<imageArray.length();i++) {
                try {
                    JSONObject obj = imageArray.getJSONObject(i);
                    String url = obj.getString("albumPath" );
Log.i("info","imgUrl多个图片的每张的URL:" + url);
                    imagesArray.add(url);
                    Bitmap bitmap = mCache.get(url);
                    if (bitmap==null) {
                        new MyAsycTask().execute(url);
Log.i("info","执行.execute方法，已经向缓存中写入数据");
                    }
                    String[] urlSplit = url.split("/");
                    String name = urlSplit[urlSplit.length-1];
                    Log.i("info","储存的文件名为：" + name);
                    try {
                        byte[] data = readImage(url);
                        FileOutputStream fos = new FileOutputStream(new File(name));
                        fos.write(data);
                        LinearLayout temp=(LinearLayout)findViewById(R.id.images);
                        Drawable d= Drawable.createFromPath(name);
                        temp.setBackground(d);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
Log.i("info","imgUrl多个图片的每张的URL:" + imagesArray);
            for(int j = 0;j<imageArray.length();j++) {
                if(mCache.get(imagesArray.get(j))!=null) {
Log.i("info","mCache中存入数据的URL：" + imagesArray.get(j));
                }
            }
        }
    }

    
    class MyAsycTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitMapFromURL(url);
            if (bitmap!=null) {
                mCache.put(url,bitmap);
                Log.i("info","已经执行mCache.put方法");
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }
    }

    //声明称为静态变量有助于调用
    public static byte[] readImage(String path) throws Exception{
        URL url = new URL(path);
// 记住使用的是HttpURLConnection类
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        //如果运行超过5秒会自动失效 这是android规定
        conn.setConnectTimeout(5*1000);
        InputStream inStream = conn.getInputStream();
        //调用readStream方法
        return readStream(inStream);
    }

    public static byte[] readStream(InputStream inStream) throws Exception{
        //把数据读取存放到内存中去
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while( (len=inStream.read(buffer)) != -1){
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    private Bitmap getBitMapFromURL(String url) {
        Log.i("info","getBitMapFromURL" + url);
        HttpURLConnection conn;
        InputStream is;
        Bitmap bitmap = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            is.close();
            bis.close();
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
