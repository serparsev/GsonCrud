package com.netmind.business.contracts;

import java.io.IOException;

import com.netmind.model.Student;

public interface StudentBl {
	public boolean addJsonFile(Student student) throws IOException;

	public boolean add(Student student) throws IOException;

}
