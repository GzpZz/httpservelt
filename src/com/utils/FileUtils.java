package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtils {
	public static void copyFile(File src, OutputStream out) {
		try (FileInputStream fin = new FileInputStream(src);) {
			byte[] buf = new byte[1024];
			int len;
			while ((len = fin.read(buf)) != -1) {
				out.write(buf, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
