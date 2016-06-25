package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class NewFileDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	private JDialog newFileDialog;
	private JTextField inputField;
	private JLabel errorInfoLabel;

	public NewFileDialog(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		this.newFileDialog = this;
		
		// ��ʾ��Ϣ
		JLabel promptLabel = new JLabel("Please name the file:");
		promptLabel.setHorizontalAlignment(JLabel.CENTER);
		promptLabel.setBounds(20, 20, 200, 30);
		promptLabel.setVisible(true);
		newFileDialog.add(promptLabel);
		
		// �����ı���
		inputField = new JTextField();
		inputField.setBounds(50, 50, 150, 30);
		inputField.setVisible(true);
		newFileDialog.add(inputField);
		
		// ������ʾ��Ϣ
		errorInfoLabel = new JLabel("File already exists!");
		errorInfoLabel.setHorizontalAlignment(JLabel.CENTER);
		errorInfoLabel.setBounds(0, 85, 250, 20);
		errorInfoLabel.setVisible(false);
		newFileDialog.add(errorInfoLabel);
		
		// ȷ�ϰ�ť
		JButton confirmButton = new JButton("confirm");
		confirmButton.setBounds(70, 110, 100, 30);
		confirmButton.setVisible(true);
		confirmButton.addActionListener(new confirmActionListener());
		newFileDialog.add(confirmButton);

		// �Ի�����������
		newFileDialog.setTitle("New");
		newFileDialog.setLayout(null);
		newFileDialog.setSize(250, 180);
		newFileDialog.setLocation(520, 300);
		newFileDialog.setResizable(false);
		newFileDialog.setAlwaysOnTop(true);
		newFileDialog.setModal(true);
		newFileDialog.setVisible(true);
	}
	
	/**
	 * ȷ�ϰ�ť������
	 */
	class confirmActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String fileName = inputField.getText();
			try {
				// �½��ļ��ɹ�
				if(RemoteHelper.getInstance().getIOService().createFile(mainFrame.username, fileName)) {
					mainFrame.fileInfoLabel.setText(fileName);
					mainFrame.fileName = fileName;
					mainFrame.codeArea.setText("");
					mainFrame.saveMenuItem.setEnabled(true);
					newFileDialog.dispose();
				}
				else {
					errorInfoLabel.setVisible(true);
				}
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
}
