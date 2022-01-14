/*
 * MovieService
 * @author paul
 */
package com.pab.challenge1.service;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.resourcehelper.MovieResourceHelper;
import java.sql.SQLException;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface MovieService {

    MovieResourceHelper addMovie(MovieResourceHelper movieResourceHelper) throws ServiceException, DatabaseException, SQLException;
    
    MovieResourceHelper getMovieById(Long movieId) throws ServiceException, DatabaseException;
    
    MovieResourceHelper getMovieByNameExact(String movieName) throws ServiceException, DatabaseException;
//    List<MovieResourceHelper> getMovieByName(String movieName) throws ServiceException, DatabaseException;

    List<MovieResourceHelper> getAllMovies() throws ServiceException, DatabaseException, SQLException;
}
