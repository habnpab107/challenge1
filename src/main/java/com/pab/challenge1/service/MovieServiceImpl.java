/*
 * MovieServiceImpl
 * @author paul
 */
package com.pab.challenge1.service;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.pab.challenge1.builder.MovieBuilder;
import com.pab.challenge1.dao.MovieDAO;
import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.model.MovieBean;
import com.pab.challenge1.resourcehelper.MovieResourceHelper;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;
    private final MovieBuilder movieBuilder;

    @Inject
    public MovieServiceImpl(MovieDAO movieDAO, MovieBuilder movieBuilder) {
        this.movieDAO = movieDAO;
        this.movieBuilder = movieBuilder;
    }

    @Override
    public MovieResourceHelper addMovie(MovieResourceHelper movieResourceHelper) throws ServiceException, DatabaseException, SQLException {
        MovieBean movieBean = movieBuilder.buildMovieBean(movieResourceHelper);
        MovieBean newMovieBean = movieDAO.insert(movieBean);
        return movieBuilder.buildMovieResourceHelper(newMovieBean);
    }
//
//    @Override
//    public void updateMovieObject(MovieResourceHelper queueProcessorResourceHelper) throws ServiceException, DatabaseException, SQLException {
//        MovieBean movieBean = movieBuilder.buildMovieBean(queueProcessorResourceHelper);
//        movieDAO.update(movieBean);
//    }
//
    @Override
    public MovieResourceHelper getMovieById(Long movieId) throws ServiceException, DatabaseException {
        MovieBean bean = movieDAO.getMovieById(movieId);
        return movieBuilder.buildMovieResourceHelper(bean);
    }
    
    @Override
    public MovieResourceHelper getMovieByNameExact(String movieName) throws ServiceException, DatabaseException {
        MovieBean bean = movieDAO.getMovieByNameExact(movieName);
        return movieBuilder.buildMovieResourceHelper(bean);
    }
//    
//    @Override
//    public List<MovieResourceHelper> getMovieByName(String movieName) throws ServiceException, DatabaseException {
//        List<MovieBean> movieBeanList = movieDAO.getMovieByName(movieName);
//        List<MovieResourceHelper> movieResourceHelperList = movieBuilder.buildMovieResourceHelperList(movieBeanList);
//        return movieResourceHelperList;
//    }

    @Override
    public List<MovieResourceHelper> getAllMovies() throws ServiceException, DatabaseException, SQLException {
        List<MovieBean> movieBeanList = movieDAO.getAll();
        List<MovieResourceHelper> movieResourceHelperList = movieBuilder.buildMovieResourceHelperList(movieBeanList);
        return movieResourceHelperList;
    }
}