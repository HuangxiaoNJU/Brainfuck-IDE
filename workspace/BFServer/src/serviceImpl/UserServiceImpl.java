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
	 * file用于存储所有用户的用户名和密码
	 */
	private File file;
	
	/**
	 * 构造方法
	 * file初始化
	 */
	public UserServiceImpl() {
		file = new File("admin_password");
	}
	
	/**
	 * 检测用户名是否已存在，若存在，返回true，若不存在，返回false
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
	 * 检测登录是否成功，若成功，返回true，若不成功，返回false
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
			System.out.println("注册失败！");
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
		System.out.println("注册成功！");
		return true;
	}

	@Override
	public boolean login(String username, String password) throws RemoteException {
		if(isLoginSuccess(username, password)) {
			System.out.println("登录成功！");
			return true;
		}
		System.out.println("登录失败！");
		return false;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
