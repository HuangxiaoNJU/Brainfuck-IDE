package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginFrame extends JFrame {
	
	public JFrame frame;
	private JTextField userIdField;
	private JPasswordField passwordField;

	public LoginFrame() {
		frame = new JFrame("Welcome!");
		frame.setLayout(null);
		
		// ��ʾ��ǩ
		JLabel userId = new JLabel("UserId:");
		JLabel password = new JLabel("Password:");
		userId.setBounds(30, 30, 80, 20);
		password.setBounds(20, 80, 80, 20);
		frame.add(userId);
		frame.add(password);
		
		// �û��������뵥���ı���
		userIdField = new JTextField();
		passwordField = new JPasswordField();
		userIdField.setBounds(110, 30, 120, 20);
		passwordField.setBounds(110, 80, 120, 20);
		frame.add(userIdField);
		frame.add(passwordField);

		// ��¼��ע�ᰴť
		JButton login = new JButton("log in");
		JButton signup = new JButton("sign up");
		login.setBounds(30, 130, 80, 20);
		signup.setBounds(140, 130, 80, 20);
		frame.add(login);
		frame.add(signup);
		// ��װ��ť������
		login.addActionListener(new loginListener());
		signup.addActionListener(new signupListener());
		
		// ������������
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(250, 200);
		frame.setLocation(520, 300);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	class loginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				if(RemoteHelper.getInstance().getUserService().login(username, password)) {
					frame.setVisible(false);
					// TODO ��¼�ɹ�
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class signupListener implements ActionListener {
		// ע��
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				if(RemoteHelper.getInstance().getUserService().signup(username, password)) {
					frame.setVisible(false);
					// TODO ע��ɹ�
				}
			} catch (RemoteException e1){
				e1.printStackTrace();
			}
		}
	}
}
