package com.vh.athena;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DBConnection {

	@SuppressWarnings("finally")
	public static Connection createConnection() throws Exception {
		Connection con = null;
		try {
			Class.forName(Constants.dbClass);
			con = DriverManager.getConnection(Constants.dbUrl,
					Constants.dbUser, Constants.dbPwd);
		} catch (Exception e) {
			throw e;
		} finally {
			return con;
		}
	}

	public static boolean checkLogin(String uname, String name)
			throws Exception {
		boolean isUserAvailable = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM users WHERE username = '" + uname
					+ "'";
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				isUserAvailable = true;
			}

			if (isUserAvailable == false) {
				String query2 = "INSERT into users(name, username) values('"
						+ name + "'," + "'" + uname + "')";
				stmt.executeUpdate(query2);
				isUserAvailable = true;
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isUserAvailable;
	}

	public static boolean storeLocation(String uname, double longitude,
			double latitude, String address, String track_type,
			String track_em_id, String country, String locality)
			throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into track_locations(fk_username, longitude, latitude, address, track_type, track_em_id, country, locality) values('"
					+ uname
					+ "',"
					+ "'"
					+ longitude
					+ "',"
					+ "'"
					+ latitude
					+ "',"
					+ "'"
					+ address
					+ "',"
					+ "'"
					+ track_type
					+ "',"
					+ "'"
					+ track_em_id
					+ "',"
					+ "'"
					+ country
					+ "',"
					+ "'"
					+ locality + "')";
			stmt.executeUpdate(query);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static boolean addContact(String uname, String contact_name,
			String email, String country, String phone) throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into contacts(fk_username, name, email, fk_country, phone) values('"
					+ uname
					+ "',"
					+ "'"
					+ contact_name
					+ "',"
					+ "'"
					+ email
					+ "'," + "'" + country + "'," + "'" + phone + "')";
			stmt.executeUpdate(query);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static ArrayList<ContactData> getContact(String uname)
			throws Exception {
		ArrayList<ContactData> ContactList = new ArrayList<ContactData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM contacts WHERE fk_username = '"
					+ uname + "'";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				ContactData toReturn = new ContactData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "name":
						toReturn.setName(rs.getString(i));
						break;
					case "email":
						toReturn.setEmail(rs.getString(i));
						break;
					case "fk_country":
						toReturn.setCountry(rs.getString(i));
						break;
					case "phone":
						toReturn.setPhone(rs.getString(i));
						break;
					}
				}
				ContactList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return ContactList;
	}

	// NOT TESTED YET
	public static boolean deleteContact(String uname, String email, String phone)
			throws Exception {
		Boolean isDeleteSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "DELETE FROM contacts WHERE fk_username = '" + uname
					+ "' AND email = '" + email + "' AND phone = '" + phone
					+ "'";
			stmt.executeUpdate(query);
			isDeleteSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isDeleteSuccessful;
	}

	public static boolean createEMID(String uname, int emCount, String address,
			String country, String latitude, String longitude, String locality, String timezone)
			throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into emergency_records(fk_username, user_em_no, address, country, latitude, longitude, locality, timezone) values('"
					+ uname
					+ "',"
					+ "'"
					+ emCount
					+ "',"
					+ "'"
					+ address
					+ "',"
					+ "'"
					+ country
					+ "',"
					+ "'"
					+ latitude
					+ "',"
					+ "'"
					+ longitude + "'," + "'" + locality + "'," + "'" + timezone + "')";
			stmt.executeUpdate(query);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static int getEmergencyCount(String uname) throws Exception {
		Connection dbConn = null;
		int emergencyCount = 0;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "UPDATE users SET em_times = em_times + 1 WHERE username = '"
					+ uname + "'";
			stmt.executeUpdate(query);
			String query2 = "SELECT em_times FROM users WHERE username = '"
					+ uname + "'";
			ResultSet rs = stmt.executeQuery(query2);
			while (rs.next()) {
				emergencyCount = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return emergencyCount;
	}
	
	public static int getEmergencyTZ(String username, int emID) throws Exception {
		Connection dbConn = null;
		int emergencyTZ = 0;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query2 = "SELECT timezone FROM emergency_records WHERE fk_username = '"
					+ username + "' AND user_em_no = '" + emID + "'";
			ResultSet rs = stmt.executeQuery(query2);
			while (rs.next()) {
				emergencyTZ = rs.getInt(1);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return emergencyTZ;
	}

	public static ArrayList<TrackData> getTrack(String uname, int emID)
			throws Exception {
		ArrayList<TrackData> TrackList = new ArrayList<TrackData>();
		Connection dbConn = null;
		int tzOffset = 0;
		long epoc = 0;
		try {
			try {
				tzOffset = getEmergencyTZ(uname, emID);
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM track_locations WHERE fk_username = '"
					+ uname + "' AND track_em_id = '" + emID + "'";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				TrackData toReturn = new TrackData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "address":
						toReturn.setAddress(rs.getString(i));
						break;
					case "country":
						toReturn.setCountry(rs.getString(i));
						break;
					case "track_dt":
						epoc = rs.getTimestamp(i).getTime() + tzOffset;
						toReturn.setDateTime(String.valueOf(new Timestamp(epoc)));
						break;
					case "latitude":
						toReturn.setLatitude(rs.getDouble(i));
						break;
					case "longitude":
						toReturn.setLongitude(rs.getDouble(i));
						break;
					case "locality":
						toReturn.setLocality(rs.getString(i));
						break;
					}
				}
				TrackList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return TrackList;
	}

	public static boolean endEM(String uname, int emID, String emStatus)
			throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;

		java.util.Date date = new java.util.Date();

		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			int trackCount = getTrack(uname, emID).size();
			String query2 = "UPDATE emergency_records SET em_status = '"
					+ emStatus + "', num_of_track = " + "'" + trackCount
					+ "', end_dt = '" + new Timestamp(date.getTime())
					+ "' WHERE fk_username = '" + uname + "' AND user_em_no ="
					+ "'" + emID + "'";
			stmt.executeUpdate(query2);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static boolean changeEMStatus(String uname, int emID, String emStatus)
			throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;

		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query2 = "UPDATE emergency_records SET em_status = '"
					+ emStatus + "' WHERE fk_username = '" + uname
					+ "' AND user_em_no =" + "'" + emID + "'";
			stmt.executeUpdate(query2);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static ArrayList<EmergencyData> getEmergency(String uname)
			throws Exception {
		ArrayList<EmergencyData> EmergencyList = new ArrayList<EmergencyData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM emergency_records WHERE fk_username = '"
					+ uname + "'";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				EmergencyData toReturn = new EmergencyData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "start_dt":
						toReturn.setDtStartTime(rs.getTimestamp(i));
						break;
					case "end_dt":
						toReturn.setDtEndTime(rs.getTimestamp(i));
						break;
					case "num_of_track":
						toReturn.setNumOfTrack(rs.getInt(i));
						break;
					case "user_em_no":
						toReturn.setEmID(rs.getInt(i));
						break;
					case "em_status":
						toReturn.setStatus(rs.getString(i));
						break;
					case "address":
						toReturn.setAddress(rs.getString(i));
						break;
					case "country":
						toReturn.setCountry(rs.getString(i));
						break;
					case "latitude":
						toReturn.setLatitude(rs.getString(i));
						break;
					case "longitude":
						toReturn.setLongitude(rs.getString(i));
						break;
					case "locality":
						toReturn.setLocality(rs.getString(i));
						break;
					case "timezone":
						toReturn.setTzOffset(rs.getInt(i));
					}
				}
				EmergencyList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return EmergencyList;
	}

	public static ArrayList<EmergencyData> getEmergencyAll() throws Exception {
		ArrayList<EmergencyData> EmergencyList = new ArrayList<EmergencyData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM emergency_records WHERE em_status = 'Emergency' AND start_dt >= DATE_ADD(NOW(), INTERVAL -6 MONTH)";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				EmergencyData toReturn = new EmergencyData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "start_dt":
						toReturn.setDtStartTime(rs.getTimestamp(i));
						break;
					case "end_dt":
						toReturn.setDtEndTime(rs.getTimestamp(i));
						break;
					case "num_of_track":
						toReturn.setNumOfTrack(rs.getInt(i));
						break;
					case "user_em_no":
						toReturn.setEmID(rs.getInt(i));
						break;
					case "em_status":
						toReturn.setStatus(rs.getString(i));
						break;
					case "address":
						toReturn.setAddress(rs.getString(i));
						break;
					case "country":
						toReturn.setCountry(rs.getString(i));
						break;
					case "latitude":
						toReturn.setLatitude(rs.getString(i));
						break;
					case "longitude":
						toReturn.setLongitude(rs.getString(i));
						break;
					case "locality":
						toReturn.setLocality(rs.getString(i));
						break;
					case "timezone":
						toReturn.setTzOffset(rs.getInt(i));
					}
				}
				EmergencyList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return EmergencyList;
	}

	public static boolean addDZ(String uname, String title, String addinfo,
			String latitude, String longitude) throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "INSERT into user_danger_zone(fk_username, title, additional_info, latitude, longitude) values('"
					+ uname
					+ "',"
					+ "'"
					+ title
					+ "',"
					+ "'"
					+ addinfo
					+ "',"
					+ "'" + latitude + "'," + "'" + longitude + "')";
			stmt.executeUpdate(query);
			isStoreSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}

	public static boolean deleteDZ(String uname, String latitude,
			String longitude) throws Exception {
		Boolean isDeleteSuccessful = false;
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "DELETE FROM user_danger_zone WHERE fk_username = '"
					+ uname + "' AND latitude = '" + latitude
					+ "' AND longitude = '" + longitude + "'";
			stmt.executeUpdate(query);
			isDeleteSuccessful = true;
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isDeleteSuccessful;
	}

	public static ArrayList<DangerZoneData> getDZ(String uname)
			throws Exception {
		ArrayList<DangerZoneData> DZList = new ArrayList<DangerZoneData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM user_danger_zone WHERE fk_username = '"
					+ uname + "' AND num_report < 11 AND record_dt >= DATE_ADD(NOW(), INTERVAL -6 MONTH)";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				DangerZoneData toReturn = new DangerZoneData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "dz_id":
						toReturn.setDz_id(String.valueOf(rs.getInt(i)));
						break;
					case "record_dt":
						toReturn.setDateTime(rs.getTimestamp(i).toString());
						break;
					case "title":
						toReturn.setTitle(rs.getString(i));
						break;
					case "additional_info":
						toReturn.setAddinfo(rs.getString(i));
						break;
					case "latitude":
						toReturn.setLatitude(String.valueOf(rs.getDouble(i)));
						break;
					case "longitude":
						toReturn.setLongitude(String.valueOf(rs.getDouble(i)));
						break;
					}
				}
				DZList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return DZList;
	}

	public static ArrayList<DangerZoneData> getDZAll() throws Exception {
		ArrayList<DangerZoneData> DZList = new ArrayList<DangerZoneData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();

			String query = "SELECT * FROM user_danger_zone WHERE num_report < 11 AND record_dt >= DATE_ADD(NOW(), INTERVAL -6 MONTH)";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				DangerZoneData toReturn = new DangerZoneData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "dz_id":
						toReturn.setDz_id(String.valueOf(rs.getInt(i)));
						break;
					case "record_dt":
						toReturn.setDateTime(rs.getTimestamp(i).toString());
						break;
					case "title":
						toReturn.setTitle(rs.getString(i));
						break;
					case "additional_info":
						toReturn.setAddinfo(rs.getString(i));
						break;
					case "latitude":
						toReturn.setLatitude(String.valueOf(rs.getString(i)));
						break;
					case "longitude":
						toReturn.setLongitude(String.valueOf(rs.getString(i)));
						break;
					}
				}
				DZList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return DZList;
	}

	public static boolean reportDZ(String uname, String dz_id, String type,
			String detail) throws Exception {
		Boolean isStoreSuccessful = false;
		Connection dbConn = null;
		Boolean isReportExist = false;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			
			String query1 = "SELECT * FROM danger_zone_report WHERE fk_username = '" + uname
					+ "' AND fk_dz_id = '" + dz_id + "'";
			ResultSet rs = stmt.executeQuery(query1);

			while (rs.next()) {
				isReportExist = true;
			}
			
			if(!isReportExist){
			String query = "UPDATE user_danger_zone SET num_report = num_report + 1 WHERE dz_id = '"
						+ dz_id + "'";
			stmt.executeUpdate(query);
				
			String query2 = "INSERT into danger_zone_report(fk_dz_id, fk_username, report_type, report_detail) values('"
					+ dz_id+ "',"+ "'"+ uname+ "',"+ "'"+ type+ "',"+ "'" + detail + "')";
			stmt.executeUpdate(query2);
			isStoreSuccessful = true;
			}
			
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return isStoreSuccessful;
	}
	
	public static ArrayList<SpecialZoneData> getSZ() throws Exception {
		ArrayList<SpecialZoneData> SZList = new ArrayList<SpecialZoneData>();
		Connection dbConn = null;
		try {
			try {
				dbConn = DBConnection.createConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Statement stmt = dbConn.createStatement();
			String query = "SELECT * FROM user_danger_zone WHERE record_dt >= DATE_ADD(NOW(), INTERVAL -6 MONTH)";
			ResultSet rs = stmt.executeQuery(query);

			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {

				System.out.println("entered while loop");
				int numColumns = rsmd.getColumnCount();
				SpecialZoneData toReturn = new SpecialZoneData();

				for (int i = 1; i < numColumns + 1; i++) {
					System.out.println("entered for loop");
					System.out.println(numColumns);
					String column_name = rsmd.getColumnName(i);
					System.out.println("column name: " + column_name);

					switch (column_name) {
					case "dz_id":
						toReturn.setDz_id(String.valueOf(rs.getInt(i)));
						break;
					case "record_dt":
						toReturn.setDateTime(rs.getTimestamp(i).toString());
						break;
					case "title":
						toReturn.setDz_title(rs.getString(i));
						break;
					case "additional_info":
						toReturn.setDz_info(rs.getString(i));
						break;
					case "latitude":
						toReturn.setLatitude(String.valueOf(rs.getString(i)));
						break;
					case "longitude":
						toReturn.setLongitude(String.valueOf(rs.getString(i)));
						break;
					}
				}
				SZList.add(toReturn);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			if (dbConn != null) {
				dbConn.close();
			}
			throw e;
		} finally {
			if (dbConn != null) {
				dbConn.close();
			}
		}
		return SZList;
	}
	
}
