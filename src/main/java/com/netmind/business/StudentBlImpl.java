package com.netmind.business;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.apache.log4j.Logger;

import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.Student;
import com.netmind.common.util.Config;
import com.netmind.dao.FileManagerDao;
import com.netmind.dao.StudentDaoImpl;
import com.netmind.dao.contracts.StudentDao;

public class StudentBlImpl implements StudentBl {

	static Logger logger = Logger.getLogger(StudentBlImpl.class);

	@Override
	public boolean add(Student student) throws IOException {
		StudentDao studentDao = new StudentDaoImpl();

		student.setAge(calculateAge(student.getDateOfBirth()));

		FileManagerDao.createFile(Config.getTxtFileName());
		logger.info(Config.getTxtText());

		return studentDao.addStudentToFile(student);
	}

	private int calculateAge(LocalDate dateOfBirth) {
		Period edad = Period.between(dateOfBirth, LocalDate.now());
		return edad.getYears();
	}

	@Override
	public boolean addJsonFile(Student student) throws IOException {
		// TODO Auto-generated method stub
		StudentDao studentDao = new StudentDaoImpl();

		FileManagerDao.createFile(Config.getJsonFileName());
		logger.info(Config.getJsonText());

		return studentDao.addToJsonFile(student);
	}

}
