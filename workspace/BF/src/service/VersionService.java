//服务器VersionService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VersionService extends Remote {
	
	public boolean saveVersion(String code, String versionName) throws RemoteException;
	
	public String readVersion(String versionName) throws RemoteException;
	
	public void clear() throws RemoteException;
}
