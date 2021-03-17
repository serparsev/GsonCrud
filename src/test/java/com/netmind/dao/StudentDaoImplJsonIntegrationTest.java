package com.netmind.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.netmind.common.model.Student;
import com.netmind.common.util.Config;
import com.netmind.dao.contracts.StudentDao;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class StudentDaoImplJsonIntegrationTest {

	static StudentDao studentDaoJson = new StudentDaoImplJson();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		FileManagerDao.createFile(Config.getTxtFileName());
		FileManagerDao.createFile(Config.getJsonFileName());

		Student student = new Student();
		student.setIdStudent(1);
		student.setName("test1");
		student.setSurname("ferrer");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		Student student1 = new Student();
		student1.setIdStudent(2);
		student1.setName("test2");
		student1.setSurname("ferrer");
		student1.setAge(20);
		DateTimeFormatter formatter1 = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth2 = LocalDate.parse("21-02-1999", formatter1);
		student.setDateOfBirth(dateOfBirth2);

		studentDaoJson.addToFile(student);
		studentDaoJson.addToFile(student1);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

	}

	/*
	 * @Before public void setUp() throws Exception { }
	 * 
	 * @After public void tearDown() throws Exception { }
	 */
	@Test
	@Parameters({ "3, pepe, soto, 21, 26-02-2000",
			"4, Mar, Biel, 21, 26-02-2000",
			"5, Juan, Fernando, 21, 26-02-2000" })
	public void testAddToFile(Integer idStudent, String name, String surname,
			Integer age, String date) throws IOException {

		Student student = new Student();
		student.setIdStudent(idStudent);
		student.setName(name);
		student.setSurname(surname);
		student.setAge(age);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse(date, formatter);
		student.setDateOfBirth(dateOfBirth);

		assertTrue(studentDaoJson.addToFile(student) == true);
	}

	@Test
	public void testGetFromFile() throws IOException {
		assertTrue(studentDaoJson.getFromFile().size() > 0);
	}

	@Test
	public void testUpdateFile() throws IOException {
		DateTimeFormatter formatter1 = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

		Student oldStudent = new Student();
		oldStudent.setIdStudent(45);
		oldStudent.setName("oldStudent");
		oldStudent.setSurname("diaz");
		oldStudent.setAge(20);
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter1);
		oldStudent.setDateOfBirth(dateOfBirth);

		UUID uuid = oldStudent.getUUId();
		studentDaoJson.addToFile(oldStudent);

		Student updatedStudent = new Student();
		updatedStudent.setIdStudent(45);
		updatedStudent.setName("UpdatedStudent");
		updatedStudent.setSurname("updated");
		updatedStudent.setAge(20);
		LocalDate dateOfBirth2 = LocalDate.parse("21-02-1999", formatter1);
		updatedStudent.setDateOfBirth(dateOfBirth2);

		assertTrue(studentDaoJson.updateFile(uuid, updatedStudent) == true);
	}

	@Test
	public void testRemoveFromFile() throws IOException {
		DateTimeFormatter formatter1 = DateTimeFormatter
				.ofPattern("dd-MM-yyyy");

		Student removeStudent = new Student();
		removeStudent.setIdStudent(45);
		removeStudent.setName("removeStudent");
		removeStudent.setSurname("diaz");
		removeStudent.setAge(20);
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter1);
		removeStudent.setDateOfBirth(dateOfBirth);

		UUID uuid = removeStudent.getUUId();
		studentDaoJson.addToFile(removeStudent);

		assertTrue(studentDaoJson.removeFromFile(uuid) == true);
	}
}
