package com.secretborder.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtil {

    public static File writeFile(byte[] bytes, String path) {
        if (bytes == null || bytes.length == 0) return null;

        try (FileOutputStream fos = new FileOutputStream(path, false)) {
            fos.write(bytes);

            return new File(path);
        } catch (Exception e) {
			throw new RuntimeException(e);
        }
    }

	public static byte[] getBytes(String filePath) {
		return getBytes(new File(filePath));
	}

	public static byte[] getBytes(File file) {
		if(!exist(file)) return null;
		
		int len = (int) file.length();
		byte[] sendBuf = new byte[len];
		
		try(FileInputStream inFile = new FileInputStream(file)) {
			inFile.read(sendBuf, 0, len);
		} catch (Exception e) {
			throw new RuntimeException(e);
        }
		
		return sendBuf;
	}

	public static boolean exist(File file) {
		return file!=null && file.exists();
	}

}
