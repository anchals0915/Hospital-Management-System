
/*
 * Author : Anchal Singh
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

	private static final String URL = "jdbc:mysql://localhost:3306/hospital";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException ce) {
			System.out.println(ce.getMessage());
		}

		Scanner sc = new Scanner(System.in);

		try (Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
			Patient patient = new Patient(con, sc);
			Doctor doctor = new Doctor(con);
			Appointment app = new Appointment(con, sc);

			System.out.println("\033[1;36m*********************************");
			System.out.println("*  \033[1;33mWelcome to Hospital Management  \033[1;36m*");
			System.out.println("*********************************\033[0m");

			do {
				System.out.println("\n");
				System.out.println("\033[1;32m1. Add Patient");
				System.out.println("2. View Patient ");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment ");
				System.out.println("5. Exit\033[0m");
				System.out.print("Enter an option : ");
				int choice = sc.nextInt();

				switch (choice) {
				case 1 -> {
					patient.addPatient();
					System.out.println();
				}
				case 2 -> {
					patient.viewPatient();
					System.out.println();
				}
				case 3 -> {
					doctor.viewDoctor();
					System.out.println();
				}
				case 4 -> {
					app.bookAppointment(patient, doctor);
					System.out.println();
				}
				case 5 -> {
					try {
						exit();
					} catch (InterruptedException e) {
						System.out.print(e.getMessage());
					}
					sc.close(); // closing the resource
					System.exit(0);
				}
				default -> System.out.println("Invalid Choice. Try again.");
				}

			} while (true);
		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} finally {
			sc.close(); // closing the resource
		}
	}

	private static void exit() throws InterruptedException {
		System.out.print("Existing System");
		int i = 5;
		while (i != 0) {
			System.out.print(".");
			Thread.sleep(450);
			i--;
		}
		System.out.println();
		System.out.print("Thank you for using Hospital Management System!!");
	}

}