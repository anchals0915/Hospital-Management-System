
/*
 * Author : Anchal Singh
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
	private Connection con;

	public Doctor(Connection con) {
		this.con = con;
	}

	// View Patient
	public void viewDoctor() {
		String query = "SELECT doctor_id, CONCAT(firstname, ' ', lastname) AS Full_name, speciality, qualification, gender FROM doctor";

		try (PreparedStatement st = con.prepareStatement(query)) {
			ResultSet rs = st.executeQuery();
			System.out.println("Doctors : ");
			System.out.printf(
					"+-----------+------------------------------+-------------------------------------------------------------------+--------------------------------------------------------------------------------------------------+--------+%n");
			System.out.printf(
					"| doctor_id | Full_name                    | speciality                                                        | qualification                                                                                    | gender |%n");
			System.out.printf(
					"+-----------+------------------------------+-------------------------------------------------------------------+--------------------------------------------------------------------------------------------------+--------+%n");

			while (rs.next()) {
				int doctorId = rs.getInt("doctor_id");
				String fullName = rs.getString("Full_name"); // Retrieve Full_name directly
				String speciality = rs.getString("speciality");
				String qualification = rs.getString("qualification");
				String gender = rs.getString("gender");

				System.out.printf("| %-9d | %-28s | %-65s | %-96s | %-6s |%n", doctorId, fullName, speciality,
						qualification, gender);
				System.out.printf(
						"+-----------+------------------------------+-------------------------------------------------------------------+--------------------------------------------------------------------------------------------------+--------+%n");

			}

		} catch (SQLException e) {
			System.out.print(e.getMessage());
		}
	}

	// Check doctor
	public boolean getDoctorById(int id) {
		String query = "SELECT * FROM doctor WHERE doctor_id= ?";

		try (PreparedStatement st = con.prepareStatement(query)) {
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Error while checking doctor existence: " + e.getMessage());
			return false;
		}
	}

}
