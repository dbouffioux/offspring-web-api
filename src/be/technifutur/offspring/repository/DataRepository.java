package be.technifutur.offspring.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import be.technifutur.offspring.beans.Event;
import be.technifutur.offspring.beans.Person;
import be.technifutur.offspring.servlet.parameters.CreateLoginParameters;

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

	public List<Event> findAllEvent() {
		List<Event> list = new ArrayList<>();
		String sql = "select * from event";

		try (Connection connection = createConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				Event activity = createEvent(resultSet);
				list.add(activity);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return list;
	}

	public List<Activity> findAllActivity() {
		List<Activity> list = new ArrayList<>();
		String sql = "SELECT name as activity_name, " + "id as activity_id, " + "\"startDate\" as start_date, "
				+ "\"startTime\" as start_time, " + "\"endDate\" as end_date, " + "\"endTime\" as end_time, "
				+ "creator_id as creator_id, " + "event_id as event_id " + "FROM activity";
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

	public List<Activity> findAllActivityByEventId(int idEv) {
		List<Activity> list = new ArrayList<>();
		String sql = "SELECT name as activity_name, " + "id as activity_id, " + "\"startDate\" as start_date, "
				+ "\"startTime\" as start_time, " + "\"endDate\" as end_date, " + "\"endTime\" as end_time, "
				+ "creator_id as creator_id, " + "event_id as event_id " + "FROM activity " + "WHERE event_id = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, idEv);
			try (ResultSet resultSet = statement.executeQuery();) {
				while (resultSet.next()) {
					Activity activity = createActivity(resultSet);
					list.add(activity);
				}
			}

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return list;
	}

	public Person findOnePersonById(int id) {
		
		Person person = null;
		String sql = "SELECT * FROM Person WHERE id = ? ";
		
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) 
		{
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					person = createPerson(resultSet);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return person;
	}
	
	private boolean findOnePersonByEmail(String email) {
		boolean result = false;
		String sql = "SELECT * FROM Person WHERE email = ? ";
		
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) 
		{
			statement.setString(1, email);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					result = true;
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return result;
	}
	
	private boolean findOnePersonByEmailAndPassword(String email, String password) {
		boolean result = false;
		String sql = "SELECT * FROM Person WHERE email = ? AND password = ? ";
		
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) 
		{
			statement.setString(1, email);
			statement.setString(2, password);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					result = true;
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return result;
	}
	
	public List<Person> findAllPersonByActivityId(int id){
		List<Person> personList = new ArrayList<>();
		String sql = "SELECT p.id, p.email, p.\"lastName\", p.\"firstName\", p.\"phoneNumber\" " + 
				"FROM person p " + 
				"INNER JOIN registration r ON r.id_person = p.id " + 
				"WHERE r.id_activity = ? ";
		
		try (
			Connection connection = createConnection();
			PreparedStatement statement = connection.prepareStatement(sql)) 
		{
			statement.setInt(1, id);
			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Person person = this.createPerson(resultSet);
					personList.add(person);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return personList;
	}
	
	private Person createPerson(ResultSet resultSet) throws SQLException {
		Person person = null;
		
		int id = resultSet.getInt("id");
		String firstName = resultSet.getString("firstName");
		String lastName = resultSet.getString("lastName");
		String email = resultSet.getString("email");
		String phoneNumber = resultSet.getString("phoneNumber");
		
		return new Person(id, firstName, lastName, email, phoneNumber);
	}

	private Event createEvent(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		String name = rs.getString("name");
		LocalDate startDate = LocalDate.parse(rs.getDate("StartDate").toString());
		LocalDate endDate = LocalDate.parse(rs.getDate("EndDate").toString());
		LocalTime startTime = LocalTime.parse(rs.getTime("StartTime").toString());
		LocalTime endTime = LocalTime.parse(rs.getTime("EndTime").toString());
		int creatorId = rs.getInt("creator_id");
		List<Activity> activities = findAllActivityByEventId(id);
		return new Event(id, name, startDate, endDate, startTime, endTime, creatorId, activities);
	}

	private Activity createActivity(ResultSet rs) throws SQLException {
		int id = rs.getInt("activity_id");
		String name = rs.getString("activity_name");
		LocalDate dateDebut = LocalDate.parse(rs.getDate("start_date").toString());
		LocalTime heureDebut = LocalTime.parse(rs.getTime("start_time").toString());
		LocalDate dateFin = LocalDate.parse(rs.getDate("end_date").toString());
		LocalTime heureFin = LocalTime.parse(rs.getTime("end_time").toString());
		int creatorId = rs.getInt("creator_id");
		int eventId = rs.getInt("event_id");

		Person person = this.findOnePersonById(creatorId);
		List<Person> participants = this.findAllPersonByActivityId(id);

		Activity activity = new Activity(id, name, dateDebut, heureDebut, dateFin, heureFin, person, eventId, participants);
		return activity;
	}

	public String checkLogin(CreateLoginParameters parameters) {

		String email = parameters.getEmail();
		String password = parameters.getPassword();
		String result = "{\"error\": null}";
		
		if (!this.findOnePersonByEmail(email)) {
			result = "{\"error\": \"User does not exist\"}";
		}
		else if (!this.findOnePersonByEmailAndPassword(email, password)) {
			result = "{\"error\": \"Password incorrect\"}";
		}
		
		return result;
	}
}
