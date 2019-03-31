package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.DAOFactory;
import dao.ScoreDao;
import dao.UtilisateurDao;

/**
 * Servlet implementation class Home
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	    public static final String VUE          = "/WEB-INF/home.jsp";
	    
	    
	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {	          	
	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }

}
