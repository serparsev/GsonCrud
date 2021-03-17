package com.netmind.dao.contracts;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.netmind.common.model.Student;

public interface StudentDao {

	boolean addToFile(Student student) throws IOException;

	List<Student> getFromFile();

	boolean updateFile(UUID uuid, Student updatedStudent);

	boolean removeFromFile(UUID uuid);
}