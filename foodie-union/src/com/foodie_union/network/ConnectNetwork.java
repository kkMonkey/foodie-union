package com.foodie_union.network;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;

import com.foodie_union.constant.Constants;

import android.util.Log;

public class ConnectNetwork {

	public Connection connection;
	public Thread networkThread = null;
	private static String INFO = "infomation";
	private static String ERR = "error";
	private ConnectionConfiguration connConfig;

	public ConnectNetwork() {
		// TODO Auto-generated constructor stub
		connConfig = new ConnectionConfiguration(Constants.SERVER_IP,
				Constants.SERVER_PORT);
		connConfig.setSecurityMode(SecurityMode.disabled);
		// connConfig.setSecurityMode(SecurityMode.required);
		connConfig.setSASLAuthenticationEnabled(false);
		connConfig.setCompressionEnabled(false);
		networkThread = new Thread() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				do {
					connect();
					while (isConnected() && !Constants.exitRequested) {
						try {
							Thread.sleep(30000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally {

						}
					}
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {

					}
				} while (!isConnected() && !Constants.exitRequested);
			}
		};
		networkThread.start();
	}

	public boolean isConnected() {
		return connection.isConnected();
	}

	public void connect() {
		connection = new XMPPConnection(connConfig);
		Log.i(INFO, "Connect to server now...");
		try {
			// Connect to the server
			connection.connect();
			// textView.setText("???");
			Log.i(INFO, "connect success!!!");
		} catch (XMPPException e) {
			Log.e(ERR, "connect failed! Reconnect... ");
		} finally {

		}
	}

}
