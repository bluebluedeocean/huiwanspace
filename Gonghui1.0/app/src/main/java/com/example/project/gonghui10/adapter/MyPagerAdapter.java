package com.example.project.gonghui10.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by meng on 2016/7/26.
 */
public class MyPagerAdapter extends PagerAdapter {
    private ImageView[] mImageViews;

    public MyPagerAdapter(ImageView[] mImageView) {
        this.mImageViews = mImageView;
    }

    /**
     * 返回页卡的数量
     * @return
     */
    @Override
    public int getCount() {
        return mImageViews.length;
    }

    /**
     * View是否来自于对象
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    /**
     * 实例化一个页卡
     * @param container
     * @param position
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        try {
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);
        }catch(Exception e){
            //handler something
        }
        if(mImageViews.length!=0) {
            return mImageViews[position % mImageViews.length];
        }else
            return null;
    }

    /**
     * 销毁一个页卡
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if(position<mImageViews.length){
            container.removeView(mImageViews[position]);
        }
    }
}
