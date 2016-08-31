package com.example.project.gonghui10.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.project.gonghui10.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Yu on 2016/8/28 0028.
 */
public class ModifyUserInfoFragment extends android.support.v4.app.Fragment {
    private static int RESULT_LOAD_IMAGE = 1000;
    private static int RESULT_OK = 1011;
    private Button takePhoto_btn, selectFromGrally_btn;
    private ImageView imageView;
    View root;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.modifyuserinfo_layout, container, false);
        selectFromGrally_btn = (Button) root.findViewById(R.id.selectfromgrally_btn);
        selectFromGrally_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        "image/*");
                startActivityForResult(intent,RESULT_LOAD_IMAGE);
            }
        });
        return root;
    }



//   此处的方法不会在fragment中调用，点击相册选择图片后不会更新imageView;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageView = (ImageView) root.findViewById(R.id.uploadpic_iv);

                imageView.setImageURI(data.getData()); if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
                    if (data != null) {
                System.out.println("11111111111111111111111111111111111");
            }else {
                System.out.println("2222222222222222222222222222222222");

            }
        }

    }


}


