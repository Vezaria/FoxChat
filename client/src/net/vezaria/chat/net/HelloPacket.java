package net.vezaria.chat.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class HelloPacket extends Packet {
	
	public static final int ID = 0x00;
	
	public String nickname = "";

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
}
