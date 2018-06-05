package net.vezaria.chat;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.vezaria.chat.components.PlaceholderTextField;

public class ServerSelect {
	
	private JFrame frmServerSelect;
	private PlaceholderTextField txtAddress;
	private PlaceholderTextField txtNickname;
	private JButton btnConnect;
	private JLabel lblStatus;
	
	public ServerSelect() {
		initialize();
	}
	
	private void initialize() {
		frmServerSelect = new JFrame();
		frmServerSelect.setTitle("FoxChat - Select a Server");
		frmServerSelect.setResizable(false);
		frmServerSelect.setBounds(100, 100, 344, 165);
		frmServerSelect.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlTop = new JPanel();
		pnlTop.setLayout(new BoxLayout(pnlTop, BoxLayout.Y_AXIS));
		JLabel lblTitle = new JLabel("FoxChat");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlTop.add(lblTitle);
		
		JLabel lblVersion = new JLabel("0.0.1");
		lblVersion.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlTop.add(lblVersion);
		
		JSeparator separator = new JSeparator();
		pnlTop.add(separator);
		
		txtAddress = new PlaceholderTextField();
		txtAddress.setPlaceholder("Address");
		txtAddress.setColumns(10);
		
		txtNickname = new PlaceholderTextField();
		txtNickname.setPlaceholder("Nickname");
		txtNickname.setColumns(10);
		
		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nickname = txtNickname.getText().trim();
				String address = txtAddress.getText().trim();
				if(nickname.isEmpty()) {
					lblStatus.setText("You must enter a nickname!");
					return;
				}
				if(address.isEmpty()) {
					lblStatus.setText("You must enter an address!");
					return;
				}
				ChatClient client = new ChatClient();
				
				lblStatus.setText("Connecting...");

				lockControls();
				new Thread(new Runnable() {
					public void run() {
						State state = client.connect(address, nickname);
						if(state == State.BANNED) {
							lblStatus.setText("Have been banned from this server!");
						} else if(state == State.ILLEGAL_NAME) {
							lblStatus.setText("That name is not allowed!");
						} else if(state == State.DISCONNECTED) {
							lblStatus.setText("Could not connect.");
						} else if(state == State.CONNECTED) {
							lblStatus.setText("Connected!");
							new ChatWindow(client);
							hide();
						}
						unlockControls();
					}
				}).start();
			}
		});
		
		JPanel pnlBottom = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmServerSelect.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, 338, GroupLayout.PREFERRED_SIZE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(txtAddress, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(txtNickname, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnConnect)
					.addContainerGap())
				.addComponent(pnlBottom, GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(pnlTop, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtAddress, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtNickname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnConnect))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlBottom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(165, Short.MAX_VALUE))
		);
		pnlBottom.setLayout(new BoxLayout(pnlBottom, BoxLayout.Y_AXIS));
		
		JSeparator separator1 = new JSeparator();
		pnlBottom.add(separator1);
		
		lblStatus = new JLabel("Ready!");
		lblStatus.setAlignmentX(0.03f);
		pnlBottom.add(lblStatus);
		frmServerSelect.getContentPane().setLayout(groupLayout);
	}
	
	public void show() {
		frmServerSelect.setVisible(true);
	}
	
	public void hide() {
		frmServerSelect.setVisible(false);
	}
	
	public void lockControls() {
		txtAddress.setEnabled(false);
		txtNickname.setEnabled(false);
		btnConnect.setEnabled(false);
	}
	
	public void unlockControls() {
		txtAddress.setEnabled(true);
		txtNickname.setEnabled(true);
		btnConnect.setEnabled(true);
	}
}
