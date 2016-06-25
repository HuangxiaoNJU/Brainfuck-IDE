//������VersionService��Stub��������ͬ
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VersionService extends Remote {
	
	public String saveVersion(String code, String versionName) throws RemoteException;
	
	public String readVersion(String versionName) throws RemoteException;
	
	public void clear() throws RemoteException;
}
