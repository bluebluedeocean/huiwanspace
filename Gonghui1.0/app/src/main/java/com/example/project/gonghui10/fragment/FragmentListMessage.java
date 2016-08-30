package com.example.project.gonghui10.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.activity.LIstMessageItemActivity;
import com.example.project.gonghui10.adapter.MyBaseAdapter;
import com.example.project.gonghui10.bean.ActivityData;
import com.example.project.gonghui10.net.ImageLoad;
import com.example.project.gonghui10.net.ItemImages;
import com.example.project.gonghui10.url.Url;
import com.example.project.gonghui10.util.AutoListView;
import com.example.project.gonghui10.util.ImageViewPager;
import com.example.project.gonghui10.util.Images;
import com.example.project.gonghui10.util.StringCheck;
import com.example.project.gonghui10.view.ReFlashListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by 19950 on 2016/7/11.
 */
public class FragmentListMessage extends Fragment implements ReFlashListView.IReflashListener,ReFlashListView.ILoadListener,MyBaseAdapter.ShowImages,
        AdapterView.OnItemClickListener {
    private View view;
    String id,title,nick_name,publish_time,publish_location,sign_up_begin_time,sign_up_end_time,activity_start_time,
            activity_finish_time,activity_location,numsign,numsigned,gatherlocation,face,firstImage,sImage;
    ArrayList<ActivityData> activity_list = new ArrayList<ActivityData>();
    private MyBaseAdapter adapter;
    private Context context;
    private ReFlashListView listView;
    private int page = 1;//listView分页显示
    private int timeTemp;//最新一条数据的时间戳
    private boolean isLoad = true;//判断是否是上拉加载
    private boolean isReFlash = false; //判断是否下拉刷新
    //private String token;
    private String sessionId;
    long currentTime = 0;
    String[] ids;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list,null,false);
        sessionId = Config.getCacheSessionId(getActivity());
        ids = Config.getCacheLocationId(getActivity());
        loadImageItems();
        page = 2;
        return view;
    }

    private void loadImageItems() {
        final ProgressDialog pd = ProgressDialog.show(getActivity(), getResources().getString(R.string.connecting), getResources().getString(R.string.connecting_to_server));

        new ImageLoad(ids,"","",sessionId,new ImageLoad.SuccessCallBack() {
            @Override
            public void onSuccess(String data,String message) {
                pd.dismiss();
                Log.i("info","FragmentListMessage 中 loadImagesItems 获得的数据：" + data);
                Bundle bundle = new Bundle();
                bundle.putString("responseData", data);
                Message msg = new Message();
                msg.setData(bundle);
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                handler.sendMessage(msg);
            }
        }, new ImageLoad.FailCallBack() {
            @Override
            public void onFail(String message) {
                pd.dismiss();
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Hadler使用handleMessage处理消息
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String json = msg.getData().getString("responseData");

Log.i("info","这里接受来自服务器的数据："+json);
            try {
                JSONArray response = new JSONArray(json);
                for(int i=0;i<response.length();i++) {
Log.i("info","进入循环体：i=" + i);
                    JSONObject item = response.getJSONObject(i);
                    id = item.getString("id");
                    face = item.getString("face");
                    title = item.getString("sTitle");
                    nick_name = item.getString("username");
                    if(!item.getString("sImage").equals("")&&item.getString("sImage")!=null) {
                        if(item.getJSONObject("sImage")!=null) {
                            JSONObject imageItem = item.getJSONObject("sImage");
                            firstImage = imageItem.getString("0p");
                        }else {
                            firstImage = null;
                        }
                    }else {
                        firstImage = null;
                    }

                    publish_time = item.getString("sPubtime");
                    publish_location = item.getString("sPubtime");
                    sign_up_begin_time = item.getString("sSignupBtime");
                    sign_up_end_time = item.getString("sSignupEtime");
                    activity_start_time = item.getString("sBtime");
                    activity_finish_time = item.getString("sEtime");
                    activity_location = item.getString("sPosition");
                    numsign = item.getString("sNumber");
                    numsigned = item.getString("sCurrentNumber");
Log.i("info：","这里接受来自服务器的Item信息：" + id + title + nick_name + publish_time + publish_location + sign_up_begin_time
                            + sign_up_end_time + activity_start_time + activity_finish_time + activity_location + numsigned + numsign);

                    ActivityData activity_item = new ActivityData(id,title,face,nick_name,publish_time,publish_location,
                            sign_up_begin_time,sign_up_end_time,activity_start_time,
                            activity_finish_time,activity_location,numsign,numsigned,firstImage);
                    if(isLoad) {
                        activity_list.add(activity_item);
                    }else if(isReFlash){
                        activity_list.add(0,activity_item);
                    }
                }
                isReFlash=false;
                isLoad = false;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for(int j=0;j<activity_list.size();j++ ){
Log.i("info","以下是每条activity_list的数据：" + activity_list.get(j).toString());
            }
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
Log.i("info","handler.postDelayed即将执行showList方法");
                    showList(activity_list);
                }
            },1000);
        }
    };

    private void showList(final ArrayList<ActivityData> data_list) {
        if(adapter==null){
            adapter = new MyBaseAdapter(getActivity(),data_list);
            adapter.setInterface(this);
            listView = (ReFlashListView) view.findViewById(R.id.fragment_listview);
            listView.setInterface((ReFlashListView.IReflashListener) this);
            listView.setInterface((ReFlashListView.ILoadListener) this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
           // adapter.onDateChange(data_list);
            //listView.setOnClickListener(this);
            listView.reflashComplete();

        }else {
            adapter.onDateChange(data_list);
            listView.reflashComplete();
        }
    }

    @Override
    public void onReflash() {
        String time =activity_list.get(0).getPublish_time();
        timeTemp = Integer.parseInt(time);
        Log.i("info","要发送给服务器的时间戳timeTemp:" + timeTemp);
        new ImageLoad(ids,time,"",  sessionId, new ImageLoad.SuccessCallBack() {
            @Override
            public void onSuccess(String data,String message) {
                isReFlash = true;
                Log.i("info","FragmentListMessage onReflash 中 loadImagesItems 获得的数据：" + data);
                Bundle bundle = new Bundle();
                bundle.putString("responseData", data);
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
                long timepass = System.currentTimeMillis() - currentTime;
                if(timepass>5000) {
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    currentTime = System.currentTimeMillis();
                }
            }
        }, new ImageLoad.FailCallBack() {
            @Override
            public void onFail(final String message) {

                Handler failHandler = new Handler();
                failHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("info","加载失败！");
                        listView.reflashComplete();
                        long timepass = System.currentTimeMillis() - currentTime;
                        if(timepass>5000) {
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            currentTime = System.currentTimeMillis();
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onLoad() {
        int length = activity_list.size();
        Log.i("info","FragmentListMessage中onLoad获得的activity_list.size:" + length);
        String time =activity_list.get(length -1).getPublish_time();
        timeTemp = Integer.parseInt(time);
        Log.i("info","要发送给服务器的时间戳timeTemp:" + timeTemp);
        new ImageLoad(ids,"",time, sessionId, new ImageLoad.SuccessCallBack() {
            @Override
            public void onSuccess(String data,String message) {
                isLoad = true;
                Bundle bundle = new Bundle();
                bundle.putString("responseData", data);
                Message msg = new Message();
                msg.setData(bundle);
                handler.sendMessage(msg);
                listView.loadComplete();
                long timepass = System.currentTimeMillis() - currentTime;
                if(timepass>5000) {
                    Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                    currentTime = System.currentTimeMillis();
                }
            }
        }, new ImageLoad.FailCallBack() {
            @Override
            public void onFail(final String message) {
                Handler failHandler = new Handler();
                failHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView.loadComplete();
                        long timepass = System.currentTimeMillis() - currentTime;
                        if(timepass>5000) {
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                            currentTime = System.currentTimeMillis();
                        }
                    }
                },1000);
            }
        });
    }

    @Override
    public void onShowImages(int id) {

        new ItemImages(id +"", new ItemImages.SuccessCallBack() {
            @Override
            public Void onSuccess(String result) {
                Intent intent = new Intent(getActivity(),ImageViewPager.class);
                Bundle bundle = new Bundle();
                bundle.putString("imagesUrl",result);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                return null;
            }
        }, new ItemImages.FailCallBack() {
            @Override
            public Void onFail(String message) {
                Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("info","<-------------点击了Item---------------------->");
        final String imagesId = activity_list.get(position-1).getId();
        final JSONArray[] imageArray = new JSONArray[1];
        final String[][] urls = new String[1][1];

        new ItemImages(imagesId, new ItemImages.SuccessCallBack() {
            @Override
            public Void onSuccess(String imagesUrl) {
                try {
                    imageArray[0] = new JSONArray(imagesUrl);
                    urls[0] = new String[imageArray[0].length()];
                    if (imageArray[0].length() < 1) {
                        return null;
                    } else {
                        for (int i = 0; i < imageArray[0].length(); i++) {
                            try {
                                JSONObject obj = imageArray[0].getJSONObject(i);
                                String url = obj.getString("albumPath");
                                Log.i("info", "imgUrl多个图片的每张的URL:" + url);
                                urls[0][i] = url;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Intent intent = new Intent(getActivity(), LIstMessageItemActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("urls",urls[0]);
                        bundle.putString("sId",imagesId);
                        intent.putExtras(bundle);
                        getActivity().startActivity(intent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }, new ItemImages.FailCallBack() {
            @Override
            public Void onFail(String result) {
                Intent intent = new Intent(getActivity(), LIstMessageItemActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("sId",imagesId);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
                return null;
            }
        });

    }
}
