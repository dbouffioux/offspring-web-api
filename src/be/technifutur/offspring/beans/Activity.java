package be.technifutur.offspring.beans;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Activity {
	private int id;
	private String name;	
	private LocalDate dateDebut;
	private LocalTime heureDebut;
	private LocalDate dateFin;
	private LocalTime heureFin;
	private int creatorId;
	private int eventId;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
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
	public int getCreatorId() {
		return creatorId;
	}
	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
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
	public Activity(int id, String name, LocalDate dateDebut2, LocalTime heureDebut2, LocalDate dateFin2, LocalTime heureFin2, int creatorId,
			int eventId) {
		super();
		this.id = id;
		this.name = name;
		this.dateDebut = dateDebut2;
		this.heureDebut = heureDebut2;
		this.dateFin = dateFin2;
		this.heureFin = heureFin2;
		this.creatorId = creatorId;
		this.eventId = eventId;
	}
	
	
	
	
	
	
}
