package dao;

import static dao.DAOUtilitaire.fermeturesSilencieuses;
import static dao.DAOUtilitaire.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Score;

public class ScoreDaoImpl implements ScoreDao{
	
	private static final String SQL_SELECT_PAR_PSEUDO = "SELECT id, pseudo, score FROM Score WHERE pseudo = ?";
    private static final String SQL_INSERT           = "INSERT INTO Score (pseudo, score) VALUES (?, ?)";
    private static final String SQL_SELECT        = "SELECT id, pseudo, score FROM Score ORDER BY score DESC";
    private static final String SQL_SELECT_SCORES_PSEUDO        = "SELECT id, pseudo, score FROM Score WHERE pseudo = ? ORDER BY score DESC";
    private DAOFactory          daoFactory;
    private static int rang;

    ScoreDaoImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }

	public void creer(Score score) throws DAOException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet valeursAutoGenerees = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, score.getPseudo(),score.getScore());
            int statut = preparedStatement.executeUpdate();
            if ( statut == 0 ) {
                throw new DAOException( "Echec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
            }
            valeursAutoGenerees = preparedStatement.getGeneratedKeys();
            if ( valeursAutoGenerees.next() ) {
                score.setId( valeursAutoGenerees.getLong( 1 ) );
            } else {
                throw new DAOException( "Echec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
        }
	}


	public Score trouver(String pseudo) throws DAOException {
		return trouver( SQL_SELECT_PAR_PSEUDO, pseudo );
	}
	
	private Score trouver( String sql, Object... objets ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Score score = null;
        
        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, sql, false, objets );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données retournée dans le ResultSet */
            if ( resultSet.next() ) {
            	score = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return score;
    }
	
	/*
     * Simple méthode utilitaire permettant de faire la correspondance (le
     * mapping) entre une ligne issue de la table des utilisateurs (un
     * ResultSet) et un bean Utilisateur.
     */
    private static Score map( ResultSet resultSet ) throws SQLException {
        Score score = new Score();
        
        score.setId(resultSet.getLong("id"));
        score.setScore(resultSet.getInt("score"));
        score.setPseudo(resultSet.getString("pseudo"));
        score.setRang(rang);
        return score;
    }

	@Override
	public List<Score> lister() throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Score> scores = new ArrayList<Score>();
        rang=1;

        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT );
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
            	scores.add( map(resultSet) );
            	rang++;
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return scores;
	}
	
	@Override
	public List<Score> lister(String pseudo) throws DAOException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Score> scores = new ArrayList<Score>();
        rang=1;      
        try {
            connection = daoFactory.getConnection();
            preparedStatement = connection.prepareStatement( SQL_SELECT_SCORES_PSEUDO );        
            preparedStatement.setString(1, pseudo);
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next() ) {
            	scores.add( map(resultSet) );
            	rang++;
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connection );
        }

        return scores;
	}
    
    


	

}
