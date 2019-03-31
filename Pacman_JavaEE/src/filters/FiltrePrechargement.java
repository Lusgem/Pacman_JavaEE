package filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import beans.Score;
import dao.DAOFactory;
import dao.ScoreDao;
import dao.UtilisateurDao;



public class FiltrePrechargement implements Filter {
	public static final String CONF_DAO_FACTORY      = "daofactory";
	public static final String ATT_SESSION_SCORES   = "scores";
	public static final String ATT_SESSION_UTILISATEURS = "utilisateurs";

	private UtilisateurDao         utilisateurDao;
	private ScoreDao        scoreDao;

	public void init( FilterConfig config ) throws ServletException {
		/* Récupération d'une instance de nos DAO Client et Commande */
		this.utilisateurDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
		this.scoreDao = ( (DAOFactory) config.getServletContext().getAttribute( CONF_DAO_FACTORY ) )
				.getScoreDao();
	}

	public void doFilter( ServletRequest req, ServletResponse res, FilterChain chain ) throws IOException,
	ServletException {
		/* Cast de l'objet request */
		HttpServletRequest request = (HttpServletRequest) req;

		/* Récupération de la session depuis la requête */
		HttpSession session = request.getSession();

		/*
		 * Si la map des scores n'existe pas en session, alors l'utilisateur se
		 * connecte pour la première fois et nous devons précharger en session
		 * les infos contenues dans la BDD.
		 */
		//if ( session.getAttribute( ATT_SESSION_SCORES ) == null ) {
			/*
			 * Récupération de la liste des scores existants, et enregistrement
			 * en session
			 */
			List<Score> listeScores = scoreDao.lister();
			Map<Long, Score> mapScores = new HashMap<Long, Score>();
			for ( Score score : listeScores ) {
				mapScores.put( score.getId(), score );
			}
			session.setAttribute( ATT_SESSION_SCORES, mapScores );

		//}

		/*
		 * De même pour la map des commandes
		 */
		/*if ( session.getAttribute( ATT_SESSION_COMMANDES ) == null ) {

            List<Commande> listeCommandes = commandeDao.lister();
            Map<Long, Commande> mapCommandes = new HashMap<Long, Commande>();
            for ( Commande commande : listeCommandes ) {
                mapCommandes.put( commande.getId(), commande );
            }
            session.setAttribute( ATT_SESSION_COMMANDES, mapCommandes );
        }*/

		/* Pour terminer, poursuite de la requête en cours */
		chain.doFilter( request, res );
	}

	public void destroy() {
	}
}
