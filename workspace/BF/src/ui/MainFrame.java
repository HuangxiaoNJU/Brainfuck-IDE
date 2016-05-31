package ui;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

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

	public MainFrame() {
		// ��������
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);
		// ������
		JScrollPane pane;
		
		// �˵�
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		JMenuItem runMenuItem = new JMenuItem("Run");
		fileMenu.add(runMenuItem);
		menuBar.setBounds(0, 0, 500, 20);
		frame.add(menuBar);
		
		// ��װ�˵�������
		newMenuItem.addActionListener(new MenuItemActionListener());
		openMenuItem.addActionListener(new MenuItemActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		runMenuItem.addActionListener(new MenuItemActionListener());

		// �����ı���
		codeArea = new JTextArea();
		codeArea.setMargin(new Insets(10, 10, 10, 10));
//		textArea.setBackground(Color.LIGHT_GRAY);
		pane = new JScrollPane(codeArea);
		pane.setBounds(0, 20, 500, 280);
		frame.add(pane);
		
		// �����ı���
		inputArea = new JTextArea("Input");
		inputArea.setMargin(new Insets(10, 10, 10, 10));
		pane = new JScrollPane(inputArea);
		pane.setBounds(0, 300, 250, 100);
		frame.add(pane);
		
		// ��ʾ���
		resultLabel = new JLabel();
		resultLabel.setText("result");
		pane = new JScrollPane(resultLabel);
		pane.setBounds(250, 300, 250, 100);
		frame.add(pane);

		// ������������
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
			} else if (cmd.equals("Run")) {
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
}
