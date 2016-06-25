package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JDialog loginDialog;
	private MainFrame mainFrame;
	private JTextField userIdField;
	private JPasswordField passwordField;
	
	public LoginDialog(MainFrame mainFrame) {
		super(mainFrame, "Welcome", true);
		
		this.mainFrame = mainFrame;
		this.loginDialog = this;
		
		// ��ʾ��ǩ
		JLabel userId = new JLabel("UserId:");
		JLabel password = new JLabel("Password:");
		userId.setBounds(30, 30, 80, 20);
		password.setBounds(20, 80, 80, 20);
		loginDialog.add(userId);
		loginDialog.add(password);
		
		// �û����������ı���
		userIdField = new JTextField();
		passwordField = new JPasswordField();
		userIdField.setBounds(100, 30, 130, 20);
//		userIdField.requestFocus();
		passwordField.setBounds(100, 80, 130, 20);
		loginDialog.add(userIdField);
		loginDialog.add(passwordField);

		// ��¼��ע�ᰴť
		JButton login = new JButton("log in");
		JButton signup = new JButton("sign up");
		login.setBounds(30, 130, 80, 20);
		signup.setBounds(140, 130, 80, 20);
		loginDialog.add(login);
		loginDialog.add(signup);
		// ��װ��ť������
		login.addActionListener(new loginListener());
		signup.addActionListener(new signupListener());
		
		// �Ի�����������
		loginDialog.setSize(250, 200);
		// ������ʾ
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
	 * ��¼��ť������
	 */
	class loginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				String info = RemoteHelper.getInstance().getUserService().login(username, password);
				// ��¼�ɹ�
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
				// ��¼ʧ�ܣ���ʾ������Ϣ
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
	
	/**
	 * ע�ᰴť������
	 */
	class signupListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = userIdField.getText();
			String password = new String(passwordField.getPassword());
			try {
				String info = RemoteHelper.getInstance().getUserService().signup(username, password);
				// ע��ɹ�
				if(info.equals("success")) {
					JOptionPane.showMessageDialog(null, "Sign up succeed!");
				}
				// ע��ʧ�ܣ���ʾ������Ϣ
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
