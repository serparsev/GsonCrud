package com.netmind.business.contracts;

import java.io.IOException;

import com.netmind.common.model.Student;

public interface StudentBl {

	public boolean add(Student student) throws IOException;

}
