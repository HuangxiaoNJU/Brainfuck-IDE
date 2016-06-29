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
	 * 版本文件目录
	 */
	private static File versionDirectory;
	/**
	 * 上一版本内容
	 */
	private static String lastVersion;
	
	public VersionServiceImpl() {
		versionDirectory = new File("Version");
		if(!versionDirectory.exists())
			versionDirectory.mkdir();
		lastVersion = null;
	}

	@Override
	public boolean saveVersion(String code, String versionName) throws RemoteException {
		if(code.equals(lastVersion))
			return false;
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
	public String readVersion(String versionName) throws RemoteException {
		// 恢复并删除此版本
		String content = "";
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
				content += "\r\n" + str;
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

	@Override
	public void clear() throws RemoteException {
		lastVersion = null;
		File[] files = versionDirectory.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

}
