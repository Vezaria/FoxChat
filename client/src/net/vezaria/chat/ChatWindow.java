package net.vezaria.chat;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JList;

public class ChatWindow {

	private JFrame frmFoxchatServer;

	private ChatClient client;
	
	public ChatWindow(ChatClient client) {
		this.client = client;
		
		initialize();
		frmFoxchatServer.setVisible(true);
	}

	private void initialize() {
		frmFoxchatServer = new JFrame();
		frmFoxchatServer.setTitle("FoxChat - Server Name");
		frmFoxchatServer.setBounds(100, 100, 450, 300);
		frmFoxchatServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmFoxchatServer.getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		JPanel pnlLobby = new JPanel();
		tabbedPane.addTab("Lobby", null, pnlLobby, null);
		
		JLabel lblTitle = new JLabel("Rooms");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_pnlLobby = new GroupLayout(pnlLobby);
		gl_pnlLobby.setHorizontalGroup(
			gl_pnlLobby.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLobby.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlLobby.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitle)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE))
					.addGap(12))
		);
		gl_pnlLobby.setVerticalGroup(
			gl_pnlLobby.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLobby.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTitle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		pnlLobby.setLayout(gl_pnlLobby);
	}
}
