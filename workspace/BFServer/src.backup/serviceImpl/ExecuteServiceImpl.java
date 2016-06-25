//�벻Ҫ�޸ı��ļ���
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {
	
	private int[] array = new int[1024];
	
	private String input;
	
	private String output;
	
	private void init(String param) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
		input = param;
		output = "";
	}

	private void compile(String code, int ptr) {
		// BF�������е���λ��
		int position = 0;
		while (position < code.length()) {
			switch(code.charAt(position)) {
			case '>':   
				ptr++; break;
			case '<':   
				ptr--; break;
			case '+':   
				array[ptr]++; break;
			case '-':   
				array[ptr]--; break;
			case '.':   
				output += (char)array[ptr]; break;
			case ',':   
				array[ptr] = input.charAt(0);
				input = input.substring(1);
				break;
			case '[':	
				// Ѱ�� ��[�� ��Ӧ�� ��]��
				int temp = 0;
				for (int j = position + 1; j < code.length(); j++) {
					if(code.charAt(j) == '[') {
						temp ++;
						continue;
					}
					if(code.charAt(j) == ']') {
						if(temp != 0) {
							temp --;
							continue;
						}
						while(array[ptr] != 0)
							compile(code.substring(position + 1, j), ptr);
						position = j;
						break;
					}
				}
				break;
			default:
				break;
			}
			position++;
		}
	}
	
	@Override
	public String execute(String code, String param) throws RemoteException {
		init(param);
		compile(code, 0);
		return output;
	}

}
