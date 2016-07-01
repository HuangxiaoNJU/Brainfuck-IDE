package undo_redo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.JTextArea;

public class TextAreaListener extends KeyAdapter {
	
	private JTextArea textArea;
	
	/**
	 * ��������ջ
	 */
	private Stack<String> undo;
	private Stack<String> redo;
	
	/**
	 * ��һ�β�����¼
	 */
	private String lastCommand;
	private boolean isUndoOrRedo;
	
	/**
	 * ÿ�β�����textArea�е�����
	 */
	private String nowString;
	
	public TextAreaListener(JTextArea codeArea, String content) {
		textArea = codeArea;
		undo = new Stack<String>();
		redo = new Stack<String>();
		lastCommand = "";
		isUndoOrRedo = false;
		nowString = content;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(!e.isControlDown()) {
			isUndoOrRedo = false;
			return;
		}
		// Ctrl + Z ����
		if(keyCode == KeyEvent.VK_Z) {
			if(!undo.empty()) {
				String content = undo.pop();
				textArea.setText(content);
				redo.push(nowString);
				lastCommand = "Undo";
				isUndoOrRedo = true;
				nowString = content;
			}
		}
		// Ctrl + Y ����
		else if(keyCode == KeyEvent.VK_Y) {
			// ֻ������һ�β���Ϊ����������ʱ���ܽ�������
			if(!redo.empty() && (lastCommand.equals("Undo") || lastCommand.equals("Redo"))) {
				String content = redo.pop();
				textArea.setText(content);
				lastCommand = "Redo";
				isUndoOrRedo = true;
				nowString = content;
			}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(isUndoOrRedo || textArea.getText().equals(nowString))
			return;
		// ������г��������������������������������ճ���ջ������ջ
		if(lastCommand.equals("Undo") || lastCommand.equals("Redo")) {
			while(!undo.empty())
				undo.pop();
			while(!redo.empty())
				redo.pop();
		}
		// ����ǰ�ı�����ѹ�볷��ջ
		if(undo.empty() || (!undo.empty() && !undo.peek().equals(nowString))) {
			undo.push(nowString);
		}
		// nowString��¼�������ı�����
		nowString = textArea.getText();
		
		// �ظ������ϲ�
		switch(e.getKeyCode()) {
		case KeyEvent.VK_TAB:
			if(lastCommand.equals("Tab")) undo.pop();
			else lastCommand = "Tab";
			break;
		case KeyEvent.VK_ENTER:
			if(lastCommand.equals("Enter")) undo.pop();
			else lastCommand = "Enter";
			break;
		case KeyEvent.VK_SPACE:
			if(lastCommand.equals("Space")) undo.pop();
			else lastCommand = "Space";
			break;
		case KeyEvent.VK_DELETE:
			if(lastCommand.equals("Delete")) undo.pop();
			else lastCommand = "Delete";
			break;
		case KeyEvent.VK_BACK_SPACE:
			if(lastCommand.equals("Backspace")) undo.pop();
			else lastCommand = "Backspace";
			break;
		default:
			if(lastCommand.equals("Other:" + e.getKeyChar())) undo.pop();
			else lastCommand = "Other:" + e.getKeyChar();
			break;
		}
	}

}
