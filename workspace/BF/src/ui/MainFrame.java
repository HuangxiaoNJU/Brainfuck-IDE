package ui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	
	/**
	 * 主窗口
	 */
	private MainFrame mainFrame;
	/**
	 * 文件信息标签
	 */
	public JLabel fileInfoLabel;
	/**
	 * 代码文本框
	 */
	public JTextArea codeArea;
	/**
	 * 输入文本框
	 */
	private JTextArea inputArea;
	/**
	 * 运行结果标签
	 */
	private JLabel resultLabel;
	/**
	 * 登录按钮
	 */
	public JButton loginButton;
	/**
	 * 登出按钮
	 */
	public JButton logoutButton;
	/**
	 * 登录信息标签
	 */
	public JLabel logInfoLabel;
	/**
	 * 新建菜单项
	 */
	public JMenuItem newMenuItem;
	/**
	 * 保存菜单项
	 */
	public JMenuItem saveMenuItem;
	/**
	 * 目前打开的文件（分为本地文件和服务器端文件）
	 */
	public String fileName = null;
	/**
	 * 用户名（未登录为null）
	 */
	public String username = null;

	/**
	 * 构造方法
	 */
	public MainFrame() {
		this.mainFrame = this;
		// 创建窗体
		mainFrame.setTitle("BF Client");
		mainFrame.setLayout(null);
		
		// 滚动条
		JScrollPane scroller;
		
		// 菜单
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		newMenuItem = new JMenuItem("New");
		newMenuItem.setEnabled(false);
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setEnabled(false);
		fileMenu.add(saveMenuItem);
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		fileMenu.add(exitMenuItem);
		
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		
		JMenu versionMenu = new JMenu("Version");
		menuBar.add(versionMenu);
		
		menuBar.setBounds(0, 0, 500, 20);
		mainFrame.add(menuBar);
		
		// 安装菜单监听器
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new ExecuteActionListener());
		executeMenuItem.addActionListener(new ExecuteActionListener());
		
		// 文件信息
		fileInfoLabel = new JLabel("No file");
		fileInfoLabel.setBounds(10, 20, 100, 20);
		mainFrame.add(fileInfoLabel);
		
		// 登录信息
		logInfoLabel = new JLabel();
		logInfoLabel.setHorizontalAlignment(JLabel.RIGHT);
		logInfoLabel.setText("Please log in: ");
		logInfoLabel.setBounds(100, 20, 310, 20);
		mainFrame.add(logInfoLabel);
		
		// 登录按钮
		loginButton = new JButton("Log in");
		loginButton.setBounds(420, 20, 80, 20);
		loginButton.setVisible(true);
		mainFrame.add(loginButton);
		// 登出按钮
		logoutButton = new JButton("Log out");
		logoutButton.setBounds(420, 20, 80, 20);
		logoutButton.setVisible(false);
		mainFrame.add(logoutButton);
		// 安装按钮监听器
		loginButton.addActionListener(new LoginActionLisener());
		logoutButton.addActionListener(new LogoutActionLisener());

		// 代码文本区
		codeArea = new JTextArea();
		codeArea.setLineWrap(true);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		scroller = new JScrollPane(codeArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 40, 500, 260);
		mainFrame.add(scroller);
		
		// 输入文本区
		inputArea = new JTextArea("Input");
		inputArea.setLineWrap(true);
		inputArea.setMargin(new Insets(20, 20, 0, 0));
		scroller = new JScrollPane(inputArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 300, 250, 100);
		mainFrame.add(scroller);
		
		// 显示结果
		resultLabel = new JLabel();
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultLabel.setText("result");
		scroller = new JScrollPane(resultLabel);
		scroller.setBounds(250, 300, 250, 100);
		mainFrame.add(scroller);

		// 窗体属性设置
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 420);
		mainFrame.setLocation(400, 200);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setFocusable(false);
		
		// 登录界面
		new LoginDialog(mainFrame);
	}
	
	/**
	 * 新建文件监听器
	 */
	class NewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new NewFileDialog(mainFrame);
		}
		
	}

	/**
	 * 运行监听器
	 */
	class ExecuteActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if (cmd.equals("Execute")) {
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
	
	/**
	 * 打开监听器
	 */
	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 未登录，则打开本地文件
			if (username == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.showDialog(new JLabel(), "Choose");
				jfc.setMultiSelectionEnabled(false);
				File file = jfc.getSelectedFile();
				saveMenuItem.setEnabled(true);
				fileName = file.getAbsolutePath();
				fileInfoLabel.setText(file.getName());
				codeArea.setText("");
				try {
					BufferedReader bf = new BufferedReader(new FileReader(file));
					String str = bf.readLine();
					while (str != null) {
						codeArea.append(str);
						str = bf.readLine();
					}
					bf.close();
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// 已登录，则打开该用户已创建的文件
			else {
				try {
					if(RemoteHelper.getInstance().getIOService().readFileList(username) == null) {
						// TODO 消息提示：用户还未创建文件
					}
					else
						new FileListDialog(mainFrame);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 保存监听器
	 */
	class SaveActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			// 若未登录
			if(username == null) {
				try {
					FileWriter fw = new FileWriter(fileName);
					fw.write(code);
					fw.flush();
					fw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			// 若已登录
			else {
				try {
					RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 退出监听器
	 */
	class exitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO
		}
		
	}
	
	/**
	 * 登入按钮监听器
	 */
	class LoginActionLisener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new LoginDialog(mainFrame);
		}
	}
	
	/**
	 * 登出按钮监听器
	 */
	class LogoutActionLisener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			username = null;
			fileName = null;
			codeArea.setText("");
			fileInfoLabel.setText("No file");
			logInfoLabel.setText("Please log in:");
			logoutButton.setVisible(false);
			loginButton.setVisible(true);
			newMenuItem.setEnabled(false);
			saveMenuItem.setEnabled(false);
		}
	}
}
