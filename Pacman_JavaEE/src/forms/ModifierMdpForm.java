package forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import beans.Utilisateur;
import dao.UtilisateurDao;

public final class ModifierMdpForm {
	private static final String CHAMP_OLD_PASS  = "ancienmdp";
	private static final String CHAMP_NEW_PASS   = "nouveaumdp";
	private static final String CHAMP_NEW_PASS_CONF       = "nouveaumdpconf";
	public static final String PARAM_PSEUDO = "pseudo";
    public static final String  ATT_SESSION_USER          = "sessionUtilisateur";
	private static final String ALGO_CHIFFREMENT = "SHA-256";
	
	private HttpServletRequest request;

	private String              resultat;
	private Map<String, String> erreurs      = new HashMap<String, String>();
	private UtilisateurDao      utilisateurDao;
	private Utilisateur user;

	public ModifierMdpForm(UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public String getResultat() {
		return resultat;
	}

	public Map<String, String> getErreurs() {
		return erreurs;
	}

	public void modifierMotDePasse( HttpServletRequest request ) {
		/* Récupération des champs du formulaire */
		this.request =request;
		String ancienMotDePasse = getValeurChamp( request, CHAMP_OLD_PASS );
		String nouveauMotDePasse = getValeurChamp( request, CHAMP_NEW_PASS );
		String nouveauMotDePasseConf = getValeurChamp(request, CHAMP_NEW_PASS_CONF);
		user = (Utilisateur) request.getSession().getAttribute(ATT_SESSION_USER);		

		/* Validation du champ mot de passe. */
		try {
			validationAncienMotDePasse( ancienMotDePasse );
		} catch ( FormValidationException e ) {
			setErreur( CHAMP_OLD_PASS, e.getMessage() );
		}
		traiterNouveauMotDePasse(nouveauMotDePasse,nouveauMotDePasseConf,user);
		
		
		

		/* Initialisation du résultat global de la validation. */
		if ( erreurs.isEmpty() ) {
			
			resultat = "Mot de Passe modifié";
		} else {
			resultat = "Échec de la modfication";
		}

	}


	/**
	 * Valide le mot de passe saisi.
	 */
	private void validationAncienMotDePasse( String motDePasse ) throws FormValidationException {
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
		if ( motDePasse != null ) {
			if ( !passwordEncryptor.checkPassword(motDePasse, user.getMotDePasse()) ) {
				throw new FormValidationException( "Le mot de passe est incorrect" );
			}
		} else {
			throw new FormValidationException( "Merci de saisir votre mot de passe." );
		}
	}
	
	
	private void validationNouveauMotDePasse( String motDePasse, String confirmation ) throws FormValidationException {
        if ( motDePasse != null && confirmation != null ) {
            if ( !motDePasse.equals( confirmation ) ) {
                throw new FormValidationException( "Les mots de passe entrés sont différents, merci de les saisir à nouveau." );
            } else if ( motDePasse.length() < 3 ) {
                throw new FormValidationException( "Les mots de passe doivent contenir au moins 3 caractères." );
            }
        } else {
            throw new FormValidationException( "Merci de saisir et confirmer votre mot de passe." );
        }
    }
	
	
	private void traiterNouveauMotDePasse( String motDePasse, String confirmation, Utilisateur utilisateur ) {
        try {
            validationNouveauMotDePasse( motDePasse, confirmation );
        } catch ( FormValidationException e ) {
            setErreur( CHAMP_NEW_PASS, e.getMessage() );
            setErreur( CHAMP_NEW_PASS_CONF, null );
        }

        ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
        passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
        passwordEncryptor.setPlainDigest( false );
        String motDePasseChiffre = passwordEncryptor.encryptPassword( motDePasse );
        utilisateurDao.modifier(motDePasseChiffre,utilisateur.getPseudo());
        utilisateur.setMotDePasse(motDePasseChiffre);
        HttpSession session = request.getSession();
        session.setAttribute(ATT_SESSION_USER, utilisateur);
    }

	/*
	 * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	 */
	private void setErreur( String champ, String message ) {
		erreurs.put( champ, message );
	}

	/*
	 * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	 * sinon.
	 */
	private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
		String valeur = request.getParameter( nomChamp );
		if ( valeur == null || valeur.trim().length() == 0 ) {
			return null;
		} else {
			return valeur;
		}
	}
}