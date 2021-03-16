package com.netmind.dao.contracts;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.netmind.common.model.Student;

public interface StudentDao {
	boolean add(Student student) throws IOException;

	boolean addToJsonFile(Student student) throws IOException;

	boolean addToTxtFile(Student student) throws IOException;

	List<Student> getAllFromJson();

	boolean updateJsonFile(UUID uuid, Student updatedStudent);

	boolean removeFromJsonFile(UUID uuid);
}
