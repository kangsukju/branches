package com.example.websocket;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import android.os.AsyncTask;
import android.util.Log;

public class Server extends WebSocketServer {
	
	String TAG = "kinow";

    public Server(InetSocketAddress address) {
        super(address);
        Log.i(TAG, "Server");
        File f;
		try {
			f = File.createTempFile("kinow", null);
			Log.i(TAG, f.getAbsolutePath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        System.out.println("new connection to " + conn.getRemoteSocketAddress());
        Log.i(TAG, "new connection to " + conn.getRemoteSocketAddress());
        conn.send("hellow");
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
        Log.i(TAG, "closed " + conn.getRemoteSocketAddress() + " with exit code " + code + " additional info: " + reason);
    }

    static int num = 0;
    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("received message from " + conn.getRemoteSocketAddress() + ": " + message);
        Log.i(TAG, "received message from " + conn.getRemoteSocketAddress() + ": " + message);
        conn.send("from server: "+num++);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.err.println("an error occured on connection " + ((conn != null) ? conn.getRemoteSocketAddress() : "") + ":" + ex);
        Log.i(TAG, "an error occured on connection "  + ex);
    }

    /*
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8887;

        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
    }
    */
}

class AsyncServer extends AsyncTask<String, Integer, Long>
{

	@Override
	protected Long doInBackground(String... params) {
        String host = "192.168.0.8";
        int port = 8887;

        WebSocketServer server = new Server(new InetSocketAddress(host, port));
        server.run();
        
		return null;
	}
	
}
