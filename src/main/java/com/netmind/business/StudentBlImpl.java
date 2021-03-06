package com.netmind.business;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Period;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.Student;
import com.netmind.common.util.Constants;
import com.netmind.dao.FileManagerDao;
import com.netmind.dao.StudentDaoImpl;
import com.netmind.dao.contracts.StudentDao;

public class StudentBlImpl implements StudentBl {

	static Logger logger = Logger.getLogger(StudentBlImpl.class);
	static Properties prop = null;
	static InputStream input = null;

	static {
		prop = new Properties();
		try {
			input = StudentBlImpl.class
					.getResourceAsStream(Constants.PROPERTIES_FILE);
			prop.load(input);
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new ExceptionInInitializerError(e);
		}
	}

	@Override
	public boolean add(Student student) throws IOException {
		StudentDao studentDao = new StudentDaoImpl();

		student.setAge(calculateAge(student.getDateOfBirth()));

		FileManagerDao.createFile(prop.getProperty(Constants.TXT_FILE_NAME));
		logger.info(Constants.TXT_FILE_CREATED);

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

		FileManagerDao.createFile(prop.getProperty(Constants.JSON_FILE_NAME));
		logger.info(Constants.JSON_FILE_CREATED);

		return studentDao.addToJsonFile(student);
	}

}
