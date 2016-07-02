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
	 * passwordsFile用于存储所有用户的用户名和密码
	 */
	private static File passwordsFile;
	
	/**
	 * onlineUsers记录所有在线用户
	 */
	private static File onlineUsersFile;
	
	/**
	 * 用户文件目录
	 */
	private static File usersDirectory;
	
	/**
	 * 构造方法
	 * file初始化
	 */
	public UserServiceImpl() {
		passwordsFile = new File("admin_password");
		usersDirectory = new File("Users");
		if(!usersDirectory.exists())
			usersDirectory.mkdir();
		onlineUsersFile = new File("onLine_Users");
		if(!onlineUsersFile.exists())
			try {
				onlineUsersFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 检测用户名是否已存在，若存在，返回true，若不存在，返回false
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
	 * 检测登录是否成功
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
		// 用户名、密码格式检查
		if(username.equals(""))
			return "User name cannot be null!";
		if(password.equals(""))
			return "Password cannot be null!";
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
			fw.write(username + ' ' + password + '\n');
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String login(String username, String password) throws RemoteException {
		String info = LoginCheckInfo(username, password);
		// 已登录用户更新
		if(info.equals("success")) {
			try {
				FileWriter fw = new FileWriter(onlineUsersFile, true);
				fw.write(username + '\n');
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

}
