package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	private JDialog loginDialog;
	private MainFrame mainFrame;
	private JTextField userIdField;
	private JPasswordField passwordField;
	
	public LoginDialog(MainFrame mainFrame) {
		super(mainFrame, "Welcome", true);
		
		this.mainFrame = mainFrame;
		this.loginDialog = this;
		
		LoginListener loginListener = new LoginListener();
		
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
		userIdField.addKeyListener(loginListener);
		passwordField.addKeyListener(loginListener);
		userIdField.setBounds(100, 30, 130, 20);
//		userIdField.requestFocus();
		passwordField.setBounds(100, 80, 130, 20);
		loginDialog.add(userIdField);
		loginDialog.add(passwordField);

		// 登录、注册按钮
		JButton login = new JButton("log in");
		JButton signup = new JButton("sign up");
		login.setBounds(30, 130, 80, 20);
		signup.setBounds(140, 130, 80, 20);
		loginDialog.add(login);
		loginDialog.add(signup);
		// 安装按钮监听器
		login.addActionListener(loginListener);
		signup.addActionListener(new SignupListener());
		
		// 对话框属性设置
		loginDialog.setSize(250, 200);
		// 居中显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) >> 1;
		int y = ((screen.height - this.getHeight()) >> 1) - 20;
		loginDialog.setLocation(x, y);
		
		loginDialog.setLayout(null);
		loginDialog.setResizable(false);
		loginDialog.setVisible(true);
	}
	
	/**
	 * 登录按钮监听器
	 */
	class LoginListener extends KeyAdapter implements ActionListener {
		
		private void login() {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				String info = RemoteHelper.getInstance().getUserService().login(username, password);
				// 登录成功
				if(info.equals("success")) {
					loginDialog.dispose();
					mainFrame.fileName = null;
					mainFrame.username = username;
					mainFrame.codeArea.setText("");
					mainFrame.fileInfoLabel.setText("No file");
					mainFrame.logInfoLabel.setText("Hi! " + username);
					mainFrame.loginButton.setVisible(false);
					mainFrame.logoutButton.setVisible(true);
					mainFrame.newMenuItem.setEnabled(true);
					
					JOptionPane.showMessageDialog(null, "Log in succeed!");
				}
				// 登录失败，提示错误信息
				else {
					userIdField.setText("");
					passwordField.setText("");
					JOptionPane.showMessageDialog(null, info, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			login();
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				login();
		}
	}
	
	/**
	 * 注册按钮监听器
	 */
	class SignupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				String info = RemoteHelper.getInstance().getUserService().signup(username, password);
				// 注册成功
				if(info.equals("success")) {
					JOptionPane.showMessageDialog(null, "Sign up succeed!");
				}
				// 注册失败，提示错误信息
				else {
					userIdField.setText("");
					passwordField.setText("");
					JOptionPane.showMessageDialog(null, info, "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
}
