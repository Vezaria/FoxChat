package net.vezaria.chat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.vezaria.chat.Connection;

public class HelloPacket extends Packet {
	
	public static final int ID = 0x00;
	
	public String nickname = "";

	public HelloPacket() { }
	public HelloPacket(String nickname) {
		this.nickname = nickname;
	}
	
	public void read(DataInputStream in) throws IOException {
		nickname = in.readUTF();
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeUTF(nickname);
	}

	public int getID() {
		return ID;
	}
	
	public static class Handler extends PacketHandler<HelloPacket> {
		public void handle(HelloPacket packet, Connection conn) {
			HandshakePacket handshake = new HandshakePacket(0);
			
			if(packet.nickname.equals("banned")) {
				handshake.status = 1;
			}
			if(packet.nickname.equals("illegal")) {
				handshake.status = 2;
			}
			
			conn.sendPacket(handshake);
			
			if(handshake.status != 0) {
				conn.disconnect();
			}
		}
	}
}
