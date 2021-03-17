package com.netmind.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.netmind.common.model.Student;
import com.netmind.common.util.Constants;
import com.netmind.dao.contracts.StudentDao;

public class StudentDaoImplJson implements StudentDao {

	static final Logger logger = Logger.getLogger(StudentDaoImplJson.class);

	@Override
	public boolean addToFile(Student student) {
		logger.info("addToJsonFile method called");
		List<Student> studentJsonList = getFromFile();
		studentJsonList.add(student);

		return writeToJsonFile(studentJsonList);
	}

	public boolean writeToJsonFile(List<Student> studentJsonList) {
		try (Writer writer = new FileWriter(
				FileManagerDao.getFileName(Constants.HASH_JSON), false)) {

			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
					.setPrettyPrinting().create();

			gson.toJson(studentJsonList.toArray(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Student> getFromFile() {
		List<Student> studentJsonList = null;
		Type typeStudentsList = new TypeToken<List<Student>>() {
		}.getType();

		studentJsonList = new Gson().fromJson(getJsonFile(), typeStudentsList);

		return (studentJsonList != null) ? studentJsonList
				: new ArrayList<Student>();
	}

	public static String getJsonFile() {
		StringBuilder serializedStudent = new StringBuilder();
		try (BufferedReader buff = new BufferedReader(new FileReader(
				FileManagerDao.getFileName(Constants.HASH_JSON)));) {
			String line;
			while ((line = buff.readLine()) != null)
				serializedStudent.append(line.trim());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return serializedStudent.toString();
	}

	@Override
	public boolean updateFile(UUID uuid, Student updatedStudent) {
		List<Student> studentJsonList = getFromFile();
		Student oldStudent = null;

		oldStudent = studentJsonList.stream()
				.filter(student -> student.getUUId().equals(uuid)).findFirst()
				.orElse(null);

		if (oldStudent != null) {
			oldStudent.setIdStudent(updatedStudent.getIdStudent());
			oldStudent.setName(updatedStudent.getName());
			oldStudent.setSurname(updatedStudent.getSurname());
			oldStudent.setAge(updatedStudent.getAge());
			oldStudent.setDateOfBirth(updatedStudent.getDateOfBirth());

			return writeToJsonFile(studentJsonList);
		} else {
			return false;
		}
	}

	@Override
	public boolean removeFromFile(UUID uuid) {
		List<Student> studentJsonList = getFromFile();
		Student removedStudent = null;

		removedStudent = studentJsonList.stream()
				.filter(student -> student.getUUId().equals(uuid)).findFirst()
				.get();

		if (removedStudent != null) {
			studentJsonList.remove(removedStudent);
			return writeToJsonFile(studentJsonList);
		} else {
			return false;
		}
	}
}
