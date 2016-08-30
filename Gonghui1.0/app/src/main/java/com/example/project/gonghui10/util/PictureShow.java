package com.example.project.gonghui10.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by meng on 2016/7/17.
 */

public class PictureShow {
    private ImageView mImageView;
    private String mURL;
    private LruCache<String,Bitmap> mCache;

    public PictureShow(Context context) {
        int MaxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = MaxMemory/4;
        mCache = new LruCache<String,Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存的时候调用
                return value.getByteCount();
            }
        };
    }

    public void addBitmapToCache(String url,Bitmap bitmap) {
        if(getBitmapFromCache(url)==null) {
            mCache.put(url,bitmap);
        }
    }

    public Bitmap getBitmapFromCache(String url) {
        return mCache.get(url);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mImageView.getTag().equals(mURL)) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public void showImageByThread(ImageView imageView,final String url) {
        mImageView = imageView;
        mURL = url;

        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromURL(url);
                Message message = Message.obtain();
                message.obj = bitmap;
                mHandler.sendMessage(message);
            }
        }.start();
    }

    public void showImageByAsyncTask(ImageView imageView,final String url) {
        Bitmap bitmap = getBitmapFromCache(url);
        if(bitmap==null) {
            new NewAsyncTask(imageView,url).execute(url);
        }else {
            imageView.setImageBitmap(bitmap);
        }
    }

    private class NewAsyncTask extends AsyncTask<String,Void,Bitmap> {
        private ImageView mImageView;
        private String Url;
        public NewAsyncTask(ImageView imageView,String url) {
            mImageView = imageView;
            Url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = getBitmapFromURL(url);
            if(bitmap!=null) {
                addBitmapToCache(url,bitmap);
            }
            return getBitmapFromURL(url);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(mImageView.getTag().equals(Url))
            mImageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromURL(String urlString) {
        Bitmap bitmap;
        InputStream is =null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            conn.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
