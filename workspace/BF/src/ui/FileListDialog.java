package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import rmi.RemoteHelper;

public class FileListDialog extends JDialog {

	private MainFrame mainFrame;
	private JDialog fileListDialog;
	private JList<String> fileList;
	
	public FileListDialog(MainFrame mainFrame) {
		super(mainFrame, "Open", true);
		
		this.mainFrame = mainFrame;
		this.fileListDialog = this;
		
		// 提示标签
		JLabel promptLabel = new JLabel(mainFrame.username + "'s all files:");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setBounds(0, 5, 160, 30);
		fileListDialog.add(promptLabel);
		
		// 文件列表
		String[] fileNames = null;
		try {
			fileNames = RemoteHelper.getInstance().getIOService().readFileList(mainFrame.username);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		fileList = new JList<String>(fileNames);
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileList.setSelectedIndex(0);
		JScrollPane scroller = new JScrollPane(fileList);
		scroller.setBounds(30, 40, 100, 140);
		fileListDialog.add(scroller);
		
		// 选择按钮
		JButton selectButton = new JButton("Select");
		selectButton.setBounds(140, 180, 100, 30);
		selectButton.addActionListener(new selectListener());
		selectButton.setVisible(true);
		fileListDialog.add(selectButton);
		
		// 对话框属性设置
		fileListDialog.setLayout(null);
		fileListDialog.setSize(250, 250);
		fileListDialog.setLocation(520, 300);
		fileListDialog.setResizable(false);
		fileListDialog.setAlwaysOnTop(true);
		fileListDialog.setVisible(true);
	}
	
	class selectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileName = fileList.getSelectedValue();
			mainFrame.fileName = fileName;
			mainFrame.fileInfoLabel.setText(fileName);
			mainFrame.saveMenuItem.setEnabled(true);
			try {
				mainFrame.codeArea.setText(RemoteHelper.getInstance().getIOService().readFile(mainFrame.username, fileName));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			fileListDialog.dispose();
		}
		
	}
	
}
