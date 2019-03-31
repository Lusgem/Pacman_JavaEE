package dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static dao.DAOUtilitaire.*;
import beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

    private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, pseudo, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";
    private static final String SQL_INSERT           = "INSERT INTO Utilisateur (email, pseudo, mot_de_passe, date_inscription) VALUES (?, ?, ?, NOW())";
    private static final String SQL_UPDATE           = "UPDATE Utilisateur SET mot_de_passe = ? WHERE pseudo = ?";

    private DAOFactory          daoFactory;

    UtilisateurDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver( String email ) throws DAOException {
        return trouver( SQL_SELECT_PAR_EMAIL, email );
    }

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public void creer( Utilisateur utilisateur ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getPseudo(), utilisateur.getMotDePasse());
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Echec de la crÃ©ation de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
    }

    /*
     * Méthode générique utilisée pour retourner un utilisateur depuis la base
     * de données, correspondant à la requete SQL donnée prenant en parametres
     * les objets passés en argument.
     */
    private Utilisateur trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            connexion = daoFactory.getConnection();
           
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }
    
    @Override
	public void modifier(String mdp, String pseudo) throws DAOException {
    	 Connection connexion = null;
         PreparedStatement preparedStatement = null;

         try {
             connexion = daoFactory.getConnection();
             preparedStatement = initialisationRequetePreparee( connexion, SQL_UPDATE, false, mdp,pseudo);
             preparedStatement.executeUpdate();
             
         } catch ( SQLException e ) {
             throw new DAOException( e );
         } finally {
             fermeturesSilencieuses(preparedStatement, connexion );
         }

		
	}

    /*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Utilisateur map( ResultSet resultSet ) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setId( resultSet.getLong( "id" ) );
        utilisateur.setEmail( resultSet.getString( "email" ) );
        utilisateur.setMotDePasse( resultSet.getString( "mot_de_passe" ) );
        utilisateur.setPseudo( resultSet.getString( "pseudo" ) );
        utilisateur.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
        return utilisateur;
    }

	

}