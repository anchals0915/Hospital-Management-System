
/*
 * Author : Anchal Singh
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
	private Connection con;
	private Scanner sc;

	public Patient(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}

	// Add Patient
	public void addPatient() {
		System.out.print("Enter Patient First Name : ");
		String firstname = sc.next();

		System.out.print("Enter Patient Last Name : ");
		String lastname = sc.next();

		System.out.print("Enter Patient Age : ");
		int age = sc.nextInt();

		System.out.print("Enter Patient Gender : ");
		String gender = sc.next();

		System.out.print("Enter Patient Medical History : ");
		String medicalhistory = sc.nextLine();
		sc.nextLine();
		System.out.print("Enter Patient Contact Number : ");
		long contactno = sc.nextLong();

		String query = "Insert into patient (firstname,lastname,age,gender,medicalhistory,contactno) VALUES (? , ? , ? , ? ,? , ? )  ";
		try (PreparedStatement st = con.prepareStatement(query)) {
			st.setString(1, firstname);
			st.setString(2, lastname);
			st.setInt(3, age);
			st.setString(4, gender);
			st.setString(5, medicalhistory);
			st.setLong(6, contactno);

			int affectedRows = st.executeUpdate();
			if (affectedRows > 0) {
				System.out.println("Patient Added Sucessfully !!");
			} else {
				System.out.println("Failed to Add Patient !!");
			}
		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}

	}

	// View Patient
	public void viewPatient() {
		String query = "SELECT patient_id, CONCAT(firstname, ' ', lastname) AS Full_name, age, gender, contactno ,medicalhistory FROM patient";
		try (PreparedStatement st = con.prepareStatement(query)) {
			ResultSet rs = st.executeQuery();
			System.out.println("Patients : ");
			System.out.printf(
					"+------------+---------------------+-----+--------+-------------+-----------------------------%n");
			System.out.printf("| patient_id | Full_name           | age | gender | contactno   | medicalhistory  %n");
			System.out.printf(
					"+------------+---------------------+-----+--------+-------------+-----------------------------%n");

			while (rs.next()) {
				int patientId = rs.getInt("patient_id");
				String fullName = rs.getString("Full_name"); // Retrieve Full_name directly
				int age = rs.getInt("age");
				String gender = rs.getString("gender");
				String medicalHistory = rs.getString("medicalhistory");
				Long contactNo = rs.getLong("contactno");

				System.out.printf("| %-10d | %-19s | %-3d | %-6s | %-10s  | %-40s %n", patientId, fullName, age, gender,
						contactNo, medicalHistory);
				System.out.printf(
						"+------------+---------------------+-----+--------+-------------+-----------------------------%n");
			}

		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	public boolean getPatientById(int id) {
		String query = "SELECT * FROM patient WHERE patient_id= ?";

		try (PreparedStatement st = con.prepareStatement(query)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error while checking patient existence: " + e.getMessage());
			return false;
		}
	}

}
