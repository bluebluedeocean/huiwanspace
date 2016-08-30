package com.example.project.gonghui10.adapter;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;

import com.example.project.gonghui10.bean.ADBean;

public class MyItemPicturesAdapter extends PagerAdapter {
	List<ADBean> listADbeans;
	ViewGroup parent;
	ImageView view;

	public MyItemPicturesAdapter(List<ADBean> listADbeans) {
		this.listADbeans = listADbeans;
	}
	public int getCount() {
		//把这个条数数值很大很大
		return Integer.MAX_VALUE;
	}
	/**相当于getView:实例化每个页面的View和添加View
	 * container:ViewPage 容器
	 * position 位置
	 */
	public Object instantiateItem(ViewGroup container, int position) {
		Log.i("info","根据位置取出某一个View");
		//根据位置取出某一个View
		view = listADbeans.get(position%listADbeans.size()).getmImageView();
		if(listADbeans.get(position%listADbeans.size()).getImgPath() != -1){
			LayoutParams p = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
			view.setBackgroundResource(listADbeans.get(position%listADbeans.size()).getImgPath());
		}
		view.setScaleType(ScaleType.FIT_XY);
		//添加到容器

		if(listADbeans.size()==0) {
			return 0;
		}else if(1<listADbeans.size()||listADbeans.size()<4) {
			container.removeView(view);
		}

		container.addView(view);

		return view;//返回实例化的View
	}
	/**
	 * 判断view和instantiateItem返回的对象是否一样
	 * Object : 时instantiateItem返回的对象
	 */
	public boolean isViewFromObject(View view, Object object) {
		if(view == object){
			return true;
		}else{
			return false;
		}
	}


	/**|
	 * 实例化两张图片,最多只能装三张图片
	 */
	public void destroyItem(ViewGroup container, int position, Object object) {
		//释放资源,
		Log.i("info","释放资源 destroyItem调用");
		container.removeView((View) object);
	}

	public synchronized void notifyImages(List<ADBean> listADbeans){
		this.listADbeans = listADbeans;
		notifyDataSetChanged();
	}

	public void destoryParent() {
		parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeAllViews();
		}
	}
}
