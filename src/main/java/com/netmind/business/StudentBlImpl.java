package com.netmind.business;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

import org.apache.log4j.Logger;

import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.Student;
import com.netmind.common.util.Config;
import com.netmind.dao.FileManagerDao;
import com.netmind.dao.contracts.StudentDao;

public class StudentBlImpl implements StudentBl {

	static final Logger logger = Logger.getLogger(StudentBlImpl.class);
	private StudentDao studentDao;

	// inyectable y mockable
	public StudentBlImpl(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public boolean add(Student student) throws IOException {
		student.setAge(calculateAge(student.getDateOfBirth()));

		FileManagerDao fileManagerDaoTxtThread = new FileManagerDao(
				Config.getTxtFileName());
		FileManagerDao fileManagerDaoJsonThread = new FileManagerDao(
				Config.getJsonFileName());

		try {
			fileManagerDaoTxtThread.start();
			fileManagerDaoTxtThread.join();
			fileManagerDaoJsonThread.start();
			fileManagerDaoJsonThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info(Config.getTextTxtFile());
		logger.info(Config.getJsonFileName());

		return (studentDao.addToTxtFile(student)
				&& studentDao.addToJsonFile(student));
	}

	private int calculateAge(LocalDate dateOfBirth) {
		Period edad = Period.between(dateOfBirth, LocalDate.now());
		return edad.getYears();
	}
}
