package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Score;
import dao.DAOFactory;
import dao.ScoreDao;
import dao.UtilisateurDao;


/**
 * Servlet implementation class ProfilPublic
 */
@WebServlet("/profilPublic")
public class ProfilPublic extends HttpServlet {
	    public static final String VUE          = "/WEB-INF/profilPublic.jsp";
	    public static final String PARAM_PSEUDO = "idScore";
	    public static final String ATT_REQUEST_SCORES = "scores";
	    public static final String ATT_REQUEST_PSEUDO = "pseudo";
	    public static final String SESSION_SCORES  = "scores";
	    public static final String CONF_DAO_FACTORY      = "daofactory";
	    
	    private ScoreDao        scoreDao;

		public void init() throws ServletException {
			/* Récupération d'une instance de nos DAO Client et Commande */
			this.scoreDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
					.getScoreDao();
						
		}
	
	    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {	          	
	    	/* Récupération du paramètre */
	    	
	        String idScore = getValeurParametre( request, PARAM_PSEUDO );
	        Long id = Long.parseLong( idScore );
	        
	        HttpSession session = request.getSession();
	        Map<Long, Score> commandes = (HashMap<Long, Score>) session.getAttribute( SESSION_SCORES );
	        String pseudo = commandes.get(id).getPseudo();
	               	        
	        List<Score> listeScores = scoreDao.lister(pseudo);
			Map<Long, Score> mapScores = new HashMap<Long, Score>();
			for ( Score score : listeScores ) {
				mapScores.put( score.getId(), score );
			}
			request.setAttribute(ATT_REQUEST_SCORES, mapScores);
			request.setAttribute(ATT_REQUEST_PSEUDO, pseudo);
	        
	        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
	    }
	    /*
	     * Méthode utilitaire qui retourne null si un paramètre est vide, et son
	     * contenu sinon.
	     */
	    private static String getValeurParametre( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }
	    
}
