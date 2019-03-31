package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import beans.Score;
import beans.Utilisateur;
import dao.DAOFactory;
import dao.ScoreDao;


@WebServlet("/compte/utilisateur")
public class CompteUtilisateur extends HttpServlet {
	    public static final String VUE          = "/WEB-INF/restreint/compteUtilisateur.jsp";
	    public static final String PARAM_PSEUDO = "pseudo";
	    public static final String  ATT_SESSION_USER          = "sessionUtilisateur";
	    public static final String ATT_REQUEST_SCORES = "scores";
	    public static final String SESSION_SCORES  = "scores";
	    public static final String CONF_DAO_FACTORY      = "daofactory";
	    public static final String  COOKIE_DERNIERE_CONNEXION = "derniereConnexion";
	    public static final String  ATT_INTERVALLE_CONNEXIONS = "intervalleConnexions";
	    public static final String  FORMAT_DATE               = "dd/MM/yyyy HH:mm:ss";
	    private ScoreDao        scoreDao;
	    
	    public void init() throws ServletException {
			/* Récupération d'une instance de nos DAO Client et Commande */
			this.scoreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
					.getScoreDao();
						
		}
	    
	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {	          	
	    	afficherScore(request);
	    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }
	    
	    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {    	
	    	afficherScore(request);    
	    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }
	    
	    public void afficherScore(HttpServletRequest request) {
	    	HttpSession session = request.getSession();
	        Utilisateur utilisateur = (Utilisateur) session.getAttribute(ATT_SESSION_USER);
	        String pseudo = utilisateur.getPseudo();            	        			
	        List<Score> listeScores = scoreDao.lister(pseudo);
			Map<Long, Score> mapScores = new HashMap<Long, Score>();
			for ( Score score : listeScores ) {
				mapScores.put( score.getId(), score );
			}
			request.setAttribute(ATT_REQUEST_SCORES, mapScores);
	    }


}