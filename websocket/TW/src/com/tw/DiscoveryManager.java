package com.tw;

import java.net.InetAddress;

public class DiscoveryManager extends Thread {

	public static final int DISCOVERY_DISCOVER = 1;
	public static final int DISCOVERY_OFFER = 2;
	private static final int DEFAULT_DISCOVERY_INTERVAL = 5;
	
	private int discovery_type;
	private InetAddress offer_address;
	private int interval;
	private DiscoveryFindRoomHandler findRoomHandler;
	
	public DiscoveryManager(int discovery_type, InetAddress offer_address, DiscoveryFindRoomHandler findRoomHandler, int interval) {
		this.discovery_type = discovery_type;
		this.offer_address = offer_address;
		this.findRoomHandler = findRoomHandler;
		this.interval = interval;
	}
	public DiscoveryManager(int discovery_type, InetAddress offer_address, int interval) {
		this(discovery_type, offer_address, null, interval);
	}
	public DiscoveryManager(int discovery_type, InetAddress offer_address) {
		this(discovery_type, offer_address, null, DEFAULT_DISCOVERY_INTERVAL);
	}
	public DiscoveryManager(int discovery_type, DiscoveryFindRoomHandler findRoomHandler, int interval) {
		this(discovery_type, null, findRoomHandler, interval);
	}
	public DiscoveryManager(int discovery_type, DiscoveryFindRoomHandler findRoomHandler) {
		this(discovery_type, null, findRoomHandler, DEFAULT_DISCOVERY_INTERVAL);
	}

	@Override
	public void run() {
		Discovery discovery = null;
		
		try {
			discovery = new Discovery(WSServer.DISCOVERY_PORT);
			
			while (!Thread.currentThread().isInterrupted()) {
				switch (discovery_type){
				case DISCOVERY_DISCOVER:
					discovery.discover(findRoomHandler);
					break;
				case DISCOVERY_OFFER:
					discovery.offer(offer_address);
					break;
				default:
					return;
				}
				Thread.sleep(interval * 1000);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
