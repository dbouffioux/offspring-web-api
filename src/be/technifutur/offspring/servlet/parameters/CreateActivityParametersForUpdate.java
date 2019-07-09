package be.technifutur.offspring.servlet.parameters;

public class CreateActivityParametersForUpdate {
	private Integer id;
	private String name;	
	private String dateDebut;
	private String heureDebut;
	private String dateFin;
	private String heureFin;
	private Integer creatorId;
	/**
	 * @return the creatorId
	 */
	public Integer getCreatorId() {
		return creatorId;
	}
	/**
	 * @param creatorId the creatorId to set
	 */
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}
	private Integer eventId;
	public CreateActivityParametersForUpdate() {

	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
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
	public String getDateDebut() {
		return dateDebut;
	}
	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(String dateDebut) {
		this.dateDebut = dateDebut;
	}
	/**
	 * @return the heureDebut
	 */
	public String getHeureDebut() {
		return heureDebut;
	}
	/**
	 * @param heureDebut the heureDebut to set
	 */
	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}
	/**
	 * @return the dateFin
	 */
	public String getDateFin() {
		return dateFin;
	}
	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}
	/**
	 * @return the heureFin
	 */
	public String getHeureFin() {
		return heureFin;
	}
	/**
	 * @param heureFin the heureFin to set
	 */
	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}
	/**
	 * @return the creatorId
	 */
//	public Integer getCreator() {
//		return creatorId;
//	}
//	/**
//	 * @param creatorId the creatorId to set
//	 */
//	public void setCreator(Integer creatorId) {
//		this.creatorId = creatorId;
//	}
	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return eventId;
	}
	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
}
