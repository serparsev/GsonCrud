package com.netmind.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.netmind.business.StudentBlImpl;
import com.netmind.dao.contracts.StudentDao;
import com.netmind.model.Student;

public class StudentDaoImpl implements StudentDao {

	static Properties prop = null;
	static InputStream input = null;

	private static ArrayList<Student> studentList = null;
	static final Logger logger = Logger.getLogger(StudentDaoImpl.class);
	static JSONArray studentJsonList = new JSONArray();

	static {
		studentList = new ArrayList<Student>();
	}

	static {
	}

	static {
		prop = new Properties();
		try {
			input = StudentBlImpl.class
					.getResourceAsStream("/config.properties");
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public boolean add(Student student) {
		logger.info("add method called");
		studentList.add(student);

		return true;
	}

	@Override
	public boolean addStudentToFile(Student student) throws IOException {
		logger.info("addStudentToFile method called");
		try (FileWriter fileWriter = new FileWriter(
				FileManagerDao.getFileName(), true);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
			bufferWriter.write(student.toTxtFile());
			bufferWriter.write(System.lineSeparator());
		} catch (IOException e) {
			logger.error(e.getMessage() + student.toString());
			throw e;
		}

		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean addToJsonFile(Student student) throws IOException {

		JSONObject studentDetails = new JSONObject();
		studentDetails.put("id", student.getIdStudent());
		studentDetails.put("name", student.getName());
		studentDetails.put("surname", student.getSurname());
		studentDetails.put("dateOfBirth", student.getDateOfBirth().toString());
		studentDetails.put("age", student.getAge());

		JSONObject studentJson = new JSONObject();
		studentJson.put("student", studentDetails);

		studentJsonList.add(studentJson);

		try (FileWriter file = new FileWriter(FileManagerDao.getFileName())) {
			file.write(studentJsonList.toString());
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
}
