//��Ҫ�ͻ��˵�Stub
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
	public String signup(String username, String password) throws RemoteException;
	
	public String login(String username, String password) throws RemoteException;

	public boolean logout(String username) throws RemoteException;
}
