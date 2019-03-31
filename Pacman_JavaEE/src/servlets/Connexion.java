package servlets;

import java.io.IOException;

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

import beans.Utilisateur;
import dao.DAOFactory;
import dao.UtilisateurDao;
import forms.ConnexionForm;

@WebServlet( "/connexion" )
public class Connexion extends HttpServlet {
	public static final String  ATT_USER                  = "utilisateur";
    public static final String  ATT_FORM                  = "form";
    public static final String  CONF_DAO_FACTORY 		  = "daofactory";
    public static final String  ATT_SESSION_USER          = "sessionUtilisateur";
    public static final String  VUE                       = "/WEB-INF/connexion.jsp";
    public static final String  VUESUCCES                 = "/compte/utilisateur";

    
    private UtilisateurDao     utilisateurDao;
    
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
    }

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm(utilisateurDao);
        /* Traitement de la requête et récupération du bean en résultant */
        Utilisateur utilisateur = form.connecterUtilisateur( request );
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /*
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }


        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        if(form.getErreurs().isEmpty()) {
        	this.getServletContext().getRequestDispatcher( VUESUCCES ).forward( request, response );
        }
        else {
        	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
        
    }
    
}