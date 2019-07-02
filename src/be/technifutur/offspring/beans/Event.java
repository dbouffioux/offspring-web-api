package be.technifutur.offspring.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class Event {
	
	private int id;
	private String name;
	private LocalDateTime start;
	private LocalDateTime end;
	private int creatorId;
	private List<Activity> listActivity;
	
	public Event(int id, String name, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, int creatorId, List<Activity> listActivity) {
		super();
		this.id = id;
		this.name = name;
		this.start = LocalDateTime.of(startDate, startTime);
		this.end = LocalDateTime.of(endDate, endTime);
		this.creatorId = creatorId;
		this.listActivity = listActivity;
	}
	
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
	 * @return the start
	 */
	public LocalDateTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(LocalDateTime start) {
		this.start = start;
	}
	/**
	 * @return the end
	 */
	public LocalDateTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(LocalDateTime end) {
		this.end = end;
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

	public List<Activity> getListActivity() {
		return listActivity;
	}

	public void setListActivity(List<Activity> listActivity) {
		this.listActivity = listActivity;
	}

}
