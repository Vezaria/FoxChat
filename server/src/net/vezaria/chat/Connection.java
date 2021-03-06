package net.vezaria.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import net.vezaria.chat.net.Packet;
import net.vezaria.chat.net.Packet.PacketHandler;

public class Connection implements Runnable {

	private FoxServer server;
	private Socket socket;

	public Connection(FoxServer server, Socket socket) {
		this.server = server;
		this.socket = socket;
	}
	
	public void run() {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			while(!socket.isClosed()) {
				byte id = in.readByte();
				int length = in.readInt();
				byte[] data = new byte[length];
				in.read(data);
				
				Packet p = Packet.getPacket(id);
				if(p != null) {
					p.read(new DataInputStream(new ByteArrayInputStream(data)));
					
					PacketHandler handler = Packet.getHandler(p.getClass());
					if(handler != null) {
						handler.handle(p, this);
					}
				} else {
					System.out.println("Unknown packet (0x" + Integer.toHexString(id) + ").");
				}
			}
		} catch(Exception e) {
			//e.printStackTrace();
		}
		System.out.println("Connection dropped.");
	}
	
	public boolean sendPacket(Packet packet) {
		try {
			byte[] payload = createPayload(packet);
			byte id = (byte)packet.getID();
			int length = payload.length;
			
			ByteArrayOutputStream bos;
			DataOutputStream packetData = new DataOutputStream(bos = new ByteArrayOutputStream(length + 5));
			packetData.writeByte(id);
			packetData.writeInt(length);
			packetData.write(payload);
			
			socket.getOutputStream().write(bos.toByteArray());
			socket.getOutputStream().flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static final int BUFFER_SIZE = 2048 * 4;
	private byte[] createPayload(Packet p) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFFER_SIZE);
		DataOutputStream o = new DataOutputStream(baos);
		p.write(o);
		return baos.toByteArray();
	}
	
	public void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
