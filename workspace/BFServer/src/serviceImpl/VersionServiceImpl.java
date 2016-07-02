package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.VersionService;

public class VersionServiceImpl implements VersionService {
	
	/**
	 * 上一版本内容
	 */
	private static String lastVersion;
	
	public VersionServiceImpl() {
//		File directory = new File("Version");
//		if(!directory.exists())
//			directory.mkdir();
		lastVersion = "";
	}

	@Override
	public boolean saveVersion(String userId, String fileName, String code, String versionName) throws RemoteException {
		if(code.equals(lastVersion))
			return false;
		File versionDirectory = new File("Version/" + userId + "/" + fileName);
		File versionFile = new File(versionDirectory, versionName);
		try {
			if(!versionFile.exists()) {
				versionFile.createNewFile();
			}
			FileWriter fw = new FileWriter(versionFile, false);
			fw.write(code);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lastVersion = code;
		return true;
	}

	@Override
	public String readVersion(String userId, String fileName, String versionName) throws RemoteException {
		// 恢复并删除此版本
		String content = "";
		File versionDirectory = new File("Version/" + userId + "/" + fileName);
		File file = new File(versionDirectory, versionName);
		try {
			BufferedReader bf = new BufferedReader(new FileReader(file));
			String str = bf.readLine();
			// 文件内容为空
			if(str == null) {
				bf.close();
				return "";
			}
			content += str;
			str = bf.readLine();
			while(str != null) {
				content += "\n" + str;
				str = bf.readLine();
			}
			bf.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.delete();
		return content;
	}

//	@Override
//	public void clear() throws RemoteException {
//		lastVersion = null;
//		File[] files = versionDirectory.listFiles();
//		for (int i = 0; i < files.length; i++) {
//			files[i].delete();
//		}
//	}

	@Override
	public String[] readVersionList(String userId, String fileName) {
		File versionDirectory = new File("Version/" + userId + "/" + fileName);
		File[] files = versionDirectory.listFiles();
		// 过滤隐藏文件
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
