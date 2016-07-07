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
	 * ������
	 */
	private MainFrame mainFrame;
	/**
	 * �ļ���Ϣ��ǩ
	 */
	public JLabel fileInfoLabel;
	/**
	 * �����ı���
	 */
	public JTextArea codeArea;
	/**
	 * �����ı���
	 */
	private JTextArea inputArea;
	/**
	 * ���н����ǩ
	 */
	private JTextArea outputArea;
	/**
	 * ��¼��ť
	 */
	public JButton loginButton;
	/**
	 * �ǳ���ť
	 */
	public JButton logoutButton;
	/**
	 * ��¼��Ϣ��ǩ
	 */
	public JLabel logInfoLabel;
	/**
	 * �½��˵���
	 */
	public JMenuItem newMenuItem;
	/**
	 * �򿪲˵���
	 */
	public JMenuItem openMenuItem;
	/**
	 * ����˵���
	 */
	public JMenuItem saveMenuItem;
	/**
	 * �˳��˵���
	 */
	public JMenuItem exitMenuItem;
	/**
	 * �汾�˵���
	 */
	public JMenu versionMenu;
	/**
	 * Ŀǰ�򿪵��ļ�����Ϊ�����ļ��ͷ��������ļ���
	 */
	public String fileName = null;
	/**
	 * �û�����δ��¼Ϊnull��
	 */
	public String username = null;

	public MainFrame() {
		this.mainFrame = this;
		// ��������
		mainFrame.setTitle("BF Client");
		mainFrame.setLayout(null);
		mainFrame.addWindowListener(new CloseListener());
		
		// ������
		JScrollPane scroller;
		
		// �˵�
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
		
		// ��װ�˵�������
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new ExitActionListener());
		compileMenuItem.addActionListener(new CompileActionListener());
		executeMenuItem.addActionListener(new ExecuteActionListener());
		
		// �ļ���Ϣ
		fileInfoLabel = new JLabel("No file");
		fileInfoLabel.setBounds(10, 20, 100, 20);
		mainFrame.add(fileInfoLabel);
		
		// ��¼��Ϣ
		logInfoLabel = new JLabel();
		logInfoLabel.setHorizontalAlignment(JLabel.RIGHT);
		logInfoLabel.setText("Please log in: ");
		logInfoLabel.setBounds(100, 20, 310, 20);
		mainFrame.add(logInfoLabel);
		
		// ��¼��ť
		loginButton = new JButton("Log in");
		loginButton.setBounds(420, 20, 80, 20);
		loginButton.setVisible(true);
		mainFrame.add(loginButton);
		// �ǳ���ť
		logoutButton = new JButton("Log out");
		logoutButton.setBounds(420, 20, 80, 20);
		logoutButton.setVisible(false);
		mainFrame.add(logoutButton);
		// ��װ��ť������
		loginButton.addActionListener(new LoginActionLisener());
		logoutButton.addActionListener(new LogoutActionLisener());

		// �����ı���
		codeArea = new JTextArea();
		codeArea.addKeyListener(new TextAreaListener(codeArea, ""));
		codeArea.setLineWrap(true);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		scroller = new JScrollPane(codeArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 40, 500, 260);
		mainFrame.add(scroller);
		
		// �����ı���
		inputArea = new JTextArea("Input Section");
		inputArea.setLineWrap(true);
		inputArea.setMargin(new Insets(15, 15, 15, 15));
		scroller = new JScrollPane(inputArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 300, 250, 100);
		mainFrame.add(scroller);
		
		// ����ı���
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

		// ������������
		mainFrame.setSize(500, 420);
		// ������ʾ
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) >> 1;
		int y = ((screen.height - this.getHeight()) >> 1) - 20;
		mainFrame.setLocation(x, y);
		
		// ������������
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 420);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setFocusable(false);
		
		// ��¼����
		new LoginDialog(mainFrame);
	}
	
	/**
	 * �½��ļ�������
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
	 * ���������
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
	 * ���м�����
	 */
	class ExecuteActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			String param = inputArea.getText();
			try {
				// ��ȡ������Ϣ
				String info = RemoteHelper.getInstance().getCompileService().compile(code, param);
				// ����ɹ�������
				if(info.equals("Success")) {
					outputArea.setForeground(Color.BLACK);
					outputArea.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
				}
				// ����ʧ����ʾ������Ϣ
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
	 * �򿪼�����
	 */
	class OpenActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// δ��¼����򿪱����ļ�
			if (username == null) {
				JFileChooser jfc = new JFileChooser();
				// �ļ�����
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
			// �ѵ�¼����򿪸��û��Ѵ������ļ�
			else {
				try {
					// ���û���δ�����ļ���������Ϣ��
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
	 * ���������
	 */
	class SaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			// δ��¼���򱣴汾���ļ�
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
			// �ѵ�¼���򱣴��ļ��Ͱ汾�ڷ�����
			SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			String versionName = dateFormat.format(new Date());
			try {
				if (RemoteHelper.getInstance().getVersionService().saveVersion(username, fileName, code, versionName)) {
					
					RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
					JMenuItem versionItem = new JMenuItem(versionName);
					versionMenu.add(versionItem);
					versionItem.addActionListener(new VersionListener());
					versionMenu.setEnabled(true);
					// ��ʾ��Ϣ
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
	 * �˳�������
	 */
	class ExitActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �������ݱ仯
			codeArea.setText("");
			saveMenuItem.setEnabled(false);
			fileName = null;
			fileInfoLabel.setText("No file");
			versionMenu.setEnabled(false);
			versionMenu.removeAll();
		}
	}
	
	/**
	 * �汾�˵��������
	 */
	class VersionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// �ָ��汾
			String versionName = e.getActionCommand();
			try {
				String code = RemoteHelper.getInstance().getVersionService().readVersion(username, fileName, versionName);
				codeArea.setText(code);
				codeArea.addKeyListener(new TextAreaListener(codeArea, code));
				RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
				versionMenu.remove((JMenuItem)e.getSource());
				if(versionMenu.getItemCount() == 0)
					versionMenu.setEnabled(false);
				// ��ʾ��Ϣ
				JOptionPane.showMessageDialog(null, "Return to version:\n" + versionName);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * ���밴ť������
	 */
	class LoginActionLisener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new LoginDialog(mainFrame);
		}
	}
	
	/**
	 * �ǳ���ť������
	 */
	class LogoutActionLisener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				RemoteHelper.getInstance().getUserService().logout(username);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			// �������ݱ仯
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
	 * �����ڹرռ�����
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
