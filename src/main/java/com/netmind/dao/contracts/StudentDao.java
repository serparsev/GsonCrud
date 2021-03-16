package com.netmind.dao.contracts;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import com.netmind.common.model.Student;

public interface StudentDao {

	boolean addToTxtFile(Student student) throws IOException;

	boolean addToJsonFile(Student student) throws IOException;

	List<Student> getAllFromTxt();

	List<Student> getAllFromJson();

	boolean updateTxtFile(UUID uuid, Student updatedStudent) throws IOException;

	boolean updateJsonFile(UUID uuid, Student updatedStudent);

	boolean removeFromTxtFile(UUID uuid) throws IOException;

	boolean removeFromJsonFile(UUID uuid);
}