package com.netmind.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.netmind.common.model.Student;
import com.netmind.common.util.Constants;
import com.netmind.dao.contracts.StudentDao;

public class StudentDaoImpl implements StudentDao {

	static final Logger logger = Logger.getLogger(StudentDaoImpl.class);

	@Override
	public boolean addToTxtFile(Student student) throws IOException {
		logger.info("addToTxtFile method called");
		List<Student> studentList = getAllFromTxt();
		studentList.add(student);

		return writeToTxtFile(studentList);
	}

	public boolean writeToTxtFile(List<Student> studentList)
			throws IOException {

		try (FileWriter fileWriter = new FileWriter(
				FileManagerDao.getFileName(Constants.HASH_TXT), false);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {

			for (Student newStudent : studentList) {
				bufferWriter.write(newStudent.toTxtFile());
				bufferWriter.write(System.lineSeparator());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw e;
		}
		return true;
	}

	@Override
	public boolean addToJsonFile(Student student) throws IOException {
		logger.info("addToJsonFile method called");
		List<Student> studentJsonList = getAllFromJson();
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
	public List<Student> getAllFromTxt() {
		List<Student> studentList = new ArrayList<Student>();
		try (Scanner reader = new Scanner(
				new File(FileManagerDao.getFileName(Constants.HASH_TXT)));) {

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("dd-MM-yyyy");
			LocalDate dateOfBirth;

			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String values[] = data.split(",");
				UUID uuid = UUID.fromString(values[0]);
				Student student = new Student(uuid);
				student.setIdStudent(Integer.parseInt(values[1]));
				student.setName(values[2]);
				student.setSurname(values[3]);
				student.setAge(Integer.parseInt(values[4]));
				dateOfBirth = LocalDate.parse(values[5], formatter);
				student.setDateOfBirth(dateOfBirth);

				studentList.add(student);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return studentList;
	}

	@Override
	public List<Student> getAllFromJson() {
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
	public boolean updateTxtFile(UUID uuid, Student updatedStudent)
			throws IOException {
		List<Student> studentList = getAllFromTxt();

		Student oldStudent = studentList.stream()
				.filter(student -> student.getUUId().equals(uuid)).findFirst()
				.orElse(null);

		if (oldStudent != null) {
			oldStudent.setIdStudent(updatedStudent.getIdStudent());
			oldStudent.setName(updatedStudent.getName());
			oldStudent.setSurname(updatedStudent.getSurname());
			oldStudent.setAge(updatedStudent.getAge());
			oldStudent.setDateOfBirth(updatedStudent.getDateOfBirth());

			return writeToTxtFile(studentList);
		} else {
			System.out.println("Student not found");
			return false;
		}
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
	public boolean removeFromTxtFile(UUID uuid) throws IOException {
		List<Student> studentList = getAllFromTxt();

		Student removedStudent = studentList.stream()
				.filter(student -> student.getUUId().equals(uuid)).findFirst()
				.orElse(null);

		if (removedStudent != null) {
			studentList.remove(removedStudent);
			return writeToTxtFile(studentList);
		} else {
			System.out.println("Student not found");
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
}
