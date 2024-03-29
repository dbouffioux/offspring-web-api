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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import be.technifutur.offspring.beans.Activity;
import be.technifutur.offspring.beans.Event;
import be.technifutur.offspring.beans.Person;
import be.technifutur.offspring.beans.Registration;
import be.technifutur.offspring.servlet.parameters.CreateActivityParameters;
import be.technifutur.offspring.servlet.parameters.CreateActivityParametersForUpdate;
import be.technifutur.offspring.servlet.parameters.CreateEventParameters;
import be.technifutur.offspring.servlet.parameters.CreateLoginParameters;
import be.technifutur.offspring.servlet.parameters.CreatePersonParameters;
import be.technifutur.offspring.servlet.parameters.CreateRegistrationParameters;

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
		String sql = "SELECT name, " + "id, " + "\"startDate\", " + "\"startTime\", " + "\"endDate\", "
				+ "\"endTime\" , " + "creator_id, " + "event_id " + "FROM activity";
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
		String sql = "SELECT name, " + "id, " + "\"startDate\", " + "\"startTime\", " + "\"endDate\", "
				+ "\"endTime\", " + "creator_id , " + "event_id " + "FROM activity " + "WHERE event_id = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(true);
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

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					person = createPersonFromResultset(resultSet);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return person;
	}

	private Person findOnePersonByEmail(String email) {
		Person result = null;
		String sql = "SELECT * FROM Person WHERE email = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(true);
			statement.setString(1, email);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					result = createPersonFromResultset(resultSet);
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

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(true);
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

	public List<Person> findAllPersonByActivityId(int id) {
		List<Person> personList = new ArrayList<>();
		String sql = "SELECT p.id, p.email, p.\"lastName\", p.\"firstName\", p.\"phoneNumber\" " + "FROM person p "
				+ "INNER JOIN registration r ON r.id_person = p.id " + "WHERE r.id_activity = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Person person = this.createPersonFromResultset(resultSet);
					personList.add(person);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return personList;
	}

	private Person createPersonFromResultset(ResultSet resultSet) throws SQLException {
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

		int id = rs.getInt("id");
		String name = rs.getString("name");
		LocalDate dateDebut = LocalDate.parse(rs.getDate("startDate").toString());
		LocalTime heureDebut = LocalTime.parse(rs.getTime("startTime").toString());
		LocalDate dateFin = LocalDate.parse(rs.getDate("endDate").toString());
		LocalTime heureFin = LocalTime.parse(rs.getTime("endTime").toString());
		int creatorId = rs.getInt("creator_id");
		int eventId = rs.getInt("event_id");

		Person person = this.findOnePersonById(creatorId);
		List<Person> participants = this.findAllPersonByActivityId(id);

		Activity activity = new Activity(id, name, dateDebut, heureDebut, dateFin, heureFin, person, eventId,
				participants);
		return activity;
	}

	public String checkLogin(CreateLoginParameters parameters) throws JsonProcessingException {

		String email = parameters.getEmail();
		String password = parameters.getPassword();
		String result = null;
		Person person = this.findOnePersonByEmail(email);

		if (person == null) {
			result = "{\"error\": \"User does not exist\"}";
		} else if (!this.findOnePersonByEmailAndPassword(email, password)) {
			result = "{\"error\": \"Password incorrect\"}";
		} else {
			ObjectMapper Obj = new ObjectMapper();
			result = Obj.writeValueAsString(person);
		}

		return result;
	}

	public String registerPerson(CreatePersonParameters parameters) throws JsonProcessingException {

		String email = parameters.getEmail();
		String result = "{\"error\": null, \"person\": null}";

		if (this.findOnePersonByEmail(email) != null) {
			result = "{\"error\": \"User does exist with this email address\"}";
		} else {
			Person person = this.insertPerson(parameters);
			ObjectMapper Obj = new ObjectMapper();
			result = Obj.writeValueAsString(person);
		}

		return result;
	}

	private Person insertPerson(CreatePersonParameters parameters) {

		Person person = null;
		String sql = "INSERT INTO person(\"firstName\", \"lastName\", email, \"phoneNumber\", password) "
				+ "VALUES (?, ?, ?, ?, ?)";
		Integer id = null;

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			connection.setAutoCommit(true);
			statement.setString(1, parameters.getFirstName());
			statement.setString(2, parameters.getLastName());
			statement.setString(3, parameters.getEmail());
			statement.setString(4, parameters.getPhoneNumber());
			statement.setString(5, parameters.getPassword());
			statement.executeUpdate();

			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
			person = this.findOnePersonById(id);

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return person;
	}

	public Activity createNewActivity(CreateActivityParameters parameters) {

		Integer id = null;

		if (parameters.getName() != null && !parameters.getName().isBlank() && parameters.getDateDebut() != null
				&& parameters.getHeureDebut() != null && parameters.getDateFin() != null
				&& parameters.getHeureFin() != null && parameters.getCreator() != null
				&& parameters.getEventId() != null) {
			try (Connection connection = createConnection();) {
				connection.setAutoCommit(true);
				id = storeActivityAndReturnGeneratedId(connection, parameters.getName(), parameters.getDateDebut(),
						parameters.getHeureDebut(), parameters.getDateFin(), parameters.getHeureFin(),
						parameters.getCreator(), parameters.getEventId());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		return findActivityById(id);
	}

	public Event createNewEvent(CreateEventParameters parameters) {
		Integer id = null;
		if (parameters.getName() != null && !parameters.getName().isBlank() && parameters.getDateDebut() != null
				&& parameters.getHeureDebut() != null && parameters.getDateFin() != null
				&& parameters.getHeureFin() != null && parameters.getCreatorId() != null) {
			try (Connection connection = createConnection()) {
				connection.setAutoCommit(true);

				id = storeEventAndReturnGeneratedId(connection, parameters.getName(), parameters.getDateDebut(),
						parameters.getHeureDebut(), parameters.getDateFin(), parameters.getHeureFin(),
						parameters.getCreatorId());
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}

		return findEventById(id);
	}

	protected Integer storeActivityAndReturnGeneratedId(Connection connection, String name, String dateDebut,
			String heureDebut, String dateFin, String heureFin, Integer creator, Integer eventId) throws SQLException {

		Integer id = null;

		try (PreparedStatement statement = connection.prepareStatement("INSERT INTO activity(\r\n"
				+ "	name, \"startDate\", \"endDate\", creator_id, event_id, \"endTime\", \"startTime\")\r\n"
				+ "	VALUES (?, ?, ?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, name);
			statement.setDate(2, Date.valueOf(dateDebut));
			statement.setDate(3, Date.valueOf(dateFin));
			statement.setInt(4, creator);
			statement.setInt(5, eventId);
			statement.setTime(6, Time.valueOf(heureFin));
			statement.setTime(7, Time.valueOf(heureDebut));

			statement.executeUpdate();
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		}

		return id;
	}

	protected Integer storeEventAndReturnGeneratedId(Connection connection, String name, String dateDebut,
			String heureDebut, String dateFin, String heureFin, Integer creatorId) throws SQLException {

		Integer id = null;

		try (PreparedStatement statement = connection.prepareStatement(
				"INSERT INTO event(" + "	name, \"startDate\", \"endDate\", creator_id, \"endTime\", \"startTime\") "
						+ "	VALUES (?, ?, ?, ?, ?, ?);",
				Statement.RETURN_GENERATED_KEYS)) {

			statement.setString(1, name);
			statement.setDate(2, Date.valueOf(dateDebut));
			statement.setDate(3, Date.valueOf(dateFin));
			statement.setInt(4, creatorId);

			statement.setTime(5, Time.valueOf(heureFin));
			statement.setTime(6, Time.valueOf(heureDebut));

			statement.executeUpdate();
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(4);
				}
			}
		}

		return id;
	}

	public Activity findActivityById(Integer id) {
		Activity activity = null;

		String sql = "SELECT * FROM activity WHERE id = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			connection.setAutoCommit(true);
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					activity = createActivity(resultSet);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return activity;
	}

	public boolean deleteActivity(int id) {

		boolean deleted = false;
		String sql = "DELETE FROM registration WHERE id_activity = ?";

		try (Connection connection = createConnection(); PreparedStatement query = connection.prepareStatement(sql);) {
			connection.setAutoCommit(false);
			query.setInt(1, id);
			query.executeUpdate();

			sql = "DELETE FROM activity WHERE id = ?";
			try (PreparedStatement queryNext = connection.prepareStatement(sql)) {

				queryNext.setInt(1, id);
				queryNext.executeUpdate();
				connection.commit();

				int updatedRows = queryNext.getUpdateCount();
				deleted = updatedRows > 0;
			}

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return deleted;
	}

	public boolean updateActivity(CreateActivityParametersForUpdate parameters) {
		boolean updated = false;

		String sql = "UPDATE activity"
				+ "	SET name=?, \"startDate\"=?, \"endDate\"=?, event_id=?, \"endTime\"=?, \"startTime\"=?"
				+ "	WHERE id = ?";

		try (Connection connection = createConnection(); PreparedStatement query = connection.prepareStatement(sql);) {
			query.setString(1, parameters.getName());
			query.setDate(2, Date.valueOf(parameters.getDateDebut()));
			query.setDate(3, Date.valueOf(parameters.getDateFin()));
			query.setInt(4, parameters.getEventId());
			query.setTime(5, Time.valueOf(parameters.getHeureFin()));
			query.setTime(6, Time.valueOf(parameters.getHeureDebut()));
			query.setInt(7, parameters.getId());

			query.executeUpdate();

			int updatedRows = query.getUpdateCount();
			updated = updatedRows > 0;

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return updated;
	}

	public boolean deleteEvent(int id) {

		boolean deleted = false;

		try (Connection connection = createConnection()) {
			List<Activity> activities = this.findAllActivityByEventId(id);

			for (Activity activity : activities) {
				this.deleteActivity(activity.getId());
			}
			// delete event at the end
			String sql = "DELETE FROM event WHERE id = ?";
			try (PreparedStatement query = connection.prepareStatement(sql)) {
				query.setInt(1, id);
				query.executeUpdate();
				int updatedRows = query.getUpdateCount();
				deleted = updatedRows > 0;
			}

		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return deleted;
	}

	public Event findEventById(int id) {
		
		Event event = null;
		String sql = "SELECT * FROM Event WHERE id = ?";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					event = this.createEvent(resultSet);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return event;

	}

	public String createNewRegistration(CreateRegistrationParameters parameters) throws JsonProcessingException {
		Registration registration = null;
		Integer id = null;
		String result = "";

		if (parameters.getPersonId() != null && parameters.getActivityId() != null) {
			try (Connection connection = createConnection()) {
				if (!registrationExist(parameters.getPersonId(), parameters.getActivityId())) {
					if (checkAvailabilityOfPerson(parameters)) {
						id = storeRegistrationAndReturnGeneratedId(connection, parameters);
						ObjectMapper Obj = new ObjectMapper();
						result = Obj.writeValueAsString(findRegistrationById(id));
					}else {
						result = "{\"error\":\"Registration in conflict whith another one\"}";
					}
					
				} else {
					result = "{\"error\":\"Registration exist\"}";
				}
			} catch (SQLException e) {
				// TODO: handle exception
			}
		}

		return result;
	}

	protected Boolean registrationExist(int personId, int activityId) {
		Boolean exist = false;

		String sql = "SELECT * " + "FROM registration " + "WHERE ? = id_person AND id_activity = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, personId);
			statement.setInt(2, activityId);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					exist = true;
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}


		return exist;
	}

	protected Integer storeRegistrationAndReturnGeneratedId(Connection connection,
			CreateRegistrationParameters parameters) throws SQLException {
		Integer id = null;

		String sql = "INSERT INTO registration(id_person, id_activity) VALUES (?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			statement.setInt(1, parameters.getPersonId());
			statement.setInt(2, parameters.getActivityId());

			statement.executeUpdate();
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					id = rs.getInt(1);
				}
			}
		}

		return id;
	}

	protected Registration findRegistrationById(Integer id) {
		Registration registration = null;
		String sql = "SELECT * FROM registration WHERE id = ? ";

		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					registration = createRegistration(resultSet);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

		return registration;
	}

	protected Registration createRegistration(ResultSet rs) throws SQLException {
		Registration registration = null;
		System.out.println(rs.getInt("id"));
		registration = new Registration(rs.getInt("id"), rs.getInt("id_person"), rs.getInt("id_activity"));

		return registration;
	}

	public Boolean deleteRegistration(Integer id) {
		Boolean deleted = false;

		String sql = "DELETE FROM registration WHERE id = ?";
		try (Connection connection = createConnection(); PreparedStatement query = connection.prepareStatement(sql)) {
			
			query.setInt(1, id);
			query.executeUpdate();

			int updatedRows = query.getUpdateCount();
			deleted = updatedRows > 0;

			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return deleted;
	}

	public List<Registration> findAllRegistration() {
		List<Registration> registrations = new ArrayList<Registration>();
		String sql = "SELECT * FROM registration";
		
		
		try (Connection connection = createConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {
			while (resultSet.next()) {
				Registration registration = createRegistration(resultSet);
				registrations.add(registration);
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		return registrations;
	}
	
	public List<Registration> findAllRegistrationByPersonId(Integer id) {
		List<Registration> registrations = new ArrayList<Registration>();
		String sql = "SELECT * FROM registration WHERE id_person = ?";
		
		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					Registration registration = createRegistration(resultSet);
					registrations.add(registration);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		return registrations;
	}
	
	protected Boolean checkAvailabilityOfPerson(CreateRegistrationParameters parameters) {
		Boolean available = true;
		List<Registration> registrations = findAllRegistrationByPersonId(parameters.getPersonId());
		Activity newActivity = findActivityById(parameters.getActivityId());
		
		for (Registration registration : registrations) {
			Activity activity = findActivityById(registration.getActivityId());
			LocalDateTime start = LocalDateTime.of(activity.getDateDebut(), activity.getHeureDebut());
			LocalDateTime end = LocalDateTime.of(activity.getDateFin(), activity.getHeureFin());
			LocalDateTime newStart = LocalDateTime.of(newActivity.getDateDebut(), newActivity.getHeureDebut());
			LocalDateTime newEnd = LocalDateTime.of(newActivity.getDateFin(), newActivity.getHeureFin());
			
			if (!checkDateOfActivity(start, newStart, end, newEnd)) {
				available = false;
			}
			

		}
		
		
		return available;
	}
	
	protected boolean checkDateOfActivity(LocalDateTime start, LocalDateTime newStart, LocalDateTime end, LocalDateTime newEnd) {
		boolean ok = true;
		
		if (start.isAfter(newStart) && start.isBefore(end)) {
			ok = false;
		}else if (end.isAfter(newStart) && end.isBefore(newEnd)) {
			ok = false;
		}else if (newStart.isAfter(start) && newStart.isBefore(end)) {
			ok = false;
		}else if (newEnd.isAfter(start) && newEnd.isBefore(end)) {
			ok = false;
		}
		
		return ok;
	}

	public List<Event> findRegistrationsByPersonId(int id) {
		
		List<Event> list = null;
		int firstId;
		String sql = "SELECT a.\"id\" as \"activity_id\", a.event_id as \"event_id\" " +
				"FROM Activity a " + 
				"JOIN Event e ON a.event_id = e.id " + 
				"WHERE a.id IN (SELECT r.id_activity " + 
				"			FROM registration r  " + 
				"			WHERE id_person = ?) " + 
				"ORDER BY a.event_id";
		
		try (Connection connection = createConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			
			statement.setInt(1, id);

			try (ResultSet resultSet = statement.executeQuery()) {
			
				Integer previousId = null;
				Integer currentId = null;
				Event event = null;
				Activity activity = null;
				List<Activity> listActivity = new ArrayList<Activity>();
				
				while (resultSet.next()) {

					if (previousId.equals(null)) { 
						previousId = resultSet.getInt("event_id");
					} 
					currentId = resultSet.getInt("event_id");
					
					if (currentId != previousId) {
						event = this.findEventById(resultSet.getInt("event_id"));
						event.setListActivity(listActivity);
						listActivity = new ArrayList<Activity>();
						previousId = resultSet.getInt("event_id");
					}
					
					activity = this.findActivityById(resultSet.getInt("activity_id"));
					listActivity.add(activity);
				}
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		
		return list;
	}
}
