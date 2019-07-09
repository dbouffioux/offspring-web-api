package be.technifutur.offspring.beans;

public class Registration {
	private int id;
	private int personId;
	private int activityId;
	
	public Registration(int id, int personId, int activityId) {
		this.id = id;
		this.personId = personId;
		this.activityId = activityId;
	}

	/**
	 * @return the personId
	 */
	public int getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(int personId) {
		this.personId = personId;
	}

	/**
	 * @return the activityId
	 */
	public int getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
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
	
}
