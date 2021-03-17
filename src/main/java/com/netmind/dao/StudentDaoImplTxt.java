package com.netmind.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.netmind.common.model.Student;
import com.netmind.common.util.Constants;
import com.netmind.dao.contracts.StudentDao;

public class StudentDaoImplTxt implements StudentDao {

	static final Logger logger = Logger.getLogger(StudentDaoImplJson.class);

	@Override
	public boolean addToFile(Student student) {
		logger.info("addToTxtFile method called");
		List<Student> studentList = getFromFile();
		studentList.add(student);

		return writeToTxtFile(studentList);
	}

	public boolean writeToTxtFile(List<Student> studentList) {

		try (FileWriter fileWriter = new FileWriter(
				FileManagerDao.getFileName(Constants.HASH_TXT), false);
				BufferedWriter bufferWriter = new BufferedWriter(fileWriter)) {

			for (Student newStudent : studentList) {
				bufferWriter.write(newStudent.toTxtFile());
				bufferWriter.write(System.lineSeparator());
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return true;
	}

	@Override
	public List<Student> getFromFile() {
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
	public boolean updateFile(UUID uuid, Student updatedStudent) {
		List<Student> studentList = getFromFile();

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
	public boolean removeFromFile(UUID uuid) {
		List<Student> studentList = getFromFile();

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
}
