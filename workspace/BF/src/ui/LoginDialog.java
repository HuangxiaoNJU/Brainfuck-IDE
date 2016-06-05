package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginDialog extends JDialog {
	
	private JDialog loginDialog;
	private MainFrame mainFrame;
	private JTextField userIdField;
	private JPasswordField passwordField;
	private JLabel infoLabel;

	public LoginDialog(MainFrame mainFrame) {
		
		this.mainFrame = mainFrame;
		
		this.loginDialog = this;
		
		// 提示标签
		JLabel userId = new JLabel("UserId:");
		JLabel password = new JLabel("Password:");
		userId.setBounds(30, 30, 80, 20);
		password.setBounds(20, 80, 80, 20);
		loginDialog.add(userId);
		loginDialog.add(password);
		
		// 用户名、密码文本框
		userIdField = new JTextField();
		passwordField = new JPasswordField();
		userIdField.setBounds(100, 30, 130, 20);
		passwordField.setBounds(100, 80, 130, 20);
		loginDialog.add(userIdField);
		loginDialog.add(passwordField);

		// 登录、注册按钮
		JButton login = new JButton("log in");
		JButton signup = new JButton("sign up");
		login.setBounds(30, 140, 80, 20);
		signup.setBounds(140, 140, 80, 20);
		loginDialog.add(login);
		loginDialog.add(signup);
		// 安装按钮监听器
		login.addActionListener(new loginListener());
		signup.addActionListener(new signupListener());
		
		// 消息提示标签
		infoLabel = new JLabel();
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		infoLabel.setBounds(25, 110, 200, 20);
		infoLabel.setVisible(false);
		loginDialog.add(infoLabel);

		// 对话框属性设置
		loginDialog.setTitle("Welcome!");
		loginDialog.setLayout(null);
		loginDialog.setSize(250, 200);
		loginDialog.setLocation(520, 300);
		loginDialog.setAlwaysOnTop(true);
		loginDialog.setResizable(false);
		loginDialog.setVisible(true);
		loginDialog.setModal(true);
	}
	
	/**
	 * 登录按钮监听器
	 */
	class loginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				if(RemoteHelper.getInstance().getUserService().login(username, password)) {
					loginDialog.dispose();
					// TODO 登录成功
					mainFrame.fileName = null;
					mainFrame.username = username;
					mainFrame.codeArea.setText("");
					mainFrame.fileInfoLabel.setText("No file");
					mainFrame.logInfoLabel.setText("Hi! " + username);
					mainFrame.loginButton.setVisible(false);
					mainFrame.logoutButton.setVisible(true);
					mainFrame.newMenuItem.setEnabled(true);
				}
				// 错误消息提示
				else {
					infoLabel.setText("Login failed!");
					infoLabel.setVisible(true);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 注册按钮监听器
	 */
	class signupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				if(RemoteHelper.getInstance().getUserService().signup(username, password)) {
					infoLabel.setText("signup succeed!");
					infoLabel.setVisible(true);
				}
				// 错误消息提示
				else {
					infoLabel.setText("User name already exists!");
					infoLabel.setVisible(true);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
