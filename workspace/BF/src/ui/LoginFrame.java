package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {

	public LoginFrame() {
		JFrame frame = new JFrame("Welcome!");
		frame.setLayout(null);
		
		// 提示标签
		JLabel userId = new JLabel("UserId:");
		JLabel password = new JLabel("Password:");
		userId.setBounds(30, 30, 80, 20);
		password.setBounds(20, 80, 80, 20);
		frame.add(userId);
		frame.add(password);
		
		// 用户名、密码单行文本框
		JTextField userIdField = new JTextField();
		JPasswordField passwordField = new JPasswordField();
		userIdField.setBounds(110, 30, 120, 20);
		passwordField.setBounds(110, 80, 120, 20);
		frame.add(userIdField);
		frame.add(passwordField);

		// 登录、注册按钮
		JButton login = new JButton("log in");
		JButton signin = new JButton("sign in");
		login.setBounds(30, 130, 80, 20);
		signin.setBounds(140, 130, 80, 20);
		frame.add(login);
		frame.add(signin);
		// 安装按钮监听器
		login.addActionListener(new loginListener());
		signin.addActionListener(new signinListener());
		
		// 窗体属性设置
		frame.setSize(250, 200);
		frame.setLocation(520, 300);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	class loginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
	class signinListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 注册
		}
	}
}
