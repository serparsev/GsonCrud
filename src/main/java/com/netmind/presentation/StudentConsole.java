package com.netmind.presentation;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.netmind.business.StudentBlImpl;
import com.netmind.business.contracts.StudentBl;
import com.netmind.common.model.EnumStudent;
import com.netmind.common.model.Student;
import com.netmind.dao.StudentDaoImplJson;
import com.netmind.dao.StudentDaoImplTxt;
import com.netmind.dao.contracts.StudentDao;

public class StudentConsole {

	@SuppressWarnings("static-access")
	public static void selectOperation() {
		StudentDao studentDaoJson = new StudentDaoImplJson();
		StudentBl studentBlJson = new StudentBlImpl(studentDaoJson);

		StudentDao studentDaoTxt = new StudentDaoImplTxt();
		StudentBl studentBlTxt = new StudentBlImpl(studentDaoTxt);

		Scanner scanner = new Scanner(System.in);
		EnumStudent enumStudent = null;
		int option;

		do {
			showPrincipalMenu();
			// The nextInt() method does not deal with the EOL token,
			// while nextLine() does.
			option = Integer.parseInt(scanner.nextLine());
			enumStudent = EnumStudent.fromValue(option);

			switch (enumStudent) {
			case ADD_STUDENT:
				Student student = new Student();
				addNewStudent(student, scanner);
				try {
					studentBlTxt.add(student);
					studentBlJson.add(student);
				} catch (IOException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
				break;
			case EXIT:
				System.out.println("Good Bye!!");
				break;
			default:
				break;
			}
		} while (option != enumStudent.EXIT.value());
		scanner.close();
	}

	private static void showPrincipalMenu() {
		System.out.println("¿Qué opción quiere seleccionar?");
		System.out.println("1.Agregar un nuevo estudiante");
		System.out.println("2.Calcular el estudiante con mayor edad");
		System.out.println("3.Salir del programa");
	}

	private static void addNewStudent(Student student, Scanner scanner) {
		System.out.println("1.Agregar un nuevo estudiante");
		System.out.println("Introduce nombre: ");
		student.setName(scanner.nextLine());

		System.out.println("Introduce apellidos: ");
		student.setSurname(scanner.nextLine());

		System.out.println("Introduce Fecha de nacimiento (dd-MM-yyyy): ");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate dateOfBirth = LocalDate.parse(scanner.nextLine(), formatter);

		student.setDateOfBirth(dateOfBirth);
	}
}
