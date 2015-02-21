package com.tw;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import android.os.AsyncTask;
import android.util.Log;

public class WSServer extends AsyncTask<String, Integer, Long> {
	public static final int WS_PORT = 8777;
	public static final int DISCOVERY_PORT = 8778;
	
	private InetSocketAddress _address;
	public WSServer(InetSocketAddress address) {
		_address = address;
	}

	@Override
	protected Long doInBackground(String... params) {
		Server server = new Server(_address);
		server.start();
		return null;
	}
}

class Member {
	public String name;
	public WebSocket socket;
	public Member(WebSocket socket, String name) {
		this.name = name;
		this.socket = socket;
	}
};

class Server extends WebSocketServer {
	
	private Map<String, Member> members = new HashMap<String, Member>();
	
	public Server(InetSocketAddress address) {
		super(address);
		Log.i(NM.TAG, "WSServer start...");
	}

	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		Log.i(NM.TAG, "WSServer close connection to "+conn.getRemoteSocketAddress());
		members.remove(conn.getRemoteSocketAddress().getHostName());
	}

	@Override
	public void onError(WebSocket conn, Exception ex) {
		Log.i(NM.TAG, "error: "+ex.getMessage());
	}

	@Override
	public void onMessage(WebSocket conn, String message) {
//		Log.i(NM.TAG, "recv: "+message);
		WebSocket socket;
		Set<Entry<String, Member>> ms = members.entrySet();
		for (Entry<String, Member> entry : ms) {
			socket = entry.getValue().socket;
			socket.send(message);
		}
		//conn.send(message);
	}

	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		Log.i(NM.TAG, "WSServer new connection to "+conn.getRemoteSocketAddress());
		Log.i(NM.TAG, "request: "+conn.getHttpContent().url);
		
		Member member = new Member(conn, conn.getRemoteSocketAddress().getHostName());
		members.put(conn.getRemoteSocketAddress().getHostName(), member);
	}
}