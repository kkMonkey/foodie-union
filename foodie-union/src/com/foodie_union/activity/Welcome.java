package com.foodie_union.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.foodie_union.constant.Constants;
import com.foodie_union.network.ConnectNetwork;

public class Welcome extends Activity {

	private Button login;
	private Button register;
	ConnectNetwork connectNetwork;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		connectNetwork = new ConnectNetwork();
		Constants.connectNetwork = connectNetwork;

		login = (Button) findViewById(R.id.login);
		register = (Button) findViewById(R.id.register);

		login.setOnClickListener(loginClick);
		register.setOnClickListener(registerClick);
	}

	OnClickListener loginClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Welcome.this, Login.class);
			startActivity(intent);
		}
	};

	OnClickListener registerClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(Welcome.this, Register.class);
			startActivity(intent);
		}
	};

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(Welcome.this);
		builder.setMessage("确定要退出软件吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						Welcome.this.finish();
					}
				});
		builder.setNegativeButton("取消",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			dialog();
			Constants.exitRequested = true;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		return false;
	}

}
