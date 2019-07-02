package be.technifutur.offspring.beans;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class Activity {
	private int id;
	private String name;	
	private LocalDate dateDebut;
	private LocalTime heureDebut;
	private LocalDate dateFin;
	private LocalTime heureFin;
	private Person creator;
	private int eventId;
	List<Person> participants;
	
	public Activity(int id, String name, LocalDate dateDebut, LocalTime heureDebut, LocalDate dateFin, LocalTime heureFin, Person creator,
			int eventId, List<Person> participants) {
		super();
		this.id = id;
		this.name = name;
		this.dateDebut = dateDebut;
		this.heureDebut = heureDebut;
		this.dateFin = dateFin;
		this.heureFin = heureFin;
		this.creator = creator;
		this.eventId = eventId;
		this.participants = participants;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the participants
	 */
	public List<Person> getParticipants() {
		return participants;
	}
	/**
	 * @param participants the participants to set
	 */
	public void setParticipants(List<Person> participants) {
		this.participants = participants;
	}
	/**
	 * @param id the id to set
	 */
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}
	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}
	/**
	 * @return the heureDebut
	 */
	public LocalTime getHeureDebut() {
		return heureDebut;
	}
	/**
	 * @param heureDebut the heureDebut to set
	 */
	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}
	/**
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}
	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}
	/**
	 * @return the heureFin
	 */
	public LocalTime getHeureFin() {
		return heureFin;
	}
	/**
	 * @param heureFin the heureFin to set
	 */
	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}
	/**
	 * @return the creatorId
	 */
	public Person getCreator() {
		return creator;
	}
	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreator(Person creator) {
		this.creator = creator;
	}
	/**
	 * @return the eventId
	 */
	public int getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

}
