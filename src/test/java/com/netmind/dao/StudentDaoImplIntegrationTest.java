package com.netmind.dao;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.netmind.business.StudentBlImpl;
import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.Student;

class StudentDaoImplIntegrationTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addStudentToFile() throws IOException {
		StudentBl studentBl = new StudentBlImpl();
		Student student = new Student();
		student.setIdStudent(1);
		student.setName("Pepe");
		student.setSurname("Soto");
		student.setAge(45);
		student.setDateOfBirth(LocalDate.parse("1975-04-10"));

		studentBl.add(student);

		Student student1 = new Student();
		student1.setIdStudent(1);
		student1.setName("Pablo");
		student1.setSurname("perez");
		student1.setAge(30);
		student1.setDateOfBirth(LocalDate.parse("1975-04-10"));

		studentBl.add(student1);

	}

	@Test
	void testAddToJsonFile() throws IOException {
		StudentBl studentBl = new StudentBlImpl();

		Student student = new Student();
		student.setIdStudent(1);
		student.setName("Pepe");
		student.setSurname("Soto");
		student.setAge(45);
		student.setDateOfBirth(LocalDate.parse("1975-04-10"));

		studentBl.addJsonFile(student);

		Student student1 = new Student();
		student1.setIdStudent(1);
		student1.setName("Pablo");
		student1.setSurname("perez");
		student1.setAge(30);
		student1.setDateOfBirth(LocalDate.parse("1975-04-10"));

		studentBl.addJsonFile(student1);
		fail("Not yet implemented");
	}

}
