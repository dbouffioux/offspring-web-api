package be.technifutur.offspring;

import be.technifutur.offspring.beans.Activity;
import be.technifutur.offspring.repository.DataRepository;

import java.util.List;
import java.util.Properties;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Properties properties = new Properties();
		
		String url = properties.getProperty("database.url");
		String user = properties.getProperty("database.user");
		String password = properties.getProperty("database.password");
		DataRepository repository = new DataRepository(url, user, password);
		List<Activity> list = repository.findAllActivity();
		
		for (Activity activity : list) {
			System.out.println(activity.name);
		}
	}

}
