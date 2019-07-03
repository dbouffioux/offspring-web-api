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
import be.technifutur.offspring.repository.DataRepository;
import be.technifutur.offspring.servlet.parameters.CreateLoginParameters;
import be.technifutur.offspring.servlet.parameters.CreatePersonParameters;


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
			try (
					InputStream in = new FileInputStream(path);
			) {
				properties.load(in);
			}
			
			String url = properties.getProperty("database.url");
			String user = properties.getProperty("database.user");
			String password = properties.getProperty("database.password");
			
			// instanciate repository
			repository = new DataRepository(url, user, password);
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// get pathinfo
		String pathInfo = request.getPathInfo();
		// set response content
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
				List<Event> events = repository.findAllEvent();
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(events);
				
				
				response.getWriter().write(json);
			}
		}
		catch(Exception e) {
			response.setStatus(404);
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get pathinfo
		ObjectMapper mapper= new ObjectMapper();
		String pathInfo = request.getPathInfo();
		String json = null;

		try {
			if (pathInfo.startsWith("/login")) {
				CreateLoginParameters parameters
					= mapper.readValue(request.getInputStream(), CreateLoginParameters.class);
				json = repository.checkLogin(parameters);
			}
			else if (pathInfo.startsWith("/register")) {
				CreatePersonParameters parameters 
					= mapper.readValue(request.getInputStream(), CreatePersonParameters.class);
				json = repository.registerPerson(parameters);
			}
		}
		catch(Exception e) {
			response.setStatus(404);
			throw new ServletException(e);
		}
		
		// set response content
		this.setHeaders(response);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json);
	}
	
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doOptions(request, response);
		this.setHeaders(response);
	}
	
	private void setHeaders( HttpServletResponse response ) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Methods", "*");
		response.addHeader("Access-Control-Allow-Headers", "*");
	}
}