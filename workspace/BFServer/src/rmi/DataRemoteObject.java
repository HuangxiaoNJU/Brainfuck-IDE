package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import service.CompileService;
import service.ExecuteService;
import service.IOService;
import service.UserService;
import service.VersionService;
import serviceImpl.CompileServiceImpl;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;
import serviceImpl.VersionServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements CompileService, ExecuteService, IOService, UserService, VersionService {
	
	private static final long serialVersionUID = 4029039744279087114L;
	private CompileService compileService;
	private ExecuteService executeService;
	private IOService iOService;
	private UserService userService;
	private VersionService versionService;
	
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		compileService = new CompileServiceImpl();
		executeService = new ExecuteServiceImpl();
		versionService = new VersionServiceImpl();
	}

	@Override
	public boolean createFile(String userId, String fileName) throws RemoteException {
		return iOService.createFile(userId, fileName);
	}
	
	@Override
	public boolean writeFile(String file, String userId, String fileName) throws RemoteException{
		return iOService.writeFile(file, userId, fileName);
	}

	@Override
	public String readFile(String userId, String fileName) throws RemoteException{
		return iOService.readFile(userId, fileName);
	}

	@Override
	public String[] readFileList(String userId) throws RemoteException{
		return iOService.readFileList(userId);
	}

	@Override
	public String signup(String username, String password) throws RemoteException {
		return userService.signup(username, password);
	}
	
	@Override
	public String login(String username, String password) throws RemoteException {
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return userService.logout(username);
	}

	@Override
	public String execute(String code, String param) throws RemoteException {
		return executeService.execute(code, param);
	}

	@Override
	public boolean saveVersion(String code, String versionName) throws RemoteException {
		return versionService.saveVersion(code, versionName);
	}

	@Override
	public String readVersion(String versionName) throws RemoteException {
		return versionService.readVersion(versionName);
	}

	@Override
	public void clear() throws RemoteException {
		versionService.clear();
	}

	@Override
	public String compile(String code, String param) throws RemoteException {
		return compileService.compile(code, param);
	}


}
