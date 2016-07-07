package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;

import rmi.RemoteHelper;
import undo_redo.TextAreaListener;


public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
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
	private JTextArea outputArea;
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
	 * 打开菜单项
	 */
	public JMenuItem openMenuItem;
	/**
	 * 保存菜单项
	 */
	public JMenuItem saveMenuItem;
	/**
	 * 退出菜单项
	 */
	public JMenuItem exitMenuItem;
	/**
	 * 版本菜单项
	 */
	public JMenu versionMenu;
	/**
	 * 目前打开的文件（分为本地文件和服务器端文件）
	 */
	public String fileName = null;
	/**
	 * 用户名（未登录为null）
	 */
	public String username = null;

	public MainFrame() {
		this.mainFrame = this;
		// 创建窗体
		mainFrame.setTitle("BF Client");
		mainFrame.setLayout(null);
		mainFrame.addWindowListener(new CloseListener());
		
		// 滚动条
		JScrollPane scroller;
		
		// 菜单
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		newMenuItem = new JMenuItem("New");
		newMenuItem.setEnabled(false);
		fileMenu.add(newMenuItem);
		openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.setEnabled(false);
		fileMenu.add(saveMenuItem);
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setEnabled(false);
		fileMenu.add(exitMenuItem);
		
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		JMenuItem compileMenuItem = new JMenuItem("Compile");
		runMenu.add(compileMenuItem);
		JMenuItem executeMenuItem = new JMenuItem("Execute");
		runMenu.add(executeMenuItem);
		
		versionMenu = new JMenu("Version");
		versionMenu.setEnabled(false);
		menuBar.add(versionMenu);
		
		menuBar.setBounds(0, 0, 500, 20);
		mainFrame.add(menuBar);
		
		// 安装菜单监听器
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new ExitActionListener());
		compileMenuItem.addActionListener(new CompileActionListener());
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
		codeArea.addKeyListener(new TextAreaListener(codeArea, ""));
		codeArea.setLineWrap(true);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		scroller = new JScrollPane(codeArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 40, 500, 260);
		mainFrame.add(scroller);
		
		// 输入文本区
		inputArea = new JTextArea("Input Section");
		inputArea.setLineWrap(true);
		inputArea.setMargin(new Insets(15, 15, 15, 15));
		scroller = new JScrollPane(inputArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 300, 250, 100);
		mainFrame.add(scroller);
		
		// 输出文本区
		outputArea = new JTextArea("Output Section");
		outputArea.setEditable(false);
		outputArea.setWrapStyleWord(true);
		outputArea.setLineWrap(true);
		outputArea.setMargin(new Insets(15, 15, 15, 15));
		scroller = new JScrollPane(outputArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(250, 300, 250, 100);
		mainFrame.add(scroller);

		// 窗体属性设置
		mainFrame.setSize(500, 420);
		// 居中显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) >> 1;
		int y = ((screen.height - this.getHeight()) >> 1) - 20;
		mainFrame.setLocation(x, y);
		
		// 窗体属性设置
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 420);
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
			String newFile = JOptionPane.showInputDialog("Please name your new file:");
			if(!newFile.equals("")) {
				try {
					if(RemoteHelper.getInstance().getIOService().createFile(mainFrame.username, newFile)) {
						fileInfoLabel.setText(newFile);
						fileName = newFile;
						codeArea.setText("");
						codeArea.addKeyListener(new TextAreaListener(codeArea, ""));
						saveMenuItem.setEnabled(true);
						exitMenuItem.setEnabled(true);
						versionMenu.setEnabled(false);
						JOptionPane.showMessageDialog(null, "New succeed!");
					}
					else {
						JOptionPane.showMessageDialog(null, newFile + " already exists!", "Error", JOptionPane.WARNING_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "File name cannot be null!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 编译监听器
	 */
	class CompileActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			String param = inputArea.getText();
			try {
				String info = RemoteHelper.getInstance().getCompileService().compile(code, param);
				outputArea.setText(info);
				if(!info.equals("Success"))
					outputArea.setForeground(Color.RED);
				else
					outputArea.setForeground(Color.BLACK);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * 运行监听器
	 */
	class ExecuteActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			String param = inputArea.getText();
			try {
				// 获取编译信息
				String info = RemoteHelper.getInstance().getCompileService().compile(code, param);
				// 编译成功则运行
				if(info.equals("Success")) {
					outputArea.setForeground(Color.BLACK);
					outputArea.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
				}
				// 编译失败提示错误信息
				else {
					outputArea.setForeground(Color.RED);
					outputArea.setText(info);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
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
				// 文件过滤
				jfc.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if(f.getName().endsWith(".rtf") || f.getName().endsWith(".txt") || f.isDirectory())
							return true;
						return false;
					}
					@Override
					public String getDescription() {
						return ".rtf .txt";
					}
				});
				jfc.showDialog(new JLabel(), "Choose");
				jfc.setMultiSelectionEnabled(false);
				File file = jfc.getSelectedFile();
				if(file != null && (file.getName().endsWith(".rtf") || file.getName().endsWith(".txt"))) {
					saveMenuItem.setEnabled(true);
					fileName = file.getAbsolutePath();
					fileInfoLabel.setText(file.getName());
					codeArea.setText("");
					try {
						BufferedReader bf = new BufferedReader(new FileReader(file));
						String str;
						while ((str = bf.readLine()) != null) {
							codeArea.append(str + '\n');
						}
						bf.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					codeArea.addKeyListener(new TextAreaListener(codeArea, codeArea.getText()));
				}
			}
			// 已登录，则打开该用户已创建的文件
			else {
				try {
					// 若用户还未创建文件，弹出消息框
					if(RemoteHelper.getInstance().getIOService().readFileList(username) == null) {
						JOptionPane.showMessageDialog(null, "You haven't created any file!", "Warning", JOptionPane.WARNING_MESSAGE);
						return;
					}
					else {
						new FileListDialog(mainFrame, new VersionListener());
					}
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
			// 未登录，则保存本地文件
			if(username == null) {
				try {
					if(RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName)) {
						JOptionPane.showMessageDialog(null, "Save succeed!");
					}
					else
						JOptionPane.showMessageDialog(null, "Save failed!", "Error", JOptionPane.WARNING_MESSAGE);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				return;
			}
			// 已登录，则保存文件和版本在服务器
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String versionName = dateFormat.format(new Date());
			try {
				if (RemoteHelper.getInstance().getVersionService().saveVersion(username, fileName, code, versionName)) {
					
					RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
					JMenuItem versionItem = new JMenuItem(versionName);
					versionMenu.add(versionItem);
					versionItem.addActionListener(new VersionListener());
					versionMenu.setEnabled(true);
					// 提示信息
					JOptionPane.showMessageDialog(null, "Save succeed!\n" + versionName);
				}
				else {
					JOptionPane.showMessageDialog(null, "You didn't make any change!", "Error", JOptionPane.WARNING_MESSAGE);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 退出监听器
	 */
	class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 界面内容变化
			codeArea.setText("");
			saveMenuItem.setEnabled(false);
			fileName = null;
			fileInfoLabel.setText("No file");
			versionMenu.setEnabled(false);
			versionMenu.removeAll();
		}
	}
	
	/**
	 * 版本菜单项监听器
	 */
	class VersionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 恢复版本
			String versionName = e.getActionCommand();
			try {
				String code = RemoteHelper.getInstance().getVersionService().readVersion(username, fileName, versionName);
				codeArea.setText(code);
				codeArea.addKeyListener(new TextAreaListener(codeArea, code));
				RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
				versionMenu.remove((JMenuItem)e.getSource());
				if(versionMenu.getItemCount() == 0)
					versionMenu.setEnabled(false);
				// 提示信息
				JOptionPane.showMessageDialog(null, "Return to version:\n" + versionName);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
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
			try {
				RemoteHelper.getInstance().getUserService().logout(username);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// 界面内容变化
			username = null;
			fileName = null;
			codeArea.setText("");
			codeArea.addKeyListener(new TextAreaListener(codeArea, ""));
			inputArea.setText("Input Section");
			outputArea.setText("Output Section");
			outputArea.setForeground(Color.BLACK);
			fileInfoLabel.setText("No file");
			logInfoLabel.setText("Please log in:");
			logoutButton.setVisible(false);
			loginButton.setVisible(true);
			newMenuItem.setEnabled(false);
			saveMenuItem.setEnabled(false);
			exitMenuItem.setEnabled(false);
			versionMenu.setEnabled(false);
			versionMenu.removeAll();
			new LoginDialog(mainFrame);
		}
	}
	
	/**
	 * 主窗口关闭监听器
	 */
	class CloseListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent arg0) {
			if(username != null) {
				try {
					RemoteHelper.getInstance().getUserService().logout(username);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
}
