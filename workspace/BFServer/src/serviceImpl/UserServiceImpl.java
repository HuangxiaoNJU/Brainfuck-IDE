package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{
	
	/**
	 * file���ڴ洢�����û����û���������
	 */
	private File file;
	
	/**
	 * ���췽��
	 * file��ʼ��
	 */
	public UserServiceImpl() {
		file = new File("admin_password");
	}
	
	/**
	 * ����û����Ƿ��Ѵ��ڣ������ڣ�����true���������ڣ�����false
	 */
	private boolean isUsernameExist(String username) {
		boolean isExist = false;
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String info = bf.readLine();
			while(info != null) {
				if(info.split(" ")[0].equals(username))
					isExist = true;
				info = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isExist;
	}
	
	/**
	 * ����¼�Ƿ�ɹ������ɹ�������true�������ɹ�������false
	 */
	private boolean isLoginSuccess(String username, String password) {
		boolean isLogin = false;
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String info = bf.readLine();
			while(info != null) {
				String inputUsername = info.split(" ")[0];
				String inputPassword = info.split(" ")[1];
				if(inputUsername.equals(username) && inputPassword.equals(password)) {
					isLogin = true;
					break;
				}
				info = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isLogin;
	}
	
	@Override
	public boolean signup(String username, String password) throws RemoteException {
		if(isUsernameExist(username)) {
			System.out.println("ע��ʧ�ܣ�");
			return false;
		}
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(username + ' ' + password + "\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("ע��ɹ���");
		return true;
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		if(isLoginSuccess(username, password)) {
			System.out.println("��¼�ɹ���");
			return true;
		}
		System.out.println("��¼ʧ�ܣ�");
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
