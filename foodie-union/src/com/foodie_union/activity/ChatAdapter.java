package com.foodie_union.activity;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.foodie_union.container.ChatMsgEntity;

public class ChatAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;// 收到对方的消息
		int IMVT_TO_MSG = 1;// 自己发送出去的消息
	}

	private static final int ITEMCOUNT = 2;// 消息类型的总数
	private List<ChatMsgEntity> coll;// 消息对象数组
	private LayoutInflater mInflater;

	public ChatAdapter(Context context, List<ChatMsgEntity> coll) {
		// TODO Auto-generated constructor stub
		this.coll = coll;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coll.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return coll.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/**
	 * 得到Item的类型，是对方发过来的消息还是自己发送出去的。
	 */
	public int getItemViewType(int position) {
		ChatMsgEntity entity = coll.get(position);

		if (entity.getMsgType()) {// 收到的消息
			return IMsgViewType.IMVT_COM_MSG;
		} else {// 自己发送的消息
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	/**
	 * Item类型的总数
	 */
	public int getViewTypeCount() {
		return ITEMCOUNT;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ChatMsgEntity entity = coll.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_left, null);
			} else {
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_text_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.username = (TextView) convertView
					.findViewById(R.id.chat_username);
			viewHolder.sendtime = (TextView) convertView
					.findViewById(R.id.chat_sendtime);
			viewHolder.context = (TextView) convertView
					.findViewById(R.id.chat_context);
			viewHolder.isComMsg = isComMsg;

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.sendtime.setText(entity.getSendtime());
		viewHolder.context.setText(entity.getMessage());
		viewHolder.username.setText(entity.getUsername());

		return convertView;

		// ViewHolder holder = null;
		// // convertView为null的时候初始化convertView。
		// if (convertView == null) {
		// holder = new ViewHolder();
		//
		// if (list.get(list.size() - 1).get("type").equals("mine")) {
		// convertView = mInflater.inflate(
		// R.layout.chatting_item_msg_text_right, null);
		// } else {
		// convertView = mInflater.inflate(
		// R.layout.chatting_item_msg_text_left, null);
		// }
		// holder.username = (TextView) convertView
		// .findViewById(R.id.chat_username);
		// holder.context = (TextView) convertView
		// .findViewById(R.id.chat_context);
		// holder.sendtime = (TextView) convertView
		// .findViewById(R.id.chat_sendtime);
		// convertView.setTag(holder);
		// } else {
		// holder = (ViewHolder) convertView.getTag();
		// }
		// holder.username.setText(list.get(position).get("sender").toString());
		// holder.context.setText(list.get(position).get("msg").toString());
		// holder.sendtime.setText(list.get(position).get("time").toString());
		// return convertView;
	}

	//
	// public static void addItem(Map<String, Object> map) {
	// list.add(map);
	// }

	public static class ViewHolder {
		public TextView username;
		public TextView context;
		public TextView sendtime;
		public boolean isComMsg = true;
	}

}
