package com.example.project.gonghui10.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.bean.ActivityData;
import com.example.project.gonghui10.util.DataTransform;
import com.example.project.gonghui10.util.ImageLoader;
import com.example.project.gonghui10.util.PictureShow;
import com.example.project.gonghui10.util.RoundImageLoader;

import java.util.ArrayList;
import java.util.Map;

public class MyBaseAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
	ArrayList<ActivityData> activity_list;
	LayoutInflater inflater;
	private Handler handler = new Handler();
	private  Context context;
	private Map<String, String> map;
	private ImageLoader imageLoader;
	private RoundImageLoader roundImageLoader;
	private PictureShow pictureShow;
	private DataTransform dataTransform;
	private ShowImages showImages;//显示照片的接口
	private ClickGood clickGood;
	private ClickComment clickComment;
	private ClickShare clickShare;
	private ClickXiala clickXiala;
	private int mStart,mEnd;	//当前可见的起始项
	private static String[] URLS;//要加载的图片url
	private long currentTime = 0;

	String id,title,face,nick_name,publish_time,publish_location,sign_up_begin_time,sign_up_end_time,activity_start_time,
			activity_finish_time,activity_location,numsign,numsigned,firstPicture,good_num,comment_num,share_num;
	int isDel,isPraise,isCollect,isJoin;

	public MyBaseAdapter(Context context, ArrayList<ActivityData> activity_list) {
		this.context = context;
		this.activity_list = activity_list;
		this.inflater = LayoutInflater.from(context);
		imageLoader = new ImageLoader(context);
		roundImageLoader = new RoundImageLoader(context);
		pictureShow = new PictureShow(context);
		dataTransform = new DataTransform(context);
		Log.i("info","MyBaseAdapter构造方法" + this.context.toString() + this.activity_list.toString());
	}

	public void onDateChange(ArrayList<ActivityData> apk_list) {
		this.activity_list = apk_list;
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		Log.i("info","getCount()返回值：" + activity_list.size() + " ");
		return activity_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return activity_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ActivityData arraydatas = activity_list.get(position);
		final ViewHolder holder;
		Log.i("info","getVeiw被调用");
		/***************************************************************/
		/**
		 * 获取数据
		 */
		id = arraydatas.getId();
		title = arraydatas.getTitle();
		face = arraydatas.getFace();
		Log.i("info","face" + face.toString());
		firstPicture = arraydatas.getFirstImage();
		if(firstPicture!=null) {
			Log.i("info","第一张照片的URL**************************" + firstPicture.toString());
		}

		nick_name = arraydatas.getNick_name();
		publish_time = arraydatas.getPublish_time();
		publish_location = arraydatas.getPublish_location();
		sign_up_begin_time = arraydatas.getSign_up_begin_time();
		sign_up_end_time = arraydatas.getSign_up_end_time();
		activity_start_time = arraydatas.getActivity_start_time();
		activity_finish_time = arraydatas.getActivity_finish_time();
		activity_location = arraydatas.getActivity_location();
		numsign = arraydatas.getNumsign();
		numsigned = arraydatas.getNumsigned();
		good_num = arraydatas.getGood_num();
		comment_num = arraydatas.getComment_num();
		share_num = arraydatas.getShare_num();
		isDel = arraydatas.getIsDel();
		isCollect = arraydatas.getIsCollect();
		isJoin = arraydatas.getIsJoin();
		isPraise = arraydatas.getIsPraise();

		Log.i("info","isPraise  ******************************" + isPraise + "*********************************\n");

		Log.i("info","adapter中的数据：" + id + title + nick_name + publish_time + publish_location + sign_up_begin_time + sign_up_end_time + activity_start_time
				+ activity_finish_time + activity_location + numsigned + numsign + "    ****    ");
		/***************************************************************/

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.fragment_home_page_layout_item,
					null);
			holder.linearLayout = (LinearLayout) convertView
					.findViewById(R.id.item);
			holder.head_image = (ImageView) convertView.findViewById(R.id.head_image);
			holder.firstImage = (ImageView) convertView.findViewById(R.id.picture);

			holder.personnickname = (TextView) convertView
					.findViewById(R.id.nick);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.publishtime = (TextView) convertView
					.findViewById(R.id.public_time);
			holder.activitybegintime = (TextView) convertView
					.findViewById(R.id.activity_begin_time);
			holder.activityendtime = (TextView) convertView
					.findViewById(R.id.activity_end_time);
			holder.place = (TextView) convertView
					.findViewById(R.id.place);
			holder.numsigned = (TextView) convertView
					.findViewById(R.id.num_signed);
			holder.numsign = (TextView) convertView
					.findViewById(R.id.num_sign);
			holder.signupbegintime = (TextView) convertView
					.findViewById(R.id.sign_up_begin_time);
			holder.signupendtime = (TextView) convertView
					.findViewById(R.id.sign_up_end_time);
			holder.gatherlocation = (TextView) convertView
					.findViewById(R.id.gather_location);
			holder.numsign = (TextView) convertView
					.findViewById(R.id.num_sign);
			holder.numsigned = (TextView) convertView
					.findViewById(R.id.num_signed);
			holder.good = (TextView) convertView.findViewById(R.id.good);
			holder.comment = (TextView) convertView.findViewById(R.id.comment);
			holder.share = (TextView) convertView.findViewById(R.id.share);
			holder.xiala = (ImageView) convertView.findViewById(R.id.xiala);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.firstImage.setTag(firstPicture);
		imageLoader.DisplayImage(firstPicture,holder.firstImage);
		//加载头像
		holder.head_image.setTag(face);
		roundImageLoader.DisplayImage(face,holder.head_image);
		holder.personnickname.setText(arraydatas.getNick_name());
		holder.title.setText(title);
		holder.publishtime.setText(DataTransform.timeStamp2Date(publish_time,null));
		holder.signupbegintime.setText(DataTransform.timeStamp2Date(sign_up_begin_time,null));
		holder.signupendtime.setText(DataTransform.timeStamp2Date(sign_up_end_time));
		holder.activitybegintime.setText(DataTransform.timeStamp2Date(activity_start_time));
		holder.activityendtime.setText(DataTransform.timeStamp2Date(activity_finish_time));
		holder.activitybegintime.setText(DataTransform.timeStamp2Date(activity_start_time));
		holder.place.setText(activity_location);
		holder.numsign.setText(numsign);
		holder.numsigned.setText(numsigned);
		holder.good.setText(good_num);
		if (isPraise!=1) {
			Log.i("info","isPraise  **********************good.setEnaable(true)" + isPraise + "*********************************\n");
			holder.good.setEnabled(true);
		}
		holder.comment.setText(comment_num);
		holder.share.setText(share_num);

		holder.firstImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long timepass = System.currentTimeMillis() - currentTime;
				if(timepass>5000) {
					showImages.onShowImages(Integer.parseInt(arraydatas.getId()));
					currentTime = System.currentTimeMillis();
				}
			}
		});

		holder.good.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				clickGood.onGoodClicked(Integer.parseInt(arraydatas.getId()),position);
			}
		});
		holder.comment.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				clickComment.onCommnentClicked(Integer.parseInt(arraydatas.getId()));
			}
		});
		holder.share.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				clickShare.onShareClicked(Integer.parseInt(arraydatas.getId()));
			}
		});
		holder.xiala.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				clickXiala.onXialaClicked(Integer.parseInt(arraydatas.getId()));
			}
		});


		return convertView;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}


	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		mStart = firstVisibleItem;
		mEnd = visibleItemCount + firstVisibleItem;
	}

	//interface
	/***************************/

	public interface ShowImages {
		void onShowImages(int id);
	}

	public interface ClickGood {
		void onGoodClicked(int id,int position);
	}

	public interface ClickComment {
		void onCommnentClicked(int id);
	}

	public interface ClickShare {
		void onShareClicked(int id);
	}
	public interface ClickXiala {
		void onXialaClicked(int id);
	}

	public void setInterface(ShowImages showImages,ClickGood clickGood,ClickComment clickComment,
							 ClickShare clickShare,ClickXiala clickXiala) {
		this.showImages = showImages;
		this.clickGood = clickGood;
		this.clickComment = clickComment;
		this.clickShare = clickShare;
		this.clickXiala = clickXiala;
	}

	class ViewHolder {
		LinearLayout linearLayout;
		TextView title, place,numsigned, activityendtime,publishtime,
				personnickname,activitybegintime,numsign,
				signupbegintime,signupendtime,gatherlocation;
		//点赞 评论 分享
		TextView good, comment, share;
		ImageView head_image, firstImage, xiala;
	}
}
