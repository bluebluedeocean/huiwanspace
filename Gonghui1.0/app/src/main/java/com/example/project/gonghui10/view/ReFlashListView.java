package com.example.project.gonghui10.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.util.AutoListView;


public class ReFlashListView extends ListView implements OnScrollListener {
	View header;// 顶部布局文件；

	int headerHeight;// 顶部布局文件的高度；
	int firstVisibleItem;// 当前第一个可见的item的位置；
	int scrollState;// listview 当前滚动状态；
	boolean isRemark;// 标记，当前是在listview最顶端摁下的；
	int startY;// 摁下时的Y值；

	int state;// 当前的状态；
	final int NONE = 0;// 正常状态；
	final int PULL = 1;// 提示下拉状态；
	final int RELESE = 2;// 提示释放状态；
	final int REFLASHING = 3;// 刷新状态；
	private  int test = 1;//用来调试Log
	IReflashListener iReflashListener;//刷新数据的接口

	View footer;//底部布局文件；
	private  int lastVisibleItem;//最后一个可见Item位置
	private int totalItemCount;//总共的Item条数
	private boolean isLoading;//正在加载
	ILoadListener iLoadListener;//上拉加载数据的接口

	public ReFlashListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public ReFlashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	public ReFlashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}

	/**
	 * 初始化界面，添加顶部布局文件到 listview
	 *
	 * @param context
	 */
	public void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		footer = inflater.inflate(R.layout.footer_layout,null);
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
Log.i("tag", "headerHeight = " + headerHeight);
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.addFooterView(footer);
		this.setOnScrollListener(this);
	}

	/**
	 * 通知父布局，占用的宽，高；
	 *
	 * @param view
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if (tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight,
					MeasureSpec.EXACTLY);			//高度为不为0时，填充父布局？
		} else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);//高度为0时
		}
		view.measure(width, height);
	}

	/**
	 * 设置header 布局 上边距；
	 *
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding,
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
						 int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
		if(totalItemCount==lastVisibleItem&&scrollState==SCROLL_STATE_IDLE) {
			if(!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);
				Log.i("info","onScrollStateChanged方法执行，使footerVISIBLE");
				iLoadListener.onLoad();
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstVisibleItem == 0) {
					isRemark = true;
					startY = (int) ev.getY();
				}
				break;

			case MotionEvent.ACTION_MOVE:
				onMove(ev);
				break;
			case MotionEvent.ACTION_UP:
				if (state == RELESE) {
					state = REFLASHING;
					// 加载最新数据；
					reflashViewByState();
					iReflashListener.onReflash();
				} else if (state == PULL) {
					state = NONE;
					isRemark = false;
					reflashViewByState();
				}
				break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 判断移动过程操作；
	 *
	 * @param ev
	 */
	private void onMove(MotionEvent ev) {
		if (!isRemark) {
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY - startY;
		int topPadding = space - headerHeight;
		switch (state) {
			case NONE:
				if (space > 0) {
					state = PULL;
					reflashViewByState();
				}
				break;
			case PULL:
				topPadding(topPadding);
				if (space > headerHeight + 30
						&& scrollState == SCROLL_STATE_TOUCH_SCROLL) {
					state = RELESE;
					reflashViewByState();
				}
				break;
			case RELESE:
				topPadding(topPadding);
				if (space < headerHeight + 30) {
					state = PULL;
					reflashViewByState();
				} else if (space <= 0) {
					state = NONE;
					isRemark = false;
					reflashViewByState();
				}
				break;
		}
	}

	/**
	 * 根据当前状态，改变界面显示；
	 */
	private void reflashViewByState() {
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
		RotateAnimation anim = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		switch (state) {
			case NONE:
Log.i("info","这里执行了reflashViewByState方法中的NONE,刷新次数：" + test);
				arrow.clearAnimation();
				topPadding(-headerHeight);
				break;

			case PULL:
				arrow.setVisibility(View.VISIBLE);
				progress.setVisibility(View.GONE);
				tip.setText("下拉可以刷新！");
				arrow.clearAnimation();
				arrow.setAnimation(anim1);
				break;
			case RELESE:
				arrow.setVisibility(View.VISIBLE);
				progress.setVisibility(View.GONE);
				tip.setText("松开可以刷新！");
				arrow.clearAnimation();
				arrow.setAnimation(anim);
				break;
			case REFLASHING:
				topPadding(50);
				arrow.setVisibility(View.GONE);
				progress.setVisibility(View.VISIBLE);
				tip.setText("正在刷新...");
Log.i("info","正在刷新执行。。。。。");
				arrow.clearAnimation();
				break;
		}
	}

	/**
	 * 获取完数据；
	 */
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		TextView lastupdatetime = (TextView) header
				.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
		test = test + 1;
Log.i("info","reflashViewByState方法即将执行");
		reflashViewByState();
Log.i("info","这里执行了reflashViewByState方法");
	}

	public void loadComplete() {
		isLoading = false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
	}

	public void setInterface(IReflashListener iReflashListener){
		this.iReflashListener = iReflashListener;
	}

	public void setInterface(ILoadListener iLoadListener) {
		this.iLoadListener = iLoadListener;
	}

	/**
	 * 刷新数据接口
	 * @author Administrator
	 */
	public interface IReflashListener{
		public void onReflash();
	}
	public interface ILoadListener {
		public void onLoad();
	}
}
