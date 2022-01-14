/* 
 * MovieDAO
 * @author paul
 */
package com.pab.challenge1.dao;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.model.MovieBean;
import java.io.Serializable;
import java.util.List;

public interface MovieDAO extends CommonDBInterface {
    <T extends Serializable> List<T> getMoviesByActorId(Long movieId) throws DatabaseException;
    MovieBean insert(MovieBean object) throws DatabaseException;
    MovieBean getMovieById(Long movieId) throws DatabaseException;
    MovieBean getMovieByNameExact(String movieName) throws DatabaseException;
}