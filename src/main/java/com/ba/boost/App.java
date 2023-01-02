package com.ba.boost;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ba.boost.model.StudentClass;
import com.ba.boost.model.Telephone;

public class App {
	/*
	 * hello db
	 */

	private Connection connection = DatabaseConnection.getInstance().getCon();
	private List<StudentClass> studentClasses = new ArrayList<>();
	private List<Telephone> numbers = new ArrayList<>();

	public static void main(String[] args) {

		if (args.length != 1) {
			System.err.println("Wrong number of arguments. Expected <<<1>>> actual <<<" + args.length + ">>>");
			System.exit(100);
		}

		DatabaseConnection.getInstance().setPropertiesFile(args[0]);

		App main = new App();
		main.retrieveStudentClass();
		main.listStudentClass();

	}

	private void listStudentClass() {

		for (int i = 0; i < this.studentClasses.size(); i++) {
			System.out.println(this.studentClasses.get(i));
			System.out.println();
		}

	}

	private void retrieveStudentClass() {

		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM student_classes");
			StudentClass stu = new StudentClass();
			while (rs.next()) {
				stu.setOid(rs.getLong(1));
				stu.setNationalId(rs.getLong(2));
				stu.setFirstName(rs.getString(3));
				stu.setLastName(rs.getString(4));
				stu.setCity(rs.getString(5));
				stu.setStreet(rs.getString(6));
				stu.setCountry(rs.getString(7));
				stu.setCountryCode(rs.getInt(8));
				stu.setCourseId(rs.getInt(9));
				stu.setCourseName(rs.getString(10));
				stu.setAttandenceYear(rs.getInt(11));
				stu.setGrade(rs.getDouble(12));
				this.studentClasses.add(stu);
			}
			for (StudentClass s : studentClasses) {
				rs = stmt.executeQuery("SELECT * FROM telephone_numbers WHERE student_oid=" + stu.getOid());
				while (rs.next()) {
					Telephone tel = new Telephone(rs.getLong(1), rs.getLong(2), rs.getString(3));
					numbers.add(tel);
				}
				s.setNumbers(numbers);
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
