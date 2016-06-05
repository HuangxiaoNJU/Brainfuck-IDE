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
	private JLabel resultLabel;
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
	 * ����˵���
	 */
	public JMenuItem saveMenuItem;
	/**
	 * Ŀǰ�򿪵��ļ�����Ϊ�����ļ��ͷ��������ļ���
	 */
	public String fileName = null;
	/**
	 * �û�����δ��¼Ϊnull��
	 */
	public String username = null;

	/**
	 * ���췽��
	 */
	public MainFrame() {
		this.mainFrame = this;
		// ��������
		mainFrame.setTitle("BF Client");
		mainFrame.setLayout(null);
		
		// ������
		JScrollPane scroller;
		
		// �˵�
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
		
		// ��װ�˵�������
		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		exitMenuItem.addActionListener(new ExecuteActionListener());
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
		codeArea.setLineWrap(true);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		scroller = new JScrollPane(codeArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 40, 500, 260);
		mainFrame.add(scroller);
		
		// �����ı���
		inputArea = new JTextArea("Input");
		inputArea.setLineWrap(true);
		inputArea.setMargin(new Insets(20, 20, 0, 0));
		scroller = new JScrollPane(inputArea);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroller.setBounds(0, 300, 250, 100);
		mainFrame.add(scroller);
		
		// ��ʾ���
		resultLabel = new JLabel();
		resultLabel.setHorizontalAlignment(JLabel.CENTER);
		resultLabel.setText("result");
		scroller = new JScrollPane(resultLabel);
		scroller.setBounds(250, 300, 250, 100);
		mainFrame.add(scroller);

		// ������������
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 420);
		mainFrame.setLocation(400, 200);
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
			new NewFileDialog(mainFrame);
		}
		
	}

	/**
	 * ���м�����
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
	 * �򿪼�����
	 */
	class OpenActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// δ��¼����򿪱����ļ�
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
			// �ѵ�¼����򿪸��û��Ѵ������ļ�
			else {
				try {
					if(RemoteHelper.getInstance().getIOService().readFileList(username) == null) {
						// TODO ��Ϣ��ʾ���û���δ�����ļ�
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
	 * ���������
	 */
	class SaveActionListener implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			// ��δ��¼
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
			// ���ѵ�¼
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
	 * �˳�������
	 */
	class exitActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO
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
