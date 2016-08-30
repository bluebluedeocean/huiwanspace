package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.gonghui10.R;
import com.example.project.gonghui10.bean.ActivityDatadetail;
import com.example.project.gonghui10.net.ItemImage;
import com.example.project.gonghui10.util.DataTransform;
import com.example.project.gonghui10.util.ImageLoader;
import com.example.project.gonghui10.util.RoundImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Meng on 2016/8/1.
 */
public class LIstMessageItemActivity extends Activity {

    String[] urls;
    String sId;

    private Context mContext;
    private LinearLayout picturesGroup;
    private ImageLoader imageLoader;
    private RoundImageLoader roundImageLoader;
    private ImageView head_image;
    private TextView activityname;
    private TextView pulisher_name;
    private TextView public_time;
    private TextView activity_context;
    private TextView activity_begin_time;
    private TextView activity_end_time;
    private TextView num_signed;
    private TextView num_sign;
    private TextView sign_up_begin_time;
    private TextView sign_up_end_time;
    private TextView place;
    private TextView gather_location;

    /*{
        "id": "用户活动id",
            "sTitle": "，用户活动标题",
            "sDesc": "用户活动描述",
            "sPubtime": "用户活动发布时间，时间戳格式",
            "sPosition": "用户活动地点",
            "sGatherPosition": "用户活动集合地点",
            "sSignupBtime": "用户活动开始报名的时间，时间戳格式",
            "sSignupEtime": "用户活动报名截止时间，时间戳格式",
            "sBtime": "用户活动开始的时间，时间戳格式",
            "sEtime": "用户活动结束的时间，时间戳格式",
            "sNumber": "用户活动限制人数",
            "sCurrentNumber": "用户活动当前参加人数",
            "praise": "活动赞的数量",
            "collection": "活动被赞的数量",
            "comment": "评论数",
            "transmit": "活动被转发的数量",
            "isTransmit": "是否时转发的活动，不是转发的默认为0，是转发的则为所转发活动的id",
            "transmitComment": "转发时候的描述",
            "username": "活动发布者",
            "sex": "活动发布者性别",
            "face": "http://49.140.166.99:8080/eLife/uploads/",//活动发布者头像，是一个url地址,
            "cName":"活动所属校区",
            "sName":"活动所属学校",
    }*/

    private String id;
    private String sTitle;
    private String sDesc;
    private String sPubtime;
    private String sPosition;
    private String sGatherPosition;
    private String sSignupBtime;
    private String sSignupEtime;
    private String sBtime;
    private String sEtime;
    private String sNumber;
    private String sCurrentNumber;
    private String praise;
    private String collection;
    private String comment;
    private String transmit;
    private String isTransmit;
    private String transmitComment;
    private String username;
    private String sex;
    private String face;
    private String cName;
    private String sName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);
        mContext = this;
        Bundle bundle = getIntent().getExtras();
        urls = bundle.getStringArray("urls");
        sId = bundle.getString("sId");
        initView();
        initPictures();
        initMessages();
    }

    private void initView() {
        imageLoader = new ImageLoader(mContext);
        roundImageLoader = new RoundImageLoader(mContext);
        picturesGroup = (LinearLayout) findViewById(R.id.Pictures_group);
        head_image = (ImageView) findViewById(R.id.head_image);
        activityname = (TextView) findViewById(R.id.activityname);
        pulisher_name = (TextView) findViewById(R.id.pulisher_name);
        public_time = (TextView) findViewById(R.id.public_time);
        activity_context = (TextView) findViewById(R.id.activity_context);
        activity_begin_time = (TextView) findViewById(R.id.activity_begin_time);
        activity_end_time = (TextView) findViewById(R.id.activity_end_time);
        num_signed = (TextView) findViewById(R.id.num_signed);
        num_sign = (TextView) findViewById(R.id.num_sign);
        sign_up_begin_time = (TextView) findViewById(R.id.sign_up_begin_time);
        sign_up_end_time = (TextView) findViewById(R.id.sign_up_end_time);
        place = (TextView) findViewById(R.id.place);
        gather_location = (TextView) findViewById(R.id.gather_location);
    }

    private void initPictures() {
        if(urls!=null&&!urls.equals("")) {
            for(int i = 0; i < urls.length; i++) {
                ImageView imageView = new ImageView(mContext);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                        1250,
                        750
                );
                //imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                picturesGroup.addView(imageView,p);
                imageLoader.DisplayImage(urls[i],imageView);
            }
        }
    }

    private void initMessages() {
        new ItemImage(sId, new ItemImage.SuccessCallBack() {
            @Override
            public void onSuccess(JSONObject obj) {
                try {
                    id = obj.getString("id");
                    sTitle = obj.getString("sTitle");
                    sDesc = obj.getString("sDesc");
                    sPubtime = obj.getString("sPubtime");
                    sPosition = obj.getString("sPosition");
                    sGatherPosition = obj.getString("sGatherPosition");
                    sSignupBtime = obj.getString("sSignupBtime");
                    sSignupEtime = obj.getString("sSignupEtime");
                    sBtime = obj.getString("sBtime");
                    sEtime = obj.getString("sEtime");
                    sNumber = obj.getString("sNumber");
                    sCurrentNumber = obj.getString("sCurrentNumber");
                    praise = obj.getString("praise");
                    collection = obj.getString("collection");
                    comment = obj.getString("comment");
                    transmit = obj.getString("transmit");
                    isTransmit = obj.getString("isTransmit");
                    transmitComment = obj.getString("transmitComment");
                    username = obj.getString("username");
                    sex = obj.getString("sex");
                    face = obj.getString("face");
                    cName = obj.getString("cName");
                    sName = obj.getString("sName");

                    ActivityDatadetail activityDatadetail = new ActivityDatadetail(id,sTitle,sDesc,sPubtime,sPosition,sGatherPosition,
                            sSignupBtime,sSignupEtime,sBtime,sEtime,sNumber,sCurrentNumber,praise,collection,comment,transmit,isTransmit,transmitComment,
                            username, sex, face, cName, sName);
                    roundImageLoader.DisplayImage(face,head_image);
                    activityname.setText(sTitle);
                    pulisher_name.setText(username);
                    public_time.setText(DataTransform.timeStamp2Date(sPubtime,"yyyy年MM月dd HH:mm"));
                    activity_context.setText(sDesc);
                    activity_begin_time.setText(DataTransform.timeStamp2Date(sBtime,"yyyy年MM月dd HH:mm"));
                    activity_end_time.setText(DataTransform.timeStamp2Date(sEtime,"yyyy年MM月dd HH:mm"));
                    num_signed.setText(sCurrentNumber);
                    num_sign.setText(sNumber);
                    sign_up_begin_time.setText(DataTransform.timeStamp2Date(sSignupBtime,"yyyy年MM月dd HH:mm"));
                    sign_up_end_time.setText(DataTransform.timeStamp2Date(sSignupEtime,"yyyy年MM月dd HH:mm"));
                    place.setText(sPosition);
                    gather_location.setText(sGatherPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new ItemImage.FailCallBack() {
            @Override
            public void onFail(String msg) {

            }
        });
    }

}
