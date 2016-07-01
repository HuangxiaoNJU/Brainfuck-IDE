package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CompileService extends Remote {

	public String compile(String code, String param) throws RemoteException;
	
}
