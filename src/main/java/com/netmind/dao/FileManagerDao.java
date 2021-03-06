package com.netmind.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class FileManagerDao extends Thread {

	public static File file = null;

	private static HashMap<String, File> fileType = new HashMap<String, File>();

	private String fileName;

	public FileManagerDao(String fileName) {
		super();
		this.fileName = fileName;
	}

	@Override
	public void run() {
		file = new File(fileName);

		try {
			if (file.createNewFile()) {
				System.out.println("File is created!");
				if (fileName.contains(".txt")) {
					fileType.put("txt", file);

				} else if (fileName.contains(".json")) {
					fileType.put("json", file);
				}

			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileName(String type) {
		file = fileType.get(type);
		return file.getName();
	}

}
