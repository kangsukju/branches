package com.tw;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class Utils {

	static public String getMyWifiAddress(Activity activity) throws Exception {

		return getMyWifiInetAddress(activity).getHostAddress();
	}
	
	static public InetAddress getMyWifiInetAddress(Activity activity) throws Exception {
		WifiManager wifiManager = (WifiManager) activity.getSystemService(activity.WIFI_SERVICE);
		if (wifiManager == null) {
			Log.e(NM.TAG, "not prepared WIFI connection");
			throw new Exception("not prepared WIFI connection");
		}
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo == null) {
			Log.e(NM.TAG, "not prepared WIFI connection");
			throw new Exception("not prepared WIFI connection");
		}
		
		byte [] ipaddr = toIPByteArray(wifiInfo.getIpAddress());
		return InetAddress.getByAddress(ipaddr);
	}
	
	static public byte[] toIPByteArray(int addr){
        return new byte[]{(byte)addr,(byte)(addr>>>8),(byte)(addr>>>16),(byte)(addr>>>24)};
    }
	
	static public byte [] getByteArrayData(Object obj) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		ObjectOutputStream o = new ObjectOutputStream(b);
		o.writeObject(obj);
		return b.toByteArray();
	}
	
	static public Object getObjectData(byte[] bytes) throws ClassNotFoundException, IOException {
		ByteArrayInputStream b = new ByteArrayInputStream(bytes);
		ObjectInputStream o = new ObjectInputStream(b);
		return o.readObject();
	}
}
