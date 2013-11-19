package com.foodie_union.activity;

import org.jivesoftware.smack.XMPPException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.foodie_union.constant.Constants;
import com.foodie_union.network.ConnectNetwork;

public class Login extends Activity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button loginButton;
	private Button returnButton;
	private ConnectNetwork connectNetwork;
	private static Handler toastHandler;
	private InputMethodManager manager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		usernameEditText = (EditText) findViewById(R.id.login_username);
		passwordEditText = (EditText) findViewById(R.id.login_password);
		loginButton = (Button) findViewById(R.id.login_login);
		returnButton = (Button) findViewById(R.id.login_return);

		usernameEditText.requestFocus();

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		connectNetwork = com.foodie_union.constant.Constants.connectNetwork;

		// 得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
		Looper looper = Looper.myLooper();
		// 此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper
		toastHandler = new MessageHandler(looper);

		loginButton.setOnClickListener(loginOnClick);
		returnButton.setOnClickListener(returnOnClick);
	}

	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (getCurrentFocus() != null
					&& getCurrentFocus().getWindowToken() != null) {
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
		return super.onTouchEvent(event);
	}

	OnClickListener loginOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Constants.exitRequested = false;
			new Thread() {
				public void run() {
					while (!connectNetwork.isConnected()
							&& !Constants.exitRequested) {
						// 创建一个Message对象，并把得到的交通信息赋值给Message对象
						Message message = Message.obtain();
						message.obj = "正在连接服务器";
						toastHandler.sendMessage(message);
						if (connectNetwork.isConnected())
							break;
						connectNetwork = new ConnectNetwork();
						message = Message.obtain();
						message.obj = "连接失败，稍后自动重连";
						toastHandler.sendMessage(message);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (!Constants.isLogined && !Constants.exitRequested) {
						try {
							connectNetwork.connection.login(usernameEditText
									.getText().toString(), passwordEditText
									.getText().toString());
							Message message = Message.obtain();
							message.obj = "登录中";
							toastHandler.sendMessage(message);
							Constants.username = usernameEditText.getText()
									.toString();
							com.foodie_union.constant.Constants.isLogined = true;

							message = Message.obtain();
							message.obj = "登录成功";
							toastHandler.sendMessage(message);
							Intent intent = new Intent(Login.this,
									MapActivity.class);
							startActivity(intent);
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();

							Message message = Message.obtain();
							message.obj = "登录失败：用户名或密码不正确";
							toastHandler.sendMessage(message);
						} finally {

						}
					} else {
						Intent intent = new Intent(Login.this,
								MapActivity.class);
						startActivity(intent);
					}

				};
			}.start();
		}
	};
	OnClickListener returnOnClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Constants.exitRequested = true;
			onBackPressed();
		}
	};

	@SuppressLint("HandlerLeak")
	class MessageHandler extends Handler {
		public MessageHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			// 处理收到的消息，把交通信息显示在title上
			Toast.makeText(Login.this, msg.obj.toString(),
					Toast.LENGTH_SHORT * 5).show();
		}
	}

}
