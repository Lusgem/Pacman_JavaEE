package dao;

import java.util.List;

import beans.Score;


public interface ScoreDao {
	
	
	void creer( Score score ) throws DAOException;

    Score trouver( String pseudo ) throws DAOException;
    
    List<Score> lister() throws DAOException ;
    
    List<Score> lister(String pseudo) throws DAOException;
}
