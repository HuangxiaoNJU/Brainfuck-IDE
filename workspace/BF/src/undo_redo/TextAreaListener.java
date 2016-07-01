package undo_redo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Stack;

import javax.swing.JTextArea;

public class TextAreaListener extends KeyAdapter {
	
	private JTextArea textArea;
	
	/**
	 * 撤销重做栈
	 */
	private Stack<String> undo;
	private Stack<String> redo;
	
	/**
	 * 上一次操作记录
	 */
	private String lastCommand;
	private boolean isUndoOrRedo;
	
	/**
	 * 每次操作后textArea中的内容
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
		// Ctrl + Z 撤销
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
		// Ctrl + Y 重做
		else if(keyCode == KeyEvent.VK_Y) {
			// 只有在上一次操作为撤销或重做时才能进行重做
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
		// 如果进行撤销或重做操作后紧跟其他操作，则清空撤销栈和重做栈
		if(lastCommand.equals("Undo") || lastCommand.equals("Redo")) {
			while(!undo.empty())
				undo.pop();
			while(!redo.empty())
				redo.pop();
		}
		// 操作前文本内容压入撤销栈
		if(undo.empty() || (!undo.empty() && !undo.peek().equals(nowString))) {
			undo.push(nowString);
		}
		// nowString记录操作后文本内容
		nowString = textArea.getText();
		
		// 重复操作合并
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
