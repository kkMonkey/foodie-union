package com.foodie_union.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.foodie_union.constant.Constants;
import com.foodie_union.entity.ChatMsgEntity;

@SuppressLint("SimpleDateFormat")
public class ChatMain extends Activity {
	// chat_main
	private final String TAG = "tag";
	private Connection mConnection;
	private ChatManager chatManager;
	private ListView chat_window;
	private Button chat_send;
	private EditText chat_editmessage;
	private String message;
	private ChatAdapter adapter;
	private List<ChatMsgEntity> mDataArrays;
	private InputMethodManager manager;
	private String herName;
	private String myOrder;
	private Chat chat;

	// kkloyy@kkloyy-Dell-System-Inspiron-N4110
	// nzp9dotjpm0v1zd

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);

		Intent intent = getIntent();
		herName = intent.getStringExtra("herName");
		myOrder = intent.getStringExtra("myOrder");

		chat_window = (ListView) findViewById(R.id.chat_window);
		chat_editmessage = (EditText) findViewById(R.id.chat_editmessage);
		chat_send = (Button) findViewById(R.id.chat_sendbtn);

		mDataArrays = new ArrayList<ChatMsgEntity>();

		adapter = new ChatAdapter(this, mDataArrays);
		chat_window.setAdapter(adapter);

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		mConnection = Constants.connectNetwork.connection;
		chatManager = mConnection.getChatManager();

		new Thread(runnable).start();

		chat = chatManager.createChat(herName + "@nzp9dotjpm0v1zd",
				new MessageListener() {
					@Override
					public void processMessage(Chat arg0, Message arg1) {
						System.out.println(arg1.getBody());
					}
				});

		sendMessage(myOrder);

		chat_send.setOnClickListener(new View.OnClickListener() {

			@SuppressLint("SimpleDateFormat")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (chat_editmessage.getText().length() < 1) {
					Toast.makeText(ChatMain.this, "不能发送空消息",
							Toast.LENGTH_SHORT * 5).show();
					return;
				}
				sendMessage(chat_editmessage.getText().toString());
			}

		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	Runnable runnable = new Runnable() {
		public void run() {
			chatManager.addChatListener(new ChatManagerListener() {
				@Override
				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(new MessageListener() {
						@SuppressLint("SimpleDateFormat")
						@Override
						public void processMessage(Chat arg0, Message arg1) {
							message = arg1.getBody();

							if (message != null) {
								SimpleDateFormat format = new SimpleDateFormat(
										"yyyy-MM-dd hh:mm:ss");

								ChatMsgEntity entity = new ChatMsgEntity();
								entity.setUsername(arg1.getFrom());
								entity.setSendtime(format.format(new Date()));
								entity.setMessage(message);
								entity.setMsgType(true);

								mDataArrays.add(entity);
								hander.sendEmptyMessage(0);
							}
						}
					});

				}
			});

			mConnection.addPacketListener(new PacketListener() {

				@Override
				public void processPacket(Packet arg0) {
					Log.i(TAG, arg0.getFrom());
				}

			}, new PacketFilter() {

				@Override
				public boolean accept(Packet arg0) {
					return true;
				}

			});

			IQ iq = new IQ() {
				@Override
				public String getChildElementXML() {
					StringBuilder buf = new StringBuilder();
					buf.append("<query xmlns=\"com:im:group\"/>");
					return buf.toString();
				}

			};
			mConnection.sendPacket(iq);

		}
	};

	@SuppressLint("HandlerLeak")
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
		chat_window.setSelection(adapter.getCount());
	};

	@SuppressLint("HandlerLeak")
	private Handler hander = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				adapter.notifyDataSetChanged(); // 发送消息通知ListView更新
				chat_window.setSelection(adapter.getCount());
				break;
			default:
				// do something
				break;
			}
		}
	};

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

	private void sendMessage(String message) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setUsername(Constants.username);
		entity.setSendtime(format.format(new Date()));
		entity.setMessage(message);
		entity.setMsgType(false);

		mDataArrays.add(entity);

		adapter.notifyDataSetChanged();
		chat_window.setSelection(adapter.getCount());

		try {
			chat.sendMessage(message);
			chat_editmessage.setText(null);
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {
			manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

}
