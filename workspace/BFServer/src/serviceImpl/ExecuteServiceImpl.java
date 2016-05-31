//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;

import service.ExecuteService;
import service.UserService;

public class ExecuteServiceImpl implements ExecuteService {
	
	public static int[] array = new int[100];
	
	public static String input;
	
	public static String result = "";

	private static void compile(String code, int ptr) {
		int i = 0;
		while (i < code.length()) {
			switch(code.charAt(i)) {
			case '>':   
				ptr++; break;
			case '<':   
				ptr--; break;
			case '+':   
				array[ptr]++; break;
			case '-':   
				array[ptr]--; break;
			case '.':   
				result += (char)array[ptr]; break;
			case ',':   
				array[ptr] = input.charAt(0);
				input = input.substring(1);
				break;
			case '[':
				// 寻找 “[” 对应的 “]”
				int temp = 0;
				for (int j = i + 1; j < code.length(); j++) {
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
							compile(code.substring(i + 1, j), ptr);
						i = j;
						break;
					}
				}
				break;
			default:  
				break;
			}
			i++;
		}
	}
	
	@Override
	public String execute(String code, String param) throws RemoteException {
		input = param;
		compile(code, 0);
		return result;
	}

}
