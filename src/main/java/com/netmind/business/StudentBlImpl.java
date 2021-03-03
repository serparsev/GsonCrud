package com.netmind.business;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import com.netmind.business.contracts.StudentBl;
import com.netmind.dao.FileManagerDao;
import com.netmind.dao.StudentDao;
import com.netmind.model.Student;

public class StudentBlImpl implements StudentBl {

	public boolean add(Student student) throws IOException {
		StudentDao studentDao = new StudentDao();

		student.setAge(calculateAge(student.getDateOfBirth()));

		FileManagerDao.createFile("student.txt");

		return studentDao.addStudentToFile(student);
	}

	private int calculateAge(LocalDate dateOfBirth) {
		Period edad = Period.between(dateOfBirth, LocalDate.now());
		return edad.getYears();
	}

	@Override
	public boolean addJsonFile(Student student) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
