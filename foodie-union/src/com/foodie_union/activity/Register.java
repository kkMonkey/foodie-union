package com.foodie_union.activity;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

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

public class Register extends Activity {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText passwordEditText2;
	private Button registerButton;
	private Button returnButton;
	private ConnectNetwork connectNetwork;
	private Connection connection;
	private InputMethodManager manager;
	private static Handler toastHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		usernameEditText = (EditText) findViewById(R.id.register_username);
		passwordEditText = (EditText) findViewById(R.id.register_password);
		passwordEditText2 = (EditText) findViewById(R.id.register_password2);
		registerButton = (Button) findViewById(R.id.register_register);
		returnButton = (Button) findViewById(R.id.register_return);

		usernameEditText.requestFocus();

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		connectNetwork = com.foodie_union.constant.Constants.connectNetwork;
		connection = connectNetwork.connection;

		// 得到当前线程的Looper实例，由于当前线程是UI线程也可以通过Looper.getMainLooper()得到
		Looper looper = Looper.myLooper();
		// 此处甚至可以不需要设置Looper，因为 Handler默认就使用当前线程的Looper
		toastHandler = new MessageHandler(looper);

		registerButton.setOnClickListener(registerOnClick);
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

	OnClickListener registerOnClick = new OnClickListener() {

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
						if (connection.isConnected())
							break;
						connectNetwork.connect();
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
					String username = usernameEditText.getText().toString();
					String password = passwordEditText.getText().toString();
					if (!password
							.equals(passwordEditText2.getText().toString())) {
						Message message = Message.obtain();
						message.obj = "两次密码不相同";
						toastHandler.sendMessage(message);
					} else {
						IQ result = myRegister(username, password);
						if (result == null) {
							Message message = Message.obtain();
							message.obj = "服务器连接超时";
							toastHandler.sendMessage(message);
						} else if (result.getType() == IQ.Type.ERROR) {
							if (result.getError().toString()
									.equalsIgnoreCase("conflict(409)")) {
								Message message = Message.obtain();
								message.obj = "用户名已存在";
								toastHandler.sendMessage(message);
							} else {
								Message message = Message.obtain();
								message.obj = "注册失败";
								toastHandler.sendMessage(message);
							}
						} else if (result.getType() == IQ.Type.RESULT) {
							Message message = Message.obtain();
							message.obj = "恭喜您注册成功";
							toastHandler.sendMessage(message);
							Constants.username = username;
							Constants.isLogined = true;
							Intent intent = new Intent(Register.this,
									MapActivity.class);
							startActivity(intent);
						}
					}
				};
			}.start();
		}

		private IQ myRegister(String username, String password) {
			// TODO Auto-generated method stub
			Registration registration = new Registration();
			registration.setType(IQ.Type.SET);
			registration.setTo(connection.getServiceName());
			registration.setUsername(username);
			registration.setPassword(password);
			PacketFilter filter = new AndFilter(new PacketIDFilter(
					registration.getPacketID()), new PacketTypeFilter(IQ.class));
			PacketCollector collector = connection
					.createPacketCollector(filter);
			connection.sendPacket(registration);

			IQ result = (IQ) collector.nextResult(SmackConfiguration
					.getPacketReplyTimeout());
			// Stop queuing results
			collector.cancel();
			return result;
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
			Toast.makeText(Register.this, msg.obj.toString(),
					Toast.LENGTH_SHORT * 5).show();
		}
	}

}
