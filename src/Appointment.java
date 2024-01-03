import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Appointment {
	private Connection con;
	private Scanner sc;

	public Appointment(Connection con, Scanner sc) {
		this.con = con;
		this.sc = sc;
	}

	public void bookAppointment(Patient pat, Doctor doc) {
		System.out.print("Enter Patient ID : ");
		int patient_id = sc.nextInt();
		System.out.print("Enter Doctor Id :");
		int doctor_id = sc.nextInt();

		// Check if patient and doctor exist
		if (pat.getPatientById(patient_id) && doc.getDoctorById(doctor_id)) {
			System.out.print("Enter appointment Date (YYYY-MM-DD) : ");
			String appointment_date = sc.next();
			System.out.print("Enter appointment Time (HH:mm) : ");
			String appointment_time = sc.next();

			if (checkDoctorAvailable(doctor_id, appointment_date)) {
				String appQuery = "INSERT INTO  appointment(patient_id, doctor_id, appointment_date, appointment_time) VALUES (?,?,?,?) ";
				try (PreparedStatement st = con.prepareStatement(appQuery)) {
					st.setInt(1, patient_id);
					st.setInt(2, doctor_id);
					st.setString(3, appointment_date);
					st.setString(4, appointment_time);
					int rowsAffected = st.executeUpdate();
					if (rowsAffected > 0) {
						System.out.println("Appointment Booked");
					} else {
						System.out.println("Failed to book appointment.");
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
				}
			} else {
				System.out.println("Doctor is not available on the specified date.");
			}
		} else {
			System.out.println("Either doctor or patient does not exist.");
		}
	}

	private boolean checkDoctorAvailable(int doctorID, String appointment_date) {
		String query = "Select count(*) from appointment where doctor_id = ? and  appointment_date = ? ";
		try (PreparedStatement st = con.prepareStatement(query)) {
			st.setInt(1, doctorID);
			st.setString(2, appointment_date);
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				if (count == 0) {
					return true;
				} else {
					return false;
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
}
