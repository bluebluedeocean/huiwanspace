package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Yu on 2016/8/30 0030.
 */
public class SelectPicActivity  {


        /***
         * 使用照相机拍照获取图片
         */
        public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
        /***
         * 使用相册中的图片
         */
        public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

        /***
         * 从Intent获取图片路径的KEY
         */
        public static final String KEY_PHOTO_PATH = "photo_path";

        private static final String TAG = "SelectPicActivity";

        private LinearLayout dialogLayout;
        private Button takePhotoBtn,pickPhotoBtn,cancelBtn;

        /**获取到的图片路径*/
        private String picPath;

        private Intent lastIntent ;

        private Uri photoUri;


}
