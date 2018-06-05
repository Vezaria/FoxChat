package net.vezaria.chat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.vezaria.chat.ChatClient;

public abstract class Packet {

	private final static Map<Integer, Class<? extends Packet>> PACKETS = new HashMap<>();
	private final static Map<Class<? extends Packet>, PacketHandler<?>> HANDLERS = new HashMap<>();
	static {
		PACKETS.put(HelloPacket.ID, HelloPacket.class);
		
		PACKETS.put(HandshakePacket.ID, HandshakePacket.class);
		HANDLERS.put(HandshakePacket.class, new HandshakePacket.Handler());
	}
	
	public static Packet getPacket(int id) {
		if(PACKETS.containsKey(id)) {
			try {
				return PACKETS.get(id).newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				return null;
			}
		} else {
			return null;
		}
	}
	
	public static PacketHandler<?> getHandler(Class<? extends Packet> packet) {
		return HANDLERS.getOrDefault(packet, null);
	}
	
	public abstract void read(DataInputStream in) throws IOException;
	public abstract void write(DataOutputStream out) throws IOException;
	public abstract int getID();
	
	public static abstract class PacketHandler<T extends Packet> {
		
		public abstract void handle(T packet, ChatClient client);
	}
}
