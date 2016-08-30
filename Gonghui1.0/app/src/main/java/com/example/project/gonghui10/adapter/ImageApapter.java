package com.example.project.gonghui10.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.util.ImageLoader;
import com.example.project.gonghui10.util.Images;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by meng on 2016/7/25.
 */
public class ImageApapter extends BaseAdapter {
    Context context;
    ArrayList<String> imagesArray;
    private ImageLoader imageLoader;
    public ImageApapter(ArrayList<String> urls,Context context) {
        this.imagesArray = urls;
        Log.i("info","ImageArray" + urls);
        this.context = context;
    }

    @Override

    public int getCount() {
        return imagesArray.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bitmap bitmap = null;
        ImageView imageView = new ImageView(context);
        Log.i("info","ImageAdapter:" + imagesArray.get(position));
        //bitmap = Images.mCache.get(imagesArray.get(position));
        String[] str = imagesArray.get(position).split("/");
        String name = str[str.length-1];
        try {
            FileInputStream fis = new FileInputStream(name);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView.setImageBitmap(bitmap);
        //imageLoader.DisplayImage(imagesArray.get(position),imageView);
        //imageView.setBackgroundResource(R.mipmap.ic_launcher);
        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        return imageView;
    }
}
