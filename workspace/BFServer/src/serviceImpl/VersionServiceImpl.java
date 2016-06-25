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
	
	public VersionServiceImpl() {
		versionDirectory = new File("Version");
		if(!versionDirectory.exists())
			versionDirectory.mkdir();
	}

	@Override
	public String saveVersion(String code, String versionName) throws RemoteException {
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
		return "success";
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
		File[] files = versionDirectory.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

}
