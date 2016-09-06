package com.example.project.gonghui10.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.project.gonghui10.Config;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.net.textuserinfo;
import com.example.project.gonghui10.util.ChoseLocationUtil;
import com.example.project.gonghui10.util.ImageLoader;
import com.example.project.gonghui10.util.SysApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;


@SuppressLint("SdCardPath")
public class ModifyUserInfoActivity extends ChoseLocationUtil implements OnClickListener {
    private ImageView ivHead;//头像显示
    private Button btnTakephoto;//拍照
    private Button btnPhotos;//相册
    private Bitmap head;//头像Bitmap
    private static String path = "/sdcard/myHead/";//sd路径

    private String face;
    private String sex;
//    private String name;
//    private String phone;
//    private String email;
//    private EditText nameEt,phoneEt,EmailEt;

    private Spinner provinceSpinner, schoolSpinner, campusSpinner, sexSpinner;
    private Button sureBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifyuserinfo_layout);
        SysApplication.getInstance().addActivity(this);
        loadInfo();
        selectLocation();
        selectSex();
        modifyHeadPic();
        sureBtn = (Button) findViewById(R.id.sureBtn);
        sureBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void selectSex() {
        sexSpinner = (Spinner) findViewById(R.id.sex_spinner);
        String[] sexItem = getResources().getStringArray(R.array.sex);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item , sexItem);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter);
        sexSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] sex = getResources().getStringArray(R.array.sex);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectLocation() {
        provinceSpinner = (Spinner) findViewById(R.id.spinner1);
        schoolSpinner = (Spinner) findViewById(R.id.spinner2);
        campusSpinner = (Spinner) findViewById(R.id.spinner3);
        initSchoolDatas();
        ArrayAdapter<String> provinceAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, mProvinceDatas);
        provinceSpinner.setAdapter(provinceAdapter);
        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCurrentProviceName = mProvinceDatas[position];
                pId = mProvinceIds[position];
                String[] schools = mSchoolDatasMap.get(mCurrentProviceName);
                if (schools == null) {
                    schools = new String[]{""};
                }
                ArrayAdapter<String> schoolAdapter = new ArrayAdapter<String>(ModifyUserInfoActivity.this,R.layout.support_simple_spinner_dropdown_item, schools);
                schoolSpinner.setAdapter(schoolAdapter);
                mCurrentSchoolName = mSchoolDatasMap.get(mCurrentProviceName)[0];
                sId = mSchoolIdsMap.get(mCurrentProviceName)[0];
                String[] campus = mCampusDatasMap.get(mCurrentSchoolName);
                mCurrentCampusName = campus[0];
                cId = mCampusIdsMap.get(mCurrentSchoolName)[0];
                if (campus == null) {
                    campus = new String[]{""};
                }
                ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(ModifyUserInfoActivity.this,R.layout.support_simple_spinner_dropdown_item, campus);
                campusSpinner.setAdapter(campusAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                mCurrentSchoolName = mSchoolDatasMap.get(mCurrentProviceName)[position];
                sId = mSchoolIdsMap.get(mCurrentProviceName)[position];
                String[] areas = mCampusDatasMap.get(mCurrentSchoolName);
                mCurrentCampusName = areas[0];
                cId = mCampusIdsMap.get(mCurrentSchoolName)[0];
                if (areas == null) {
                    areas = new String[]{""};
                }
                ArrayAdapter<String> campusAdapter = new ArrayAdapter<String>(ModifyUserInfoActivity.this,R.layout.support_simple_spinner_dropdown_item, areas);
                campusSpinner.setAdapter(campusAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        campusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner) parent;
                mCurrentCampusName = (String) sp.getItemAtPosition(position);
                cId = mCampusIdsMap.get(mCurrentSchoolName)[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        sure_location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                location = mCurrentCampusName;
//                if(mCurrentProviceName.equals("全国")) {
//                    location = mCurrentProviceName;
//                }
//                Config.cacheLocationId(ModifyUserInfoActivity.this,pId,sId,cId);
//                Config.cacheLocation( ModifyUserInfoActivity.this,location);
//                Intent in = new Intent();
//                in.setClass(ModifyUserInfoActivity.this,MainActivity.class);
//                startActivity(in);
//                finish();
//            }
//        });
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

    private void modifyHeadPic() {
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

    private void loadInfo() {

        new textuserinfo(Config.getCacheSessionId(this), new textuserinfo.SuccessCallBack() {
            @Override
            public void onSuccess(JSONObject obj) {
                try {
                    setContent(obj);
//                    setInit(obj);
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

//    private void setInit(JSONObject obj) throws JSONException {
//        nameEt = (EditText) findViewById(R.id.inputName_Tv);
//        phoneEt = (EditText) findViewById(R.id.inputPhone_Tv);
//        EmailEt = (EditText) findViewById(R.id.inputEmail_Tv);
//
//        name = obj.getString("name");
//        phone = obj.getString("phone");
//        email = obj.getString("email");
//
//        nameEt.setHint(name);
//        phoneEt.setHint(phone);
//        EmailEt.setHint(email);
//    }

    public void setContent(JSONObject content) throws JSONException {
        ImageLoader il = new ImageLoader(this);
        face = content.getString("face");
        il.DisplayImage(face, ivHead);

        sex = content.getString("sex");
        sexSpinner.setSelection(getSelection());
    }

    private int getSelection() {
        if (sex == "男") {
            return 0;
        } else if (sex == "女") {
            return 1;
        } else return 2;
    }
}