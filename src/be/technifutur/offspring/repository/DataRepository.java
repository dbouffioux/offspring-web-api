package be.technifutur.offspring.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import be.technifutur.offspring.beans.Activity;

public class DataRepository {
	private String url;
	private String user;
	private String password;

	public DataRepository(String url, String user, String password) {
		super();
		this.url = url;
		this.user = user;
		this.password = password;
	}

	protected Connection createConnection() throws SQLException {
		return DriverManager.getConnection(url, user, password);
	}

	public List<Activity> findAllActivity() {
		List<Activity> list = new ArrayList<>();
		String sql = "SELECT name as activity_name, "
				+ "id as activity_id, "
				+ "\"startDate\" as start_date, "
				+ "\"startTime\" as start_time, "
				+ "\"endDate\" as end_date, "
				+ "\"endTime\" as end_time, "
				+ "creator_id as creatorId, "
				+ "event_id as event_id " 
		+ "FROM activity";
		try (Connection connection = createConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				Activity activity = createActivity(resultSet);
				list.add(activity);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return list;

	}

	private Activity createActivity(ResultSet rs) throws SQLException {
		System.out.println("creation");
		int id = rs.getInt("activity_id");
		String name = rs.getString("activity_name");
		Date dateDebut = rs.getDate("start_date");
		Time heureDebut = rs.getTime("start_time");
		Date dateFin = rs.getDate("end_date");
		Time heureFin = rs.getTime("end_time");
		int creatorId = rs.getInt("creatorId");
		int eventId = rs.getInt("eventId");
		
		Activity activity = new Activity(id, name, dateDebut, heureDebut, dateFin, heureFin, creatorId, eventId);
		return activity;
	}	

}
