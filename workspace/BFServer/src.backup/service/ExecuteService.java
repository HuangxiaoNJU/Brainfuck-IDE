//�벻Ҫ�޸ı��ļ���
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ExecuteService extends Remote {
	
	/**
	 * ���ǽ�ͨ���˷���������Ľ��������ܣ��벻Ҫ�޸ķ��������������ͣ����ز�������
	 * @param code bfԴ����
	 * @return ���н��
	 * @throws RemoteException
	 */
	public String execute(String code, String param) throws RemoteException;
}
