package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import rmi.RemoteHelper;
import undo_redo.TextAreaListener;

public class FileListDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	
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
		// 居中显示
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		int x = (screen.width - this.getWidth()) >> 1;
		int y = ((screen.height - this.getHeight()) >> 1) - 20;
		fileListDialog.setLocation(x, y);
		
		fileListDialog.setResizable(false);
		fileListDialog.setVisible(true);
	}
	
	class selectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileName = fileList.getSelectedValue();
			if(fileName.equals(mainFrame.fileName)) {
				JOptionPane.showMessageDialog(null, "You have already opened " + fileName, "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			mainFrame.fileName = fileName;
			mainFrame.fileInfoLabel.setText(fileName);
			mainFrame.saveMenuItem.setEnabled(true);
			try {
				String content = RemoteHelper.getInstance().getIOService().readFile(mainFrame.username, fileName);
				mainFrame.codeArea.setText(content);
				mainFrame.codeArea.addKeyListener(new TextAreaListener(mainFrame.codeArea, content));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			mainFrame.clearVersion();
			fileListDialog.dispose();
		}
		
	}
	
}
