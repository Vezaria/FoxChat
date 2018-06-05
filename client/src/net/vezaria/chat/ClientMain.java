package net.vezaria.chat;

import javax.swing.UIManager;

public class ClientMain {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ServerSelect serverSelect = new ServerSelect();
		serverSelect.show();
	}
}
