package rmi;

import java.rmi.Remote;

import service.CompileService;
import service.ExecuteService;
import service.IOService;
import service.UserService;
import service.VersionService;

public class RemoteHelper {
	private Remote remote;
	private static RemoteHelper remoteHelper = new RemoteHelper();
	
	public static RemoteHelper getInstance() {
		return remoteHelper;
	}
	
	private RemoteHelper() {
	}
	
	public void setRemote(Remote remote) {
		this.remote = remote;
	}
	
	public CompileService getCompileService() {
		return (CompileService)remote;
	}
	
	public ExecuteService getExecuteService() {
		return (ExecuteService)remote;
	}
	
	public IOService getIOService() {
		return (IOService)remote;
	}
	
	public UserService getUserService() {
		return (UserService)remote;
	}
	
	public VersionService getVersionService() {
		return (VersionService)remote;
	}
}
