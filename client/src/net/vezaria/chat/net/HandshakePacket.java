package net.vezaria.chat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.vezaria.chat.ChatClient;
import net.vezaria.chat.State;

public class HandshakePacket extends Packet {

	public static final int ID = 0x01;

	public int status = 0;
	
	public void read(DataInputStream in) throws IOException {
		status = in.readInt();
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeInt(status);
	}

	public int getID() {
		return ID;
	}
	
	public static class Handler extends PacketHandler<HandshakePacket> {
		public void handle(HandshakePacket packet, ChatClient client) {
			if(packet.status == 0) {
				client.setState(State.CONNECTED);
			} else if(packet.status == 1) {
				client.setState(State.BANNED);
			} else if(packet.status == 2) {
				client.setState(State.ILLEGAL_NAME);
			} else {
				client.setState(State.DISCONNECTED);
			}
		}
	}
}
