package com.netmind.dao.contracts;

import java.io.IOException;

import com.netmind.common.model.Student;

public interface StudentDao {

	public boolean add(Student student) throws IOException;

	public boolean addToJsonFile(Student student) throws IOException;

	public boolean addToTxtFile(Student student) throws IOException;
}
