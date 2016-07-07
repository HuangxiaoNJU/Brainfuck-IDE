package serviceImpl;

import java.rmi.RemoteException;

import service.CompileService;

public class CompileServiceImpl implements CompileService {
	private int[] array = new int[128];
	
	private String input;
	
	private String result;
	
	private void init(String param) {
		for (int i = 0; i < array.length; i++)
			array[i] = 0;
		input = param;
		result = "Success";
	}

	private void check(String code, int ptr) throws CompileException {
		// BF�������е���λ��
		int position = 0;
		while (position < code.length()) {
			switch(code.charAt(position)) {
			case '>':   
				// �����±�����
				if(++ptr >= 128) {
					result = "Array index overflow!";
					throw new CompileException();
				}
				break;
			case '<':   
				// �����±�����
				if(--ptr < 0) {
					result = "Array index underflow!";
					throw new CompileException();
				}
				break;
			case '+':   
				array[ptr]++; break;
			case '-':   
				array[ptr]--; break;
			case '.':   
				// �������ݳ���ACSII���ʾ��Χ
				if(array[ptr] < 0 || array[ptr] > 127) {
					result = "IntCastChar exception!";
					throw new CompileException();
				}
				break;
			case ',':   
				if(input.length() == 0) {
					result = "Parameter exception!";
					throw new CompileException();
				}
				array[ptr] = input.charAt(0);
				input = input.substring(1);
				break;
			case '[':	
				// �������ƥ��
				boolean isMatch = false;
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
						isMatch = true;
						while(array[ptr] != 0)
							check(code.substring(position + 1, j), ptr);
						position = j;
						break;
					}
				}
				// ���Ų�ƥ��
				if(!isMatch) {
					result = "Bracket exception!";
					throw new CompileException();
				}
				break;
			case ']':
				// ���Ų�ƥ��
				result = "Bracket exception!";
				throw new CompileException();
			default:
				break;
			}
			position++;
		}
	}
	
	static class CompileException extends Exception {
		private static final long serialVersionUID = 1L;
	}

	@Override
	public String compile(String code, String param) throws RemoteException {
		init(param);
		try {
			check(code, 0);
		} catch(CompileException e) {
			return result;
		}
		return result;
	}

}
