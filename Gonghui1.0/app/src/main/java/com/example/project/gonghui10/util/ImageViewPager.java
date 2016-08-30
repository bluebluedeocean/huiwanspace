package com.example.project.gonghui10.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.adapter.MyPagerAdapter;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by meng on 2016/7/26.
 */
public class ImageViewPager extends Activity implements OnPageChangeListener{
    private ViewPager viewPager;
    private JSONArray  imageArray;
    public static LruCache<String,Bitmap> mCache;
    ArrayList<String> imagesUrlArray = new ArrayList<>();
    ImageView[] mImageView;
    private Context context = this;
    ProgressBar progressBar;
    private ImageView[] tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageviewpager);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //初始化缓存，分配缓存大小
        initmCache();
        //获取传过来的intent信息，是所有要加载图片的url数据，可以被解析成Json格式
        get_Intent();
        //下载所有图片，并将图片存储在缓存中
        loadImages(imageArray);
        initTips();

        //viewPager.addOnPageChangeListener(this);
Log.i("info","<------------------已经执行loadImage方法，下面是init方法-------------------->");
        //等待所有的图片下载完毕，然后从缓存中获取图片，初始化图片数组，并初始化Adapter设置ViewPager
        new initAsyncTask().execute();
        viewPager.setOnPageChangeListener(this);

    }

    private void initTips() {
        // 将点点加入到ViewGroup中
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        tips = new ImageView[imagesUrlArray.size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new LayoutParams(10, 10));
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 5;
            layoutParams.rightMargin = 5;
            group.addView(imageView, layoutParams);
        }

    }

    private void initmCache() {
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
    }

    private void loadImages(JSONArray imageArray) {
        Log.i("info","imgUrl多个图片的URL长度:" + imageArray.length());
        imagesUrlArray = new ArrayList<String>();
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
                    imagesUrlArray.add(url);
                    Bitmap bitmap = mCache.get(url);
                    if (bitmap==null) {
                        new MyAsycTask().execute(url);
                        Log.i("info","执行.execute方法，已经向缓存中写入数据");
                    }
                   /* String[] urlSplit = url.split("/");
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
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            Log.i("info","imgUrl多个图片的每张的URL:" + imagesUrlArray);
            for(int j = 0;j<imageArray.length();j++) {
                if(mCache.get(imagesUrlArray.get(j))!=null) {
                    Log.i("info","mCache中存入数据的URL：" + imagesUrlArray.get(j));
                }
            }
            Log.i("info","<--------------------mCache.size():" + mCache.size());

        }
    }



    private void setImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_focused);
            } else {
                tips[i].setBackgroundResource(R.mipmap.page_indicator_unfocused);
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyAsycTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected synchronized Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitMapFromURL(url);
            if (bitmap!=null) {
                mCache.put(url,bitmap);
                Log.i("info","已经执行mCache.put方法");
                if(mCache.get(url)==null) {
                    Log.i("info","xxxxxxxxxxxxxxxxxxxxxxx存入的数据没有取出xxxxxxxxxxxxx");
                } else{
                    Log.i("info","<----------取出的值不为空--------->");
                    Log.i("info","<----------mCache的大小：--------->" + mCache.size());
                }
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

    private void get_Intent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String imgUrl = bundle.getString("imagesUrl");
        Log.i("info","imgUrl多个图片的URL字符串:" + imgUrl);
        try {
            imageArray = new JSONArray(imgUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void loadOk() {
        int flag = 0;
        for(String url:imagesUrlArray) {
            if(mCache.get(url)!=null) {
                flag++;
            }
        }
        if(flag==imagesUrlArray.size()){
Log.i("info","<------------图片已经加载完毕，图片url集合imagesUrlArray的长度为：" + imagesUrlArray.size());
            return;
        }
        else {
            try {
Log.i("info","<--------------------图片还未加载完毕，再次执行加载判断------------------------>");
                Thread.sleep(500);
                loadOk();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class initAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            loadOk();
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressBar.setVisibility(View.GONE);
            Log.i("ingo","<-----------执行onPostExecute方法----------->");
            super.onPostExecute(aVoid);
            mImageView = new ImageView[imagesUrlArray.size()];
            Log.i("info","执行onPostExecute方法,imagesUrlArray的长度：" + imagesUrlArray.size());

            int num1=0;
            for(String urlString:imagesUrlArray) {
                if(mCache.get(urlString)!=null);
                    num1++;
            }

            Log.i("info","<------------------onPostExecute中测得mCache不为空的bitmap个数：" + num1);

            for(int size=0;size<imagesUrlArray.size();size++) {
                ImageView imageView = new ImageView(context);
                mImageView[size] = imageView;
                String url = imagesUrlArray.get(size);
                Bitmap bitmap = mCache.get(url);

                if(bitmap!=null) {
                    imageView.setImageBitmap(bitmap);
                    Log.i("ingo","<-----------执行setImageBItmap方法,初始化，ImageViews数组中添加数据----------->");
                }
                Log.i("info","mImageView数组的长度：" + mImageView.length);
            }

            MyPagerAdapter myPagerAdapter = new MyPagerAdapter(mImageView);
            viewPager.setAdapter(myPagerAdapter);

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCache != null) {
            if (mCache.size() > 0) {
                for(String url:imagesUrlArray) {
                    if(mCache.get(url) !=null) {
                        Bitmap map = mCache.remove(url);
                        if(map!=null) {
                            map.recycle();
                        }
                    }
                }

                Log.i("info","执行完mCache.remove方法，mCache的大小;" + mCache.size());
                /*Log.i("info",
                        "mMemoryCache.size() " + mCache.size());
                mCache.evictAll();
                Log.i("info", "mMemoryCache.size()" + mCache.size());*/
            }
          //  mCache = null;
        }
    }
}
