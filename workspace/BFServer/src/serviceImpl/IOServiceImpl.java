package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.IOService;

public class IOServiceImpl implements IOService{
		
	@Override
	public boolean createFile(String userId, String fileName) throws RemoteException {
		// �������û����ļ��У������û�������
		File filePath = new File(userId);
		if(!filePath.exists())
			filePath.mkdir();
		File file = new File(filePath, fileName);
		// ���ļ��Ѵ��ڣ�����ʧ��
		if(file.exists())
			return false;
		// �ļ������ڣ��������ļ�
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public boolean writeFile(String content, String userId, String fileName) {
		File f = new File(new File(userId), fileName);
		try {
			FileWriter fw = new FileWriter(f, false);
			fw.write(content);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String userId, String fileName) {
		String content = "";
		File file = new File(new File(userId), fileName);
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String str = bf.readLine();
			// �ļ�����Ϊ��
			if(str == null) {
				bf.close();
				return "";
			}
			content += str;
			str = bf.readLine();
			while(str != null) {
				content += "\r\n" + str;
				str = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public String[] readFileList(String userId) {
		File directory = new File(userId);
		// ��Ŀ¼�����ڣ�����û���δ�������ļ�
		if(!directory.exists()) {
			return null;
		}
		File[] files = directory.listFiles();
		// ���������ļ�
		int length = files.length;
		for (int i = 0; i < files.length; i++)
			if(files[i].isHidden())
				length --;
		String[] fileNames = new String[length];
		int ptr = 0;
		for (int i = 0; i < files.length; i++)
			if(!files[i].isHidden()) {
				fileNames[ptr] = files[i].getName();
				ptr ++;
			}
		return fileNames;
	}

}
