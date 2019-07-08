package be.technifutur.offspring.servlet.parameters;

public class CreateRegistrationParameters {
	private Integer activityId;
	private Integer personId;
	
	public CreateRegistrationParameters() {
		
	}

	/**
	 * @return the activityId
	 */
	public Integer getActivityId() {
		return activityId;
	}

	/**
	 * @param activityId the activityId to set
	 */
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	/**
	 * @return the personId
	 */
	public Integer getPersonId() {
		return personId;
	}

	/**
	 * @param personId the personId to set
	 */
	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	
	
}
