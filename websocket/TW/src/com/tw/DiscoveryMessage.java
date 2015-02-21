package com.tw;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Arrays;

public class DiscoveryMessage implements Serializable {
	static private byte [] fixed_magic = new byte[]{0x01, 0x02, 0x03, 0x04, 0x05, 0x06};
	byte [] magic;
	byte protocol;
	byte version;
	byte service;
	byte [] server;
	
	
	
	@Override
	public String toString() {
		return "NeighborhoodMsg [magic=" + Arrays.toString(magic)
				+ ", protocol=" + protocol + ", version=" + version
				+ ", service=" + service + ", server="
				+ Arrays.toString(server) + "]";
	}
	
	public DiscoveryMessage(InetAddress address) {
		magic = fixed_magic.clone();
		protocol = 0;
		version = 0;
		service = 1;
		server = address.getAddress();
	}
	public boolean validateMagic() {
		for (int i=0; i<fixed_magic.length; i++) {
			if (magic[i] != fixed_magic[i]) {
				return false;
			}
		}
		return true;
	}
};
