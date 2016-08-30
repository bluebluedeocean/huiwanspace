package com.example.project.gonghui10.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.project.gonghui10.R;
import com.example.project.gonghui10.sqlite.MyloginCursor;
import com.example.project.gonghui10.sqlite.MytabOperate;
import com.example.project.gonghui10.sqlite.MyDatabaseHelper;

public class ChangePasswordActivity extends Activity implements
		OnClickListener, OnFocusChangeListener {
	private Button changePwBack;
	private Button changePwBtn;
	private TextView changePwBackText;
	private TextView changePwIdText;
	private EditText changePwId;
	private TextView changePwText;
	private EditText changePw;
	private TextView changePwNewText;
	private EditText changePwNew;
	private String myPhone, myPassword, myPwNew;
	private int myflagPhone, myflagPassword, myflagPwNew;
	private SQLiteOpenHelper helper;
	private MytabOperate mylogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_changepassword);
		// 数据库辅助类的初始化
		helper = new MyDatabaseHelper(this);
		initView();
	}

	/**
	 * 控件的初始化
	 */
	private void initView() {
		changePwBack = (Button) findViewById(R.id.changePwBack);
		// 点击事件的监听
		changePwBack.setOnClickListener(this);
		changePwBtn = (Button) findViewById(R.id.changePwBtn);
		changePwBtn.setOnClickListener(this);
		changePwBackText = (TextView) findViewById(R.id.changePwBackText);
		changePwBackText.setOnClickListener(this);

		changePwId = (EditText) findViewById(R.id.changePwId);
		// EditText焦点改变的监听
		changePwId.setOnFocusChangeListener(this);
		changePw = (EditText) findViewById(R.id.changePw);
		changePw.setOnFocusChangeListener(this);
		changePwNew = (EditText) findViewById(R.id.changePwNew);
		changePwNew.setOnFocusChangeListener(this);
		changePwNew.setOnClickListener(this);

		changePwIdText = (TextView) findViewById(R.id.changePwIdText);
		changePwText = (TextView) findViewById(R.id.changePwText);
		changePwNewText = (TextView) findViewById(R.id.changePwNewText);
	}

	/**
	 * 控件的点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.changePwBack:
				// 关闭当前的Activity
				ChangePasswordActivity.this.finish();
				break;
			case R.id.changePwBackText:
				ChangePasswordActivity.this.finish();
				break;
			case R.id.changePwNew:
				// EditText重新获取焦点
				changePwNew.setFocusable(true);
				changePwNew.setFocusableInTouchMode(true);
				changePwNew.requestFocus();
				changePwNew.findFocus();
				break;
			case R.id.changePwBtn:
				// EditText失去焦点
				changePwNew.setFocusable(false);
				// 获取EditText中的内容
				myPhone = changePwId.getText().toString();
				myPwNew = changePwNew.getText().toString();
				if (myflagPhone == 1 && myflagPassword == 1 && myflagPwNew == 1) {
					// 取得数据库的写权限
					mylogin = new MytabOperate(helper.getWritableDatabase());
					// 更新数据
					mylogin.updata(myPwNew, myPhone);
					// Dialog弹窗的实现
					new AlertDialog.Builder(ChangePasswordActivity.this)
							.setTitle("提示").setMessage("修改成功！")
							.setPositiveButton("确认",
									// 弹窗内按钮的点击事件
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(DialogInterface dialog,
															int which) {
											// Intent跳转事件
											Intent i = new Intent(
													ChangePasswordActivity.this,
													LoginActivity.class);
											startActivity(i);
										}
									}).show();
				} else {
					Toast.makeText(ChangePasswordActivity.this, "修改失败",
							Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				break;
		}
	}

	/**
	 * 焦点监听时间
	 *
	 * @param v
	 *            对应的控件
	 * @param hasFocus
	 *            是否点击
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		myPhone = changePwId.getText().toString();
		myPassword = changePw.getText().toString();
		myPwNew = changePwNew.getText().toString();
		switch (v.getId()) {
			case R.id.changePwId:
				if (hasFocus == false) {
					if (new MyloginCursor(
							ChangePasswordActivity.this.helper
									.getReadableDatabase()).find(myPhone).size() == 0
							&& changePwId.length() != 0) {
						// 控件的可见性
						changePwIdText.setVisibility(View.VISIBLE);
					} else {
						changePwIdText.setVisibility(View.INVISIBLE);
						myflagPhone = 1;
					}
				}
				break;
			case R.id.changePw:
				if (hasFocus == false) {
					if (myflagPhone == 1) {
						// 数据的查找，并对查找到的数据进行拆分
						String result[] = new MyloginCursor(
								ChangePasswordActivity.this.helper
										.getReadableDatabase()).find(myPhone)
								.toString().split(",");
						if (myPassword.equals(result[2]) && changePw.length() != 0) {
							changePwText.setVisibility(View.INVISIBLE);
							myflagPassword = 1;
						} else {
							changePwText.setVisibility(View.VISIBLE);
						}
					} else {
						Toast.makeText(ChangePasswordActivity.this, "请先输入正确的账号",
								Toast.LENGTH_SHORT).show();
					}
				}

				break;

			case R.id.changePwNew:
				if (hasFocus == false) {
					if ((myPwNew.length() < 6 || myPwNew.length() > 20)
							&& myPwNew.length() != 0) {
						changePwNewText.setVisibility(View.VISIBLE);
					} else {
						changePwNewText.setVisibility(View.INVISIBLE);
						myflagPwNew = 1;
					}
				}
				break;
			default:
				break;
		}
	}
}
