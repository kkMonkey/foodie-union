package com.foodie_union.network;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Registration;

import android.util.Log;

public class IMMain {

	public static Connection mConnection;
	private static String INFO = "infomation";
	private static String ERR = "error";

	public static boolean connect(String server_ip) {
		ConnectionConfiguration connConfig = new ConnectionConfiguration(
				server_ip, 5222);
		connConfig.setSecurityMode(SecurityMode.disabled);
		// connConfig.setSecurityMode(SecurityMode.required);
		connConfig.setSASLAuthenticationEnabled(false);
		connConfig.setCompressionEnabled(false);
		mConnection = new XMPPConnection(connConfig);
		int reConnectTimes = 1;
		while (!mConnection.isConnected() && reConnectTimes++ < 6) {
			Log.i(INFO, "Connect to server now...");
			try {
				// Connect to the server
				mConnection.connect();
				// textView.setText("???");
				Log.i(INFO, "connect success!!!");
			} catch (XMPPException e) {
				Log.e(ERR, "connect failed! Reconnect " + reConnectTimes, e);
			} finally {

			}
		}
		return (mConnection.isConnected());
	}

	public static boolean login(String username, String password) {
		boolean flag = false;
		if (!mConnection.isAuthenticated()) {
			Log.i(INFO, "Login to server now...");
			try {
				mConnection.login(username, password);
				Log.i(INFO, "login success!");
				flag = true;
			} catch (XMPPException e) {
				Log.e(ERR, "login failed!");
				flag = false;
			} finally {

			}
			return flag;
		}

		return false;
	}

	public static IQ register(String username, String password) {
		Registration registration = new Registration();
		registration.setType(IQ.Type.SET);
		registration.setTo(mConnection.getServiceName());
		registration.setUsername(username);
		registration.setPassword(password);
		PacketFilter filter = new AndFilter(new PacketIDFilter(
				registration.getPacketID()), new PacketTypeFilter(IQ.class));
		PacketCollector collector = mConnection.createPacketCollector(filter);
		mConnection.sendPacket(registration);

		IQ result = (IQ) collector.nextResult(SmackConfiguration
				.getPacketReplyTimeout());
		// Stop queuing results
		collector.cancel();
		/*
		 * if (result == null) { Toast.makeText(this, "服务器连接超时",
		 * Toast.LENGTH_SHORT).show(); } else if (result.getType() ==
		 * IQ.Type.ERROR) { if
		 * (result.getError().toString().equalsIgnoreCase("conflict(409)")) {
		 * Toast.makeText(this, "用户名已存在", Toast.LENGTH_SHORT).show(); } else {
		 * Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show(); } } else if
		 * (result.getType() == IQ.Type.RESULT) { Toast.makeText(this,
		 * "恭喜您注册成功", Toast.LENGTH_SHORT).show(); }
		 */

		return result;
	}

	

}
