package com.tw;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.util.Log;

public class Discovery {
	private int port;
	
	public Discovery(int _port) throws Exception {
		port = _port;
	}
	public void offer(InetAddress address) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		socket.setReuseAddress(true);
		socket.setBroadcast(true);		
		DiscoveryMessage msg = new DiscoveryMessage(address);
		Log.i(NM.TAG, "port: "+port+"offer: "+msg);
		byte [] data = Utils.getByteArrayData(msg);
		DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName("255.255.255.255"), port);
		socket.send(packet);
		socket.close();
	}
	public void discover(DiscoveryFindRoomHandler findRoomHandler) throws Exception {
		DatagramSocket socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
		socket.setBroadcast(false);
		//socket.setSoTimeout(1000);
		Log.i(NM.TAG, "start discover");
		byte [] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		socket.receive(packet);
		Log.i(NM.TAG, "recv discover");
		
		Object obj = Utils.getObjectData(packet.getData());
		DiscoveryMessage msg = (DiscoveryMessage) obj;
		if (msg.validateMagic()) {
			if (findRoomHandler != null) {
				findRoomHandler.findRoom(msg);
			}
		}
		socket.close();
	}
}
 