package com.foodie_union.constant;

import com.foodie_union.network.ConnectNetwork;

public class Constants {
	public static String SERVER_IP = "169.254.58.12";
	// public static String SERVER_IP = "kkloyy.eicp.net";
	public static int SERVER_PORT = 5222;
	public static ConnectNetwork connectNetwork = null;
	public static boolean isLogined;
	public static String username = null;
	public static boolean exitRequested = false;
	public static final String[] TYPE_FILTER = { "火锅", "干锅", "小炒", "小吃", "奶茶" };
}
