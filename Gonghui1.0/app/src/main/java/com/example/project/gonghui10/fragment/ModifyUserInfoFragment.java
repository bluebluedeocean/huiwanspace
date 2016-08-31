package com.example.project.gonghui10.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.project.gonghui10.R;


/**
 * Created by Yu on 2016/8/28 0028.
 */
public class ModifyUserInfoFragment extends android.support.v4.app.Fragment {
    private static int RESULT_LOAD_IMAGE = 1000;
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
                 if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
                    if (data != null) {
                        imageView.setImageURI(data.getData());
                    }
            }
        }

}





