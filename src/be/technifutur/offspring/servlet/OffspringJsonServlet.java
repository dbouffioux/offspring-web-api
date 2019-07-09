package be.technifutur.offspring.servlet;

import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import be.technifutur.offspring.beans.Activity;
import be.technifutur.offspring.beans.Event;
import be.technifutur.offspring.beans.Registration;
import be.technifutur.offspring.repository.DataRepository;
import be.technifutur.offspring.servlet.parameters.CreateActivityParameters;
import be.technifutur.offspring.servlet.parameters.CreateActivityParametersForUpdate;
import be.technifutur.offspring.servlet.parameters.CreateEventParameters;
import be.technifutur.offspring.servlet.parameters.CreateLoginParameters;
import be.technifutur.offspring.servlet.parameters.CreatePersonParameters;
import be.technifutur.offspring.servlet.parameters.CreateRegistrationParameters;

/**
 * Servlet implementation class OffspringJsonServlet
 */
@WebServlet("/json/*")
public class OffspringJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected DataRepository repository;

	@Override
	public void init(ServletConfig config) throws ServletException {

		super.init(config);

		try {
			// recover postredriver
			Class.forName("org.postgresql.Driver");

			// get db connection info
			String path = getServletContext().getRealPath("/WEB-INF/database.properties");

			// load configuration properties from file
			Properties properties = new Properties();
			try (InputStream in = new FileInputStream(path);) {
				properties.load(in);
			}

			String url = properties.getProperty("database.url");
			String user = properties.getProperty("database.user");
			String password = properties.getProperty("database.password");

			// instanciate repository
			repository = new DataRepository(url, user, password);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get pathinfo
		String pathInfo = request.getPathInfo();
		// set response content
		this.setHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		try {
			if (pathInfo.startsWith("/activity")) {

				// generate JSON
				List<Activity> activity = repository.findAllActivity();
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(activity);
				response.getWriter().write(json);
				
			} else if (pathInfo.startsWith("/event")) {
				
				String[] parts = pathInfo.split("/");
				String json = null;
				ObjectMapper mapper = new ObjectMapper();
				
				if(parts.length >= 3) {
					int id = Integer.parseInt(parts[2]);
					//Event event = repository.findEventById(id);
					json = mapper.writeValueAsString(event);
				}
				else {
					List<Event> events = repository.findAllEvent();
					json = mapper.writeValueAsString(events);
				}
				
				response.getWriter().write(json);
			} else if(pathInfo.startsWith("/registration")) {
				List<Registration> registrations = repository.findAllRegistration();
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(registrations);
				
				response.getWriter().write(json);
			}
		} catch (Exception e) {
			response.setStatus(404);
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get pathinfo
		ObjectMapper mapper = new ObjectMapper();
		String pathInfo = request.getPathInfo();
		String json = null;

		try {
			if (pathInfo.startsWith("/login")) {
				CreateLoginParameters parameters = mapper.readValue(request.getInputStream(),
						CreateLoginParameters.class);
				json = repository.checkLogin(parameters);
			} else if (pathInfo.startsWith("/register")) {
				CreatePersonParameters parameters = mapper.readValue(request.getInputStream(),
						CreatePersonParameters.class);
				json = repository.registerPerson(parameters);
			} else if (pathInfo.startsWith("/createActivity")) {
				CreateActivityParameters parameters = mapper.readValue(request.getInputStream(),
						CreateActivityParameters.class);
				json = mapper.writeValueAsString(repository.createNewActivity(parameters));
			} else if (pathInfo.startsWith("/createEvent")) {
				CreateEventParameters parameters = mapper.readValue(request.getInputStream(),
						CreateEventParameters.class);
				json = mapper.writeValueAsString(repository.createNewEvent(parameters));
			}else if (pathInfo.startsWith("/create-registration")) {
				CreateRegistrationParameters parameters = mapper.readValue(request.getInputStream(),
						CreateRegistrationParameters.class);
				json = repository.createNewRegistration(parameters);
			}
		} catch (Exception e) {
			response.setStatus(404);
			throw new ServletException(e);
		}

		// set response content
		this.setHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		boolean deleted = false;
		String[] parts = pathInfo.split("/");
		int id = Integer.parseInt(parts[2]);

		try {
			if (pathInfo.startsWith("/activity")) {
				deleted = this.repository.deleteActivity(id);
				
			} else if(pathInfo.startsWith("/event")) {
 				deleted = this.repository.deleteEvent(id);	
 			} else if(pathInfo.startsWith("/registration")) {
 				System.out.println(id);
 				deleted = this.repository.deleteRegistration(id);
 			}
		} catch (Exception e) {
			response.setStatus(404);
			throw new ServletException(e); 
		}

		// set response content
		this.setHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"deleted\":" + deleted + "}");
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		ObjectMapper mapper = new ObjectMapper();
		
		boolean updated = false;

		try {
			if (pathInfo.startsWith("/update-activity")) {
				CreateActivityParametersForUpdate parameters= mapper.readValue(request.getInputStream(),
						CreateActivityParametersForUpdate.class);
				updated = this.repository.updateActivity(parameters);
			}
		} catch (Exception e) {
			response.setStatus(404);
			throw new ServletException(e);
		}

		// set response content
		this.setHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"updated\":" + updated + "}");
	}

	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doOptions(request, response);
		this.setHeaders(response);
	}

	private void setHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
	}
}