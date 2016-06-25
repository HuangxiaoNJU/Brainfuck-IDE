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
	 * passwordsFile���ڴ洢�����û����û���������
	 */
	private static File passwordsFile;
	
	/**
	 * �û��ļ�Ŀ¼
	 */
	private static File usersDirectory;
	
	/**
	 * ���췽��
	 * file��ʼ��
	 */
	public UserServiceImpl() {
		passwordsFile = new File("admin_password");
		usersDirectory = new File("Users");
		if(!usersDirectory.exists())
			usersDirectory.mkdir();
	}
	
	/**
	 * ����û����Ƿ��Ѵ��ڣ������ڣ�����true���������ڣ�����false
	 */
	private boolean isUsernameExist(String username) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(passwordsFile));
			String info = bf.readLine();
			while(info != null) {
				if(info.split(" ")[0].equals(username)) {
					bf.close();
					return true;
				}
				info = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * ����¼�Ƿ�ɹ�
	 */
	private String LoginCheckInfo(String username, String password) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(passwordsFile));
			String info = bf.readLine();
			while(info != null) {
				String inputUsername = info.split(" ")[0];
				String inputPassword = info.split(" ")[1];
				if(inputUsername.equals(username)) {
					if(inputPassword.equals(password)) {
						bf.close();
						return "success";
					}
					else {
						bf.close();
						return "Password error!";
					}
				}
				info = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "User name does not exist!";
	}
	
	@Override
	public String signup(String username, String password) throws RemoteException {
		// �û���������ո���
		for (int i = 0; i < username.length(); i++) {
			if(username.charAt(i) == ' ')
				return "User name cannot contain spaces!";
		}
		for (int i = 0; i < password.length(); i++)
			if(password.charAt(i) == ' ') {
				return "Password cannot contain spaces!";
			}
		if(isUsernameExist(username))
			return "The user name has been registered!";
		
		try {
			FileWriter fw = new FileWriter(passwordsFile, true);
			fw.write(username + ' ' + password + "\r\n");
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		return LoginCheckInfo(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO
		return true;
	}

}
