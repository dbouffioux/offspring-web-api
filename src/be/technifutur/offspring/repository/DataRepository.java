package be.technifutur.offspring.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		String sql = "SELECT name as activity_name " + "FROM activity";
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
		String name = rs.getString("activity_name");
		Activity activity = new Activity(name);
		return activity;
	}

	

}
