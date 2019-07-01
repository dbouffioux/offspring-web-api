package be.technifutur.offspring.beans;

public class Activity {
	public String name;

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

	public Activity(String name) {
		super();
		this.name = name;
	}
	
	
}
