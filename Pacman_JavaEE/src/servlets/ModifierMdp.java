package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DAOFactory;
import dao.UtilisateurDao;
import forms.ModifierMdpForm;

@WebServlet( "/compte/modifier" )
public class ModifierMdp extends HttpServlet {
	public static final String VUE          = "/WEB-INF/restreint/modifierMdp.jsp";
	public static final String  ATT_FORM                  = "form";
    public static final String CONF_DAO_FACTORY      = "daofactory";
    private UtilisateurDao        utilisateurDao;
    
    public void init() throws ServletException {
		/* Récupération d'une instance de nos DAO Client et Commande */
		this.utilisateurDao = ( (DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) )
				.getUtilisateurDao();
					
	}

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	    	
    	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
        
        ModifierMdpForm form = new ModifierMdpForm(utilisateurDao);
        form.modifierMotDePasse(request);
        
        request.setAttribute( ATT_FORM, form );

        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }
}