package com.foodie_union.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private static List<Map<String, Object>> mData;
	public static HashMap<Integer, Boolean> isSelected;

	public OrderAdapter(List<Map<String, Object>> list, Context context) {
		mInflater = LayoutInflater.from(context);
		mData = list;
		init();
	}

	// 初始化
	@SuppressLint("UseSparseArrays")
	private void init() {
		// 这儿定义isSelected这个map是记录每个listitem的状态，初始状态全部为false。
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mData.size(); i++) {
			isSelected.put(i, false);
		}
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	public static Map<String, Object> getMyItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// convertView为null的时候初始化convertView。
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.orderitem, null);
			holder.count = (EditText) convertView
					.findViewById(R.id.orderitem_editText);
			holder.orderName = (TextView) convertView
					.findViewById(R.id.orderitem_textView);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.orderitem_checkBox);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.count.setText(mData.get(position).get("count").toString());
		holder.orderName.setText(mData.get(position).get("orderName")
				.toString());
		holder.checkBox.setChecked(isSelected.get(position));
		return convertView;
	}

	public static HashMap<Integer, Boolean> getIsSelected() {
		return isSelected;
	}

	public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
		OrderAdapter.isSelected = isSelected;
	}

	@SuppressLint("UseSparseArrays")
	public static void recovery() {
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < mData.size(); i++) {
			isSelected.put(i, false);
			mData.get(i).put("count", R.string.orderinitialvalue);
		}
	}

	public static void setCount(int count, int position) {
		mData.get(position).put("count", count);
	}

	public static class ViewHolder {
		public EditText count;
		public TextView orderName;
		public CheckBox checkBox;
	}
}
