package com.netmind.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
import com.netmind.dao.contracts.StudentDao;

public class StudentDaoImpl implements StudentDao {

	private static List<Student> studentList = null;
	static final Logger logger = Logger.getLogger(StudentDaoImpl.class);

	static {
		studentList = new ArrayList<Student>();
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
				FileManagerDao.getFileName("txt"), true);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {
			bufferWriter.write(student.toTxtFile());
			bufferWriter.write(System.lineSeparator());
		} catch (IOException e) {
			logger.error(e.getMessage() + student.toString());
			throw e;
		}

		return true;
	}

	@Override
	public boolean addToJsonFile(Student student) throws IOException {
		List<Student> studentJsonList = getAllFromJson();
		studentJsonList.add(student);

		return writeToJsonFile(studentJsonList);
	}

	@Override
	public List<Student> getAllFromJson() {
		List<Student> studentJsonList = null;
		Type typeStudentsList = new TypeToken<List<Student>>() {
		}.getType();

		studentJsonList = new Gson().fromJson(getJsonFile(), typeStudentsList);

		if (studentJsonList == null) {
			studentJsonList = new ArrayList<Student>();
		}
		return studentJsonList;
	}

	@Override
	public boolean updateJsonFile(UUID uuid, Student updatedStudent) {
		List<Student> studentJsonList = getAllFromJson();
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
	public boolean removeFromJsonFile(UUID uuid) {
		List<Student> studentJsonList = getAllFromJson();
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

	public static String getJsonFile() {
		StringBuilder serializedStudent = new StringBuilder();
		try (BufferedReader buff = new BufferedReader(
				new FileReader(FileManagerDao.getFileName("json")));) {
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

	public boolean writeToJsonFile(List<Student> studentJsonList) {
		try (Writer writer = new FileWriter(FileManagerDao.getFileName("json"),
				false)) {

//			GsonBuilder gsonBuilder = new GsonBuilder();
//			gsonBuilder.registerTypeAdapter(LocalDate.class,
//					new LocalDateSerializer());

			Gson gson = new GsonBuilder()
					.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
					.setPrettyPrinting().create();

			gson.toJson(studentJsonList.toArray(), writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
