package com.foodie_union.activity;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.foodie_union.constant.Constants;

public class MapActivity extends Activity {
	BMapManager mBMapMan = null;
	MapView mMapView = null;
	LinearLayout layout = null;
	Button searchButton = null, buttonToRemove;
	Button button1, button2, button3, button4, button5;
	Map<Integer, Button> buttonMap = null;
	int nameID, searchSize = 18;
	Spinner spinner = null;
	private ArrayAdapter<String> adapter;

	@SuppressLint("UseSparseArrays")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init("7a94583384b6852b009181a404f9ccbb", null);
		// 注锟解：锟斤拷锟斤拷锟斤拷锟斤拷setContentView前锟斤拷始锟斤拷BMapManager锟斤拷锟襟，凤拷锟斤拷岜拷锟�
		setContentView(R.layout.mappage);
		mMapView = (MapView) findViewById(R.id.bmapsView);
		mMapView.setBuiltInZoomControls(true);

		layout = (LinearLayout) findViewById(R.id.mylayout);

		spinner = (Spinner) findViewById(R.id.spinner1);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, Constants.TYPE_FILTER);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);

		// 添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

		// 设置默认值
		spinner.setVisibility(View.VISIBLE);

		searchButton = (Button) findViewById(R.id.search);
		searchButton.setOnClickListener(addbutton);

		button1 = new Button(this);
		button1.setText("火锅");
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(button1);
				buttonMap.remove(1);
			}
		});
		button2 = new Button(this);
		button2.setText("干锅");
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(button2);
				buttonMap.remove(2);
			}
		});
		button3 = new Button(this);
		button3.setText("小炒");
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(button3);
				buttonMap.remove(3);
			}
		});
		button4 = new Button(this);
		button4.setText("小吃");
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(button4);
				buttonMap.remove(4);
			}
		});
		button5 = new Button(this);
		button5.setText("奶茶");
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				layout.removeView(button5);
				buttonMap.remove(5);
			}
		});

		buttonMap = new HashMap<Integer, Button>();

		// 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟矫碉拷锟斤拷锟脚控硷拷
		MapController mMapController = mMapView.getController();
		// 锟矫碉拷mMapView锟侥匡拷锟斤拷权,锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷坪锟斤拷锟狡斤拷坪锟斤拷锟斤拷锟�
		// (24.82619651446854, 102.84932613372803)
		GeoPoint point = new GeoPoint((int) (24.82619651446854 * 1E6),
				(int) (102.84932613372803 * 1E6));
		// 锟矫革拷木锟轿筹拷裙锟斤拷锟揭伙拷锟紾eoPoint锟斤拷锟斤拷位锟斤拷微锟斤拷 (锟斤拷 * 1E6)
		mMapController.setCenter(point);// 锟斤拷锟矫碉拷图锟斤拷锟侥碉拷
		mMapController.setZoom(15);// 锟斤拷锟矫碉拷图zoom锟斤拷锟斤拷
		mBMapMan.start();

		/*
		 * 要锟斤拷锟斤拷overlay锟斤拷锟斤拷录锟绞憋拷锟揭拷坛锟絀temizedOverlay
		 * 锟斤拷锟斤拷锟斤拷锟斤拷锟铰硷拷时锟斤拷直锟斤拷锟斤拷锟絀temizedOverlay.
		 */
		class OverlayTest extends ItemizedOverlay<OverlayItem> {
			// 锟斤拷MapView锟斤拷锟斤拷ItemizedOverlay
			public OverlayTest(Drawable mark, MapView mapView) {
				super(mark, mapView);
			}

			protected boolean onTap(int index) {
				// 锟节此达拷锟斤拷item锟斤拷锟斤拷录锟�

				Intent intent = new Intent(MapActivity.this, OrderPage.class);
				intent.putExtra("index", index);
				Log.v("index", String.valueOf(index));
				startActivity(intent);
				return true;
			}

			public boolean onTap(GeoPoint pt, MapView mapView) {
				// 锟节此达拷锟斤拷MapView锟侥碉拷锟斤拷录锟斤拷锟斤拷锟斤拷锟斤拷锟�true时
				super.onTap(pt, mapView);
				return false;
			}
			// 锟斤拷2.1.1 锟斤拷始锟斤拷使锟斤拷 add/remove 锟斤拷锟斤拷overlay ,
			// 锟斤拷锟斤拷锟斤拷写锟斤拷锟铰接匡拷
			/*
			 * @Override protected OverlayItem createItem(int i) { return
			 * mGeoList.get(i); }
			 * 
			 * @Override public int size() { return mGeoList.size(); }
			 */
		}

		/**
		 * 锟斤拷锟斤拷要锟斤拷锟絆verlay锟侥地凤拷使锟斤拷锟斤拷锟铰达拷锟诫， 锟斤拷锟斤拷Activity锟斤拷onCreate()锟斤拷
		 */
		// 准锟斤拷要锟斤拷拥锟絆verlay
		// (24.82619651446854, 102.84932613372803)
		double mLat1 = 24.82419;
		double mLon1 = 102.84532;
		double mLat2 = 24.82617;
		double mLon2 = 102.84734;
		double mLat3 = 24.82815;
		double mLon3 = 102.84937;
		// 锟矫革拷木锟轿筹拷裙锟斤拷锟紾eoPoint锟斤拷锟斤拷位锟斤拷微锟斤拷 (锟斤拷 * 1E6)
		GeoPoint p1 = new GeoPoint((int) (mLat1 * 1E6), (int) (mLon1 * 1E6));
		GeoPoint p2 = new GeoPoint((int) (mLat2 * 1E6), (int) (mLon2 * 1E6));
		GeoPoint p3 = new GeoPoint((int) (mLat3 * 1E6), (int) (mLon3 * 1E6));
		// 准锟斤拷overlay图锟斤拷锟斤拷荩锟斤拷锟斤拷实锟斤拷锟斤拷锟斤拷薷锟�
		// Drawable mark = getResources().getDrawable(R.drawable.icon_marka);
		Drawable mark = getResources().getDrawable(R.drawable.mapmarker);
		// 锟斤拷OverlayItem准锟斤拷Overlay锟斤拷锟�

		OverlayItem item1 = new OverlayItem(p1, "item1", "item1");
		OverlayItem item2 = new OverlayItem(p2, "item2", "item2");
		OverlayItem item3 = new OverlayItem(p3, "item3", "item3");

		// 锟斤拷锟斤拷IteminizedOverlay
		OverlayTest itemOverlay = new OverlayTest(mark, mMapView);
		// 锟斤拷IteminizedOverlay锟斤拷拥锟組apView锟斤拷

		mMapView.getOverlays().clear();
		mMapView.getOverlays().add(itemOverlay);

		// 锟斤拷锟斤拷锟斤拷锟斤拷准锟斤拷锟斤拷锟斤拷锟斤拷准锟斤拷锟矫ｏ拷使锟斤拷锟斤拷锟铰凤拷锟斤拷锟斤拷锟斤拷overlay.
		// 锟斤拷锟給verlay,
		// 锟斤拷锟斤拷锟斤拷锟斤拷锟絆verlay时使锟斤拷addItem(List<OverlayItem>)效锟绞革拷锟�
		itemOverlay.addItem(item2);
		itemOverlay.addItem(item1);
		itemOverlay.addItem(item3);
		mMapView.refresh();
		mMapView.invalidate();
		// 删锟斤拷overlay .
		// itemOverlay.removeItem(itemOverlay.getItem(0));
		// mMapView.refresh();
		// 锟斤拷锟給verlay
		// itemOverlay.removeAll();
		// mMapView.refresh();
	}

	@Override
	protected void onDestroy() {
		mMapView.destroy();
		if (mBMapMan != null) {
			mBMapMan.destroy();
			mBMapMan = null;
		}
		Constants.isLogined = false;
		Constants.exitRequested = true;
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mMapView.onPause();
		if (mBMapMan != null) {
			mBMapMan.stop();
		}
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		if (mBMapMan != null) {
			mBMapMan.start();
		}
		super.onResume();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	private OnClickListener addbutton = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (buttonMap.containsKey(nameID)) {
				Toast.makeText(MapActivity.this, "筛选条件已存在!",
						Toast.LENGTH_SHORT * 5).show();
			} else {
				switch (nameID) {
				case 1:
					buttonMap.put(nameID, button1);
					layout.addView(button1);
					break;
				case 2:
					buttonMap.put(nameID, button2);
					layout.addView(button2);
					break;
				case 3:
					buttonMap.put(nameID, button3);
					layout.addView(button3);
					break;
				case 4:
					buttonMap.put(nameID, button4);
					layout.addView(button4);
					break;
				case 5:
					buttonMap.put(nameID, button5);
					layout.addView(button5);
					break;

				default:
					break;
				}

			}

		}
	};

	// 使用数组形式操作
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			nameID = arg2 + 1;
		}

		public void onNothingSelected(AdapterView<?> arg0) {
		}
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(MapActivity.this);
		builder.setMessage("确定要退出登录吗?");
		builder.setTitle("提示");
		builder.setPositiveButton("确认",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						MapActivity.this.finish();
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
			Constants.isLogined = false;
			Constants.exitRequested = true;
			dialog();
			return false;
		}
		return false;
	}

}