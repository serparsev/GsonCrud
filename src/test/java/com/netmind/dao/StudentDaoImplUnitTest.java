package com.netmind.dao;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.netmind.common.model.Student;
import com.netmind.dao.contracts.StudentDao;

@RunWith(MockitoJUnitRunner.class)
public class StudentDaoImplUnitTest {

	@Mock
	private StudentDao studentDao;

	public List<Student> studentList = new ArrayList<Student>();

	Student student;
	Student student1;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		Student student = new Student();
		student.setIdStudent(1);
		student.setName("test1");
		student.setSurname("ferrer");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		UUID uuid = student.getUUId();

		Student student1 = new Student();
		student1.setIdStudent(1);
		student1.setName("test1");
		student1.setSurname("ferrer");
		student1.setAge(20);
		dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student1.setDateOfBirth(dateOfBirth);

		UUID uuid1 = student1.getUUId();

		studentList.add(student);
		studentList.add(student1);

		when(studentDao.addToJsonFile(student)).thenReturn(true);
		when(studentDao.getAllFromJson()).thenReturn(studentList);
		when(studentDao.updateJsonFile(uuid, student)).thenReturn(true);
		when(studentDao.removeFromJsonFile(uuid1)).thenReturn(true);
	}

	@Test
	public void testAddToJsonFile() throws IOException {
		Student student = new Student();
		student.setIdStudent(1);
		student.setName("Added");
		student.setSurname("User");
		student.setAge(20);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		studentList.add(student);

		verify(studentDao, never()).addToJsonFile(student);

		assertTrue("No se ha insertado el estudiante", studentList.size() > 2);
	}

	@Test
	public void testGetAllFromJson() throws IOException {
		List<Student> studentList = studentDao.getAllFromJson();

		verify(studentDao, never()).addToJsonFile(student);
		verify(studentDao, never()).addToJsonFile(student1);

		assertTrue("El tama�o de la lista es diferente que 2",
				studentList.size() == 2);
	}

	@Test
	public void testUpdateJsonFile() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		UUID uuid = student.getUUId();

		student.setIdStudent(1);
		student.setName("Updated");
		student.setSurname("User");
		student.setAge(20);
		LocalDate dateOfBirth = LocalDate.parse("21-02-1999", formatter);
		student.setDateOfBirth(dateOfBirth);

		Student student = studentList.stream()
				.filter(studentObj -> studentObj.getUUId().equals(uuid))
				.findFirst().orElse(null);

		verify(studentDao, never()).updateJsonFile(uuid, student);

		assertTrue("El estudiante no se ha encontrado",
				student.getName().equals("Updated"));
	}

	@Test
	public void testRemoveJsonFile() {
		List<Student> studentList = studentDao.getAllFromJson();
		UUID uuid = student1.getUUId();

		studentList.remove(student1);

		verify(studentDao, never()).removeFromJsonFile(uuid);

		Student removedStudent = studentList.stream()
				.filter(s -> s.getUUId().equals(uuid)).findFirst().orElse(null);

		assertTrue("El estudiante no se ha encontrado", removedStudent == null);
	}
}
