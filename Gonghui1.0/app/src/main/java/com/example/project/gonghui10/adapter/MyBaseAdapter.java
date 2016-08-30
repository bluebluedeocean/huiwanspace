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
	private int mStart,mEnd;	//当前可见的起始项
	private static String[] URLS;//要加载的图片url
	private long currentTime = 0;

	String id,title,face,nick_name,publish_time,publish_location,sign_up_begin_time,sign_up_end_time,activity_start_time,
			activity_finish_time,activity_location,numsign,numsigned,firstPicture;

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
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
			/*holder.comment.setEnabled(true);
			holder.share.setEnabled(true);
			holder.mappictureImageView.setEnabled(true);*/
		}

			holder.firstImage.setTag(firstPicture);
			imageLoader.DisplayImage(firstPicture,holder.firstImage);
		//加载头像
		holder.head_image.setTag(face);
		roundImageLoader.DisplayImage(face,holder.head_image);
		/*pictureShow.showImageByThread(holder.head_image,face);
		pictureShow.showImageByThread(holder.firstImage,firstPicture);*/
		/*pictureShow.showImageByAsyncTask(holder.head_image,face);
		pictureShow.showImageByAsyncTask(holder.firstImage,firstPicture);*/
		holder.personnickname.setText(arraydatas.getNick_name());
		holder.title.setText(title);
		holder.publishtime.setText(dataTransform.timeStamp2Date(publish_time,null));
		holder.signupbegintime.setText(dataTransform.timeStamp2Date(sign_up_begin_time,null));
		holder.signupendtime.setText(dataTransform.timeStamp2Date(sign_up_end_time));
		holder.activitybegintime.setText(dataTransform.timeStamp2Date(activity_start_time));
		holder.activityendtime.setText(dataTransform.timeStamp2Date(activity_finish_time));
		holder.activitybegintime.setText(dataTransform.timeStamp2Date(activity_start_time));
		holder.place.setText(activity_location);
		holder.numsign.setText(numsign);
		holder.numsigned.setText(numsigned);

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
		public void onShowImages(int id);
	}

	public void setInterface(ShowImages showImages) {
		this.showImages = showImages;
	}

	class ViewHolder {
		LinearLayout linearLayout;

		TextView title, publictime, label, time, place,numsigned,
				textdetail, titleattentionno,activityendtime,publishtime,
				 personnickname,personlocation,activitybegintime,numsign,
				signupbegintime,signupendtime,gatherlocation;

		ImageView head_image, firstImage, personheader;

		TextView phone, message, comment, mappictureImageView, share;

	}
}
