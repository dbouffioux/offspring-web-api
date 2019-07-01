package be.technifutur.offspring.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class OffspringJsonServlet
 */
@WebServlet("/json/*")
public class OffspringJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
			//repository = new DvdRentalJdbcRepositoryImpl(url, user, password);
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}