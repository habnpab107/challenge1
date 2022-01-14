/*
 * MovieDAOImpl
 * @author paul
 */
package com.pab.challenge1.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.datasource.MySQLDataSource;
import com.pab.challenge1.model.MovieBean;
import javax.naming.NamingException;

public class MovieDAOImpl implements MovieDAO {

    Logger LOGGER = Logger.getLogger(MovieDAOImpl.class);

    private static final String MOVIELIST = "SELECT * FROM movie ORDER BY movieName desc";
    private static final String MOVIEBYIDSQL = "SELECT * FROM movie m WHERE m.movieId = ?";
    private static final String MOVIEBYNAMEEXACTSQL = "SELECT * FROM movie m WHERE m.movieName = ?";
    private static final String MOVIESBYACTORIDSQL = "SELECT movieName FROM (movie m, catalog c) WHERE c.actorId = ? and m.movieId = c.movieId";
    private static final String MOVIE_INSERT = "insert into movie (movieName) values (?)";
//    private static final String MOVIE_UPDATE = "update movie SET movieName = ? WHERE movieId = ?";
    
    /**
     *
     * @param object
     * @return MovieBean
     * @throws DatabaseException
     */
    @Override
    public MovieBean insert(MovieBean object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long result = 0;
        MovieBean movieBean = (MovieBean) object;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("MovieDAO -- insert -- DataSource obj is  " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("MovieDAO -- insert -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(MOVIE_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, movieBean.getMovieName());
            LOGGER.info("MovieDAO -- insert -- Database query  " + preparedStatement.toString());
            result = preparedStatement.executeUpdate();
            if (result == 1) {
                resultSet = preparedStatement.getGeneratedKeys(); // get movieId that was auto generated
                resultSet.next();
                movieBean.setMovieId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("MovieDAO -- insert -- DB Exception occurred and message is " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "insert", this.toString());
        } catch (NamingException se2) {
            LOGGER.error("MovieDAO -- insert -- db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("MovieDAO -- insert -- db freeing resources exception " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
            }
        }
        return movieBean;
    }

    /**
     *
     * @param <T>
     * @param object
     * @return result status in 1 or 0
     * @throws DatabaseException
     */
    @Override
    public <T extends Serializable> Long update(T object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        long result = 0;
//        try {
//            DataSource dataSource = MySQLDataSource.getDataSource();
//            LOGGER.info("MovieDAO -- update -- DataSource obj is  " + dataSource);
//            connection = dataSource.getConnection();
//            preparedStatement = connection.prepareStatement(MOVIE_UPDATE);
//            MovieBean movieBean = (MovieBean) object;
//            preparedStatement.setString(1, movieBean.getStatus());
//            preparedStatement.setLong(2, movieBean.getWorkflowguid());
//            LOGGER.info("MovieDAO -- update -- Data base query  " + preparedStatement.toString());
//            result = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error("MovieDAO -- Update -- DB Exception occurred  and message is " + e.getMessage());
//            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), DatabaseUtil.MTHDMSG_SELECT, this.toString());
//        } catch (Exception se) {
//            LOGGER.error("MovieDAO -- updateStatus --  SQL exception occurred  and message is " + se.getMessage());
//            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), DatabaseUtil.MTHDMSG_SELECT, this.toString());
//        } finally {
//            // finally block used to close resources
//            try {
//                 MySQLDataSource.close(null, preparedStatement, connection);
//            } catch (SQLException se2) {
//                LOGGER.error(" MovieDAO -- updateStatus --  db freeing resources exception " + se2.getMessage());
//                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), DatabaseUtil.MTHDMSG_SELECT, this.toString());
//            }
//        }
        return result;
    }

    /**
     *
     * @param <T>
     * @return list of objects
     * @throws DatabaseException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> getAll() throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        List<MovieBean> movieBeanList = new ArrayList<>();
        MovieBean movieBean = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(MOVIELIST);
            LOGGER.info("MovieDAO -- getAll -- Database query  " + preparedStatement.toString());
            resultset = preparedStatement.executeQuery(MOVIELIST);
            while (resultset.next()) {
                movieBean = new MovieBean();
                movieBean.setMovieId(resultset.getLong("movieId"));
                movieBean.setMovieName(resultset.getString("movieName"));
                movieBeanList.add(movieBean);
            }
        } catch (SQLException e) {
            LOGGER.error("MovieDAO (getAll) SQL Exception: " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "getAll", this.toString());
        } catch (Exception se2) {
            LOGGER.error("MovieDAO -- getAll -- db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getAll", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultset, preparedStatement, connection);
            } catch (SQLException exception) {
                LOGGER.error("MovieDAO -- getAll -- db freeing resources exception " + exception.getMessage());
                throw new DatabaseException("Database Exception: " + exception.getMessage(), this.getClass().getName(), "getAll", this.toString());
            }
        }
        return (List<T>) movieBeanList;
    }


    @Override
    public void delete(Long key) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public MovieBean getMovieById(final Long movieId) throws DatabaseException {
        MovieBean movieBean = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("MovieDAO -- getMovieById -- DataSource obj is... " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("MovieDAO -- getMovieById -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(MOVIEBYIDSQL);
            preparedStatement.setLong(1, movieId);
            LOGGER.info("MovieDAO -- getMovieById -- Database query  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                movieBean = new MovieBean();
                movieBean.setMovieId(resultSet.getLong("movieId"));
                movieBean.setMovieName(resultSet.getString("movieName"));
            }
        } catch (SQLException | NamingException se) {
            LOGGER.error("MovieDAO -- getMovieById --  SQL or Naming exception occurred; message: " + se.getMessage());
            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
        } finally {
            // finally block used to close resources
            try {
                 MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("MovieDAO -- getMovieById --  DataBase closing exception occurred; message: " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
            }
        }
        return movieBean;
    }
    
    @Override
    public MovieBean getMovieByNameExact(final String movieName) throws DatabaseException {
        MovieBean movieBean = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("MovieDAO -- getActorByNameExact -- DataSource obj is... " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("MovieDAO -- getActorByNameExact -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(MOVIEBYNAMEEXACTSQL);
            preparedStatement.setString(1, movieName);
            LOGGER.info("MovieDAO -- getActorByNameExact -- Database query  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                movieBean = new MovieBean();
                movieBean.setMovieId(resultSet.getLong("movieId"));
                movieBean.setMovieName(resultSet.getString("movieName"));
            }
        } catch (SQLException | NamingException se) {
            LOGGER.error("MovieDAO -- getActorByNameExact --  SQL or Naming exception occurred; message: " + se.getMessage());
            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getActorByNameExact", this.toString());
        } finally {
            // finally block used to close resources
            try {
                 MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("MovieDAO -- getActorByNameExact --  DataBase closing exception occurred; message: " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getActorByNameExact", this.toString());
            }
        }
        return movieBean;
    }
    
    /**
     *
     * @param <T>
     * @param movieId
     * @return list of objects
     * @throws DatabaseException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> getMoviesByActorId(final Long movieId) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultset = null;
        List<MovieBean> movieBeanList = new ArrayList<>();
        MovieBean movieBean;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(MOVIESBYACTORIDSQL);
            preparedStatement.setLong(1, movieId);
            LOGGER.info("MovieDAO -- getMovieById -- Database query  " + preparedStatement.toString());
            resultset = preparedStatement.executeQuery();
            while (resultset.next()) {
                movieBean = new MovieBean();
//                movieBean.setMovieId(resultset.getLong("movieId"));
                movieBean.setMovieName(resultset.getString("movieName"));
                movieBeanList.add(movieBean);
            }
        } catch (SQLException e) {
            LOGGER.error("MovieDAO (getMoviesByActorId) SQL Exception: " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "getMoviesByActorId", this.toString());
        } catch (NamingException se2) {
            LOGGER.error("MovieDAO (getMoviesByActorId) db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getMoviesByActorId", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultset, preparedStatement, connection);
            } catch (SQLException exception) {
                LOGGER.error("MovieDAO (getMoviesByActorId) db freeing resources exception " + exception.getMessage());
                throw new DatabaseException("Database Exception: " + exception.getMessage(), this.getClass().getName(), "getMoviesByActorId", this.toString());
            }
        }
        return (List<T>) movieBeanList;
    }

//
//    @Override
//    public MovieBean getMovieById(final Long movieId) throws DatabaseException {
//        MovieBean movieBean = null;
//        ResultSet resultSet = null;
//        PreparedStatement preparedStatement = null;
//        Connection connection = null;
//        try {
//            DataSource dataSource = MySQLDataSource.getDataSource();
//            LOGGER.info("MovieDAO -- getMovieById -- DataSource obj is... " + dataSource);
//            connection = dataSource.getConnection();
//            LOGGER.info("MovieDAO -- getMovieById -- Database connection status " + connection.toString());
//            preparedStatement = connection.prepareStatement(MOVIEBYIDSQL);
//            preparedStatement.setLong(1, movieId);
//            LOGGER.info("MovieDAO -- getMovieById -- Database query  " + preparedStatement.toString());
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                movieBean = new MovieBean();
//                movieBean.setMovieId(resultSet.getLong("movieId"));
//                movieBean.setMovieName(resultSet.getString("movieName"));
//            }
//        } catch (SQLException | NamingException se) {
//            LOGGER.error("MovieDAO -- getMovieById --  SQL or Naming exception occurred; message: " + se.getMessage());
//            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
//        } finally {
//            // finally block used to close resources
//            try {
//                 MySQLDataSource.close(resultSet, preparedStatement, connection);
//            } catch (SQLException se2) {
//                LOGGER.error("MovieDAO -- getMovieById --  DataBase closing exception occurred; message: " + se2.getMessage());
//                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
//            }
//        }
//        return movieBean;
//    }
//    
//    @Override
//    public MovieBean getMovieByName(final String movieName) throws DatabaseException {
//        MovieBean movieBean = null;
//        ResultSet resultSet = null;
//        PreparedStatement preparedStatement = null;
//        Connection connection = null;
//        try {
//            DataSource dataSource = MySQLDataSource.getDataSource();
//            LOGGER.info("MovieDAO -- getMovieByName -- DataSource obj is... " + dataSource);
//            connection = dataSource.getConnection();
//            LOGGER.info("MovieDAO -- getMovieByName -- Database connection status " + connection.toString());
//            preparedStatement = connection.prepareStatement(MOVIEBYNAMESQL);
//            preparedStatement.setString(1, "%" + movieName + "%");
//            LOGGER.info("MovieDAO -- getMovieByName -- Database query  " + preparedStatement.toString());
//            resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                movieBean = new MovieBean();
//                movieBean.setMovieId(resultSet.getLong("movieId"));
//                movieBean.setMovieName(resultSet.getString("movieName"));
//            }
//        } catch (SQLException | NamingException se) {
//            LOGGER.error("MovieDAO -- getMovieByName --  SQL or Naming exception occurred; message: " + se.getMessage());
//            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
//        } finally {
//            // finally block used to close resources
//            try {
//                 MySQLDataSource.close(resultSet, preparedStatement, connection);
//            } catch (SQLException se2) {
//                LOGGER.error("MovieDAO -- getMovieByName --  DataBase closing exception occurred; message: " + se2.getMessage());
//                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getMovieById", this.toString());
//            }
//        }
//        return movieBean;
//    }
}