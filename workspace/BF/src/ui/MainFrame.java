package ui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	private JTextArea codeArea;
	private JTextArea inputArea;
	private JLabel resultLabel;
	private JButton loginButton;
	private JButton logoutButton;

	public MainFrame() {
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);
		// 滚动条
		JScrollPane pane;
		
		// 菜单
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		
		menuBar.setBounds(0, 0, 420, 20);
		frame.add(menuBar);
		
		// 安装菜单监听器
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new MenuItemActionListener());
		executeMenuItem.addActionListener(new MenuItemActionListener());
		
		// 登录按钮
		loginButton = new JButton("Log in");
		loginButton.setBounds(420, 0, 80, 20);
		frame.add(loginButton);
		// 登出按钮
		logoutButton = new JButton("Log out");
		logoutButton.setBounds(420, 0, 80, 20);
		frame.add(logoutButton);
		loginButton.setVisible(true);
		logoutButton.setVisible(false);
		
		loginButton.addActionListener(new LoginActionLisener());
		logoutButton.addActionListener(new LogoutActionLisener());

		// 代码文本区
		codeArea = new JTextArea();
		codeArea.setMargin(new Insets(10, 10, 10, 10));
//		textArea.setBackground(Color.LIGHT_GRAY);
		pane = new JScrollPane(codeArea);
		pane.setBounds(0, 20, 500, 280);
		frame.add(pane);
		
		// 输入文本区
		inputArea = new JTextArea("Input");
		inputArea.setMargin(new Insets(30, 20, 0, 0));
		pane = new JScrollPane(inputArea);
		pane.setBounds(0, 300, 250, 100);
		frame.add(pane);
		
		// 显示结果
		resultLabel = new JLabel();
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultLabel.setText("result");
		pane = new JScrollPane(resultLabel);
		pane.setBounds(250, 300, 250, 100);
		frame.add(pane);

		// 窗体属性设置
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 420);
		frame.setLocation(400, 200);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	class MenuItemActionListener implements ActionListener {
		/**
		 * 
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Open")) {
				codeArea.setText("Open");
			} else if (cmd.equals("Save")) {
				codeArea.setText("Save");
			} else if (cmd.equals("Execute")) {
				String code = codeArea.getText();
				String param = inputArea.getText();
				try {
					resultLabel.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	class SaveActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			try {
				RemoteHelper.getInstance().getIOService().writeFile(code, "admin", "code");
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class LoginActionLisener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			logoutButton.setVisible(true);
			loginButton.setVisible(false);
			new LoginFrame();
		}
	}
	
	class LogoutActionLisener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			loginButton.setVisible(true);
			logoutButton.setVisible(false);
		}
	}
}
