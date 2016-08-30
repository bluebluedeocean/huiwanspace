package com.example.project.gonghui10.activity;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.LinearLayout;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.fragment.UserNickFragment;



/**
 * Created by Yu on 2016/8/23 0023.
 *
 */
public class UserNickActivity extends FragmentActivity {

    public LinearLayout backBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersetting);
        if (savedInstanceState==null){
            getSupportFragmentManager().beginTransaction().
                    add(R.id.fragment_page,new UserNickFragment()).commit();
        }
        goback();
    }

    private void goback() {
        backBtn = (LinearLayout) findViewById(R.id.backbtn1);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
