package ui;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
	 * ����˵���
	 */
	public JMenuItem saveMenuItem;
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
		codeArea.addKeyListener(new UndoRedoListener());
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
		
		// ����ı���
		outputArea = new JTextArea("Output");
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputArea.setMargin(new Insets(20, 20, 0, 0));
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
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(500, 420);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setFocusable(false);
		
		// ��¼����
		new LoginDialog(mainFrame);
	}
	
	/**
	 * ��հ汾��Ϣ
	 */
	private void clearVersion() {
		versionMenu.setEnabled(false);
		// ��հ汾�˵���
		versionMenu.removeAll();
		// ɾ���������汾�ļ�
		try {
			RemoteHelper.getInstance().getVersionService().clear();
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * �½��ļ�������
	 */
	class NewActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String newFile = JOptionPane.showInputDialog("Please name your new file:");
			if(newFile != null) {
				try {
					if(RemoteHelper.getInstance().getIOService().createFile(mainFrame.username, newFile)) {
						fileInfoLabel.setText(newFile);
						fileName = newFile;
						codeArea.setText("");
						saveMenuItem.setEnabled(true);
						JOptionPane.showMessageDialog(null, "New succeed!");
						clearVersion();
					}
					else {
						JOptionPane.showMessageDialog(null, newFile + " already exists!", "Error", JOptionPane.WARNING_MESSAGE);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
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
				outputArea.setText(RemoteHelper.getInstance().getExecuteService().execute(code, param));
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
				// ѡ�����ʹ��� TODO
				jfc.showDialog(new JLabel(), "Choose");
				jfc.setMultiSelectionEnabled(false);
				File file = jfc.getSelectedFile();
				if(file != null && file.getName().endsWith(".rtf")) {
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
						new FileListDialog(mainFrame);
					}
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}
			// ����汾��Ϣ
			clearVersion();
		}
	}

	/**
	 * ���������
	 */
	class SaveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			try {
				if (RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName)) {
					// ����汾
					SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
					String versionName = dateFormat.format(new Date());
					
					RemoteHelper.getInstance().getVersionService().saveVersion(code, versionName);
				    
					JMenuItem versionItem = new JMenuItem(versionName);
					versionMenu.add(versionItem);
					versionItem.addActionListener(new VersionListener());
					versionMenu.setEnabled(true);
					// ��ʾ��Ϣ
					JOptionPane.showMessageDialog(null, "Save succeed!\n" + versionName);
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
			codeArea.setText("");
			fileName = null;
			fileInfoLabel.setText("No file");
			// ����汾��Ϣ
			clearVersion();
		}
	}
	
	/**
	 * �汾�˵��������
	 */
	class VersionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String versionName = e.getActionCommand();
			try {
				String code = RemoteHelper.getInstance().getVersionService().readVersion(versionName);
				codeArea.setText(code);
				RemoteHelper.getInstance().getIOService().writeFile(code, username, fileName);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			versionMenu.remove((JMenuItem)e.getSource());
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
			// ����汾��Ϣ
			clearVersion();
		}
	}
	
	/**
	 * ����(Ctrl+Z)����(Ctrl+Y)���̼�����
	 */
	class UndoRedoListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
				System.out.println("Undo");
			}
			else if(e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()) {
				System.out.println("Redo");
			}
		}
		public void keyTyped(KeyEvent e) {}
		public void keyReleased(KeyEvent e) {}
	}
}
