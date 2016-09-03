package com.example.project.gonghui10.activity;

import java.io.File;
import java.io.FileOutputStream;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.net.textuserinfo;
import com.example.project.gonghui10.util.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;


@SuppressLint("SdCardPath")
public class ModifyUserInfoActivity extends Activity implements OnClickListener {
    private ImageView ivHead;//头像显示
    private String face;
    private Button btnTakephoto;//拍照
    private Button btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/myHead/";//sd路径


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyuserinfo_layout);
        init();
        modify();
    }

    private void modify() {
        //初始化控件
        btnPhotos = (Button) findViewById(R.id.selectfromgrally_btn);
        btnTakephoto = (Button) findViewById(R.id.takepohoto_btn);
        btnPhotos.setOnClickListener(this);
        btnTakephoto.setOnClickListener(this);
        ivHead = (ImageView) findViewById(R.id.uploadpic_iv);
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
            ivHead.setImageDrawable(drawable);
        } else {
            /**
             *	如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.selectfromgrally_btn://从相册里面取照片
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                break;
            case R.id.takepohoto_btn://调用相机拍照
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                        "head.jpg")));
                startActivityForResult(intent2, 2);//采用ForResult打开
                break;
            default:
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        ivHead.setImageBitmap(head);//用ImageView显示出来
                    }
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    ;

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";//图片
    }

    private void init() {
        new textuserinfo(Config.getCacheSessionId(this), new textuserinfo.SuccessCallBack() {
            @Override
            public void onSuccess(JSONObject obj) {
                try {
                    setContent(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new textuserinfo.FailCallBack() {
            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void setContent(JSONObject content) throws JSONException {
        ImageLoader il = new ImageLoader(this);
        face = content.getString("face");
        il.DisplayImage(face,ivHead);
    }
}