package net.vezaria.chat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HandshakePacket extends Packet {

	public static final int ID = 0x01;

	public int status = 0;
	
	public HandshakePacket() {}
	public HandshakePacket(int status) {
		this.status = status;
	}
	
	public void read(DataInputStream in) throws IOException {
		status = in.readInt();
	}

	public void write(DataOutputStream out) throws IOException {
		out.writeInt(status);
	}

	public int getID() {
		return ID;
	}
}
