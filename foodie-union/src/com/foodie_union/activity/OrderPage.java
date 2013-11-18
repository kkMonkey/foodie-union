package com.foodie_union.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.foodie_union.activity.OrderAdapter.ViewHolder;

public class OrderPage extends Activity {

	private ListView listView;
	private Button clearButton;
	private Button watchButton;
	private Button confirmButton;
	private OrderAdapter adapter;
	private String shopKeeperName;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderpage);

		listView = (ListView) findViewById(R.id.orderpage_listView);
		clearButton = (Button) findViewById(R.id.orderpage_clear);
		watchButton = (Button) findViewById(R.id.orderpage_watch);
		confirmButton = (Button) findViewById(R.id.orderpage_confirm);
		
		Intent intent=new Intent();
		double shopKeeperLon=intent.getIntExtra("shopKeeperLon", 0);
		double shopKeeperLat=intent.getIntExtra("shopKeeperLat", 0);
		shopKeeperName="我是店家";

		adapter = new OrderAdapter(getData(), this);
		listView.setAdapter(adapter);
		listView.setItemsCanFocus(false);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				final ViewHolder vHollder = (ViewHolder) view.getTag();
				// 在每次获取点击的item时将对于的checkbox状态改变，同时修改map的值。
				vHollder.checkBox.toggle();

				final int myPosition = position;
				final EditText inputServer = new EditText(OrderPage.this);

				if (!OrderAdapter.isSelected.get(position)) {
					inputServer.setText("1");
					DigitsKeyListener numericOnlyListener = new DigitsKeyListener(
							false, true);
					inputServer.setKeyListener(numericOnlyListener);
					AlertDialog.Builder builder = new AlertDialog.Builder(
							OrderPage.this);
					builder.setTitle("订单")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setView(inputServer)
							.setNegativeButton("取消", null);
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									OrderAdapter.setCount(Integer
											.parseInt(inputServer.getText()
													.toString()), myPosition);
									adapter.notifyDataSetChanged();

									OrderAdapter.isSelected.put(myPosition,
											vHollder.checkBox.isChecked());
								}
							});
					builder.show();
				} else {
					OrderAdapter.setCount(R.string.orderinitialvalue,
							myPosition);
					adapter.notifyDataSetChanged();
					OrderAdapter.isSelected.put(myPosition,
							vHollder.checkBox.isChecked());
				}
			}
		});

		clearButton.setOnClickListener(clearOnClickListener);
		watchButton.setOnClickListener(watchOnClickListener);
		confirmButton.setOnClickListener(confirmOnClickListener);
		confirmButton.requestFocus();
	}

	private List<Map<String, Object>> getData() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;

		map = new HashMap<String, Object>();
		map.put("ischecked", true);
		map.put("orderName", "小炒牛肉1");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", true);
		map.put("orderName", "小炒鸡肉1");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", true);
		map.put("orderName", "小炒鸭肉1");
		map.put("count", 0);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒牛肉2");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸡肉2");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸭肉2");
		map.put("count", 0);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒牛肉3");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸡肉3");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸭肉3");
		map.put("count", 0);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒牛肉4");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸡肉4");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸭肉4");
		map.put("count", 0);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒牛肉5");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸡肉5");
		map.put("count", 0);
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("ischecked", false);
		map.put("orderName", "小炒鸭肉5");
		map.put("count", 0);
		list.add(map);
		return list;
	}

	OnClickListener clearOnClickListener = new OnClickListener() {

		@SuppressLint("UseSparseArrays")
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			OrderAdapter.recovery();
			adapter.notifyDataSetChanged();
		}
	};
	OnClickListener watchOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < adapter.getCount(); i++) {
				if (OrderAdapter.isSelected.get(i)) {
					Map<String, Object> map = OrderAdapter.getMyItem(i);
					stringBuilder.append(map.get("orderName"));
					stringBuilder.append("--->");
					stringBuilder.append(map.get("count"));
					stringBuilder.append("份\n");
				}
			}
			if (stringBuilder.length() < 5) {
				stringBuilder.append("您的订单为空");
			}
			new AlertDialog.Builder(OrderPage.this).setTitle("订单")
					.setMessage(stringBuilder).show();
		}
	};

	OnClickListener confirmOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 0; i < adapter.getCount(); i++) {
				if (OrderAdapter.isSelected.get(i)) {
					Map<String, Object> map = OrderAdapter.getMyItem(i);
					stringBuilder.append(map.get("orderName"));
					stringBuilder.append("--->");
					stringBuilder.append(map.get("count"));
					stringBuilder.append("份\n");
				}
			}
			if (stringBuilder.length() < 5) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						OrderPage.this);
				builder.setTitle("订单")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setMessage("您的订单为空, 点击确定将进入与店家的聊天界面")
						.setNegativeButton("取消", null);
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								Intent intent = new Intent(OrderPage.this, ChatMain.class);
								intent.putExtra("herName", shopKeeperName);
								intent.putExtra("myOrder", "");
								startActivity(intent);
							}
						});
				builder.show();
			} else {
				Intent intent = new Intent(OrderPage.this, ChatMain.class);
				intent.putExtra("herName", shopKeeperName);
				intent.putExtra("myOrder", stringBuilder.toString());
				startActivity(intent);
			}
		}
	};

}
