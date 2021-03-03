package com.netmind.dao;

import java.io.File;
import java.io.IOException;

public class FileManagerDao {

	public static File file = null;

	public static void createFile(String fileName) {
		file = new File(fileName);

		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getFileName() {
		return file.getName();
	}

}
