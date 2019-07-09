package be.technifutur.offspring.data.repository.test;

import org.junit.jupiter.api.Test;

import be.technifutur.offspring.beans.Person;
import be.technifutur.offspring.repository.DataRepository;

import static org.junit.jupiter.api.Assertions.*;

public class _02_TestFindOnePersonById {
	
	public DataRepository repository;

	public void initRepository() {
		try {
			// recover postredriver
			Class.forName("org.postgresql.Driver");
	
			String url = "jdbc:postgresql://localhost:5432/offspring";
			String user = "postgres";
			String password = "postgres";
	
			// instanciate repository
			this.repository = new DataRepository(url, user, password);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void testExistingPerson() {
		this.initRepository();

		Person expected = new Person(6,"Damien","Bouffioux","damien.bouffioux@gmail.com","0479308008");
		Person actual = this.repository.findOnePersonById(6);
		
		assertNotNull(actual);
		assertEquals(expected.getId(), expected.getId());
		assertEquals(expected.getEmail(), actual.getEmail());
		assertEquals(expected.getFirstName(), actual.getFirstName());
		assertEquals(expected.getLastName(), actual.getLastName());
		assertEquals(expected.getPassword(), actual.getPassword());
		assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
	}
	
	@Test
	void testNonExistingPerson() {
		this.initRepository();

		Person actual = this.repository.findOnePersonById(0);

		assertNull(actual);
	}
}
