/*
 * ActorDAOImpl
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
import com.pab.challenge1.model.ActorBean;
import com.pab.challenge1.model.MovieBean;
import javax.naming.NamingException;

public class ActorDAOImpl implements ActorDAO {

    Logger LOGGER = Logger.getLogger(ActorDAOImpl.class);

    private static final String ACTORLIST = "SELECT * FROM actor ORDER BY actorName desc";
    private static final String ACTORBYIDSQL = "SELECT * FROM actor a WHERE a.actorId = ?";
    private static final String ACTORBYNAMEEXACTSQL = "SELECT * FROM actor a WHERE a.actorName = ?";
    private static final String ACTORBYNAMESQL = "SELECT * FROM actor a WHERE a.actorName like ?";
    private static final String ACTOR_INSERT = "insert into `actor` (actorName) values (?)";
//    private static final String ACTOR_UPDATE = "update actor SET actorName = ? WHERE actorId = ?";
    
    /**
     *
     * @param object
     * @return ActorBean
     * @throws DatabaseException
     */
    @Override
    public ActorBean insert(ActorBean object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long result = 0;
        ActorBean actorBean = (ActorBean) object;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("ActorDAO -- insert -- DataSource obj is  " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("ActorDAO -- insert -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(ACTOR_INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, actorBean.getActorName());
            LOGGER.info("ActorDAO -- insert -- Database query  " + preparedStatement.toString());
            result = preparedStatement.executeUpdate();
            if (result == 1) {
                    resultSet = preparedStatement.getGeneratedKeys(); // get actorId that was auto generated
                    resultSet.next();
                    actorBean.setActorId(resultSet.getLong(1));
            }
        } catch (SQLException e) {
            LOGGER.error("ActorDAO -- insert -- DB Exception occurred and message is " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "insert", this.toString());
        } catch (NamingException se2) {
            LOGGER.error("ActorDAO -- insert -- db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("ActorDAO -- insert -- db freeing resources exception " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
            }
        }
        return actorBean;
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
        long result = 0;
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//        try {
//            DataSource dataSource = MySQLDataSource.getDataSource();
//            LOGGER.info("ActorDAO -- update -- DataSource obj is  " + dataSource);
//            connection = dataSource.getConnection();
//            preparedStatement = connection.prepareStatement(ACTOR_UPDATE);
//            ActorBean actorBean = (ActorBean) object;
//            preparedStatement.setString(1, actorBean.getStatus());
//            preparedStatement.setLong(2, actorBean.getWorkflowguid());
//            LOGGER.info("ActorDAO -- update -- Data base query  " + preparedStatement.toString());
//            result = preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            LOGGER.error("ActorDAO -- Update -- DB Exception occurred  and message is " + e.getMessage());
//            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), DatabaseUtil.MTHDMSG_SELECT, this.toString());
//        } catch (Exception se) {
//            LOGGER.error("ActorDAO -- updateStatus --  SQL exception occurred  and message is " + se.getMessage());
//            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), DatabaseUtil.MTHDMSG_SELECT, this.toString());
//        } finally {
//            // finally block used to close resources
//            try {
//                 MySQLDataSource.close(null, preparedStatement, connection);
//            } catch (SQLException se2) {
//                LOGGER.error(" ActorDAO -- updateStatus --  db freeing resources exception " + se2.getMessage());
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
        List<ActorBean> actorBeanList = new ArrayList<>();
        ActorBean actorBean = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement(ACTORLIST);
            LOGGER.info("ActorDAO -- getAll -- Database query  " + preparedStatement.toString());
            resultset = preparedStatement.executeQuery(ACTORLIST);
            MovieDAOImpl mdImpl = new MovieDAOImpl();
            while (resultset.next()) {
                actorBean = new ActorBean();
                actorBean.setActorId(resultset.getLong("actorId"));
                actorBean.setActorName(resultset.getString("actorName"));
                List<MovieBean> movieBeanList = mdImpl.getMoviesByActorId(actorBean.getActorId());
                actorBean.setMovies(movieBeanList);
                actorBeanList.add(actorBean);
            }
        } catch (SQLException e) {
            LOGGER.error("ActorDAO (getAll) SQL Exception: " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "getAll", this.toString());
        } catch (DatabaseException | NamingException se2) {
            LOGGER.error("ActorDAO -- getAll -- db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getAll", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultset, preparedStatement, connection);
            } catch (SQLException exception) {
                LOGGER.error("ActorDAO -- getAll -- db freeing resources exception " + exception.getMessage());
                throw new DatabaseException("Database Exception: " + exception.getMessage(), this.getClass().getName(), "getAll", this.toString());
            }
        }
        return (List<T>) actorBeanList;
    }

    @Override
    public void delete(Long key) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ActorBean getActorById(final Long actorId) throws DatabaseException {
        ActorBean actorBean = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("ActorDAO -- getActorById -- DataSource obj is... " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("ActorDAO -- getActorById -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(ACTORBYIDSQL);
            preparedStatement.setLong(1, actorId);
            LOGGER.info("ActorDAO -- getActorById -- Database query  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            MovieDAOImpl mdImpl = new MovieDAOImpl();
            
            while (resultSet.next()) {
                actorBean = new ActorBean();
                actorBean.setActorId(resultSet.getLong("actorId"));
                actorBean.setActorName(resultSet.getString("actorName"));
                List<MovieBean> movieBeanList = mdImpl.getMoviesByActorId(actorBean.getActorId());
                actorBean.setMovies(movieBeanList);
                
            }
        } catch (SQLException | NamingException se) {
            LOGGER.error("ActorDAO -- getActorById --  SQL or Naming exception occurred; message: " + se.getMessage());
            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getActorById", this.toString());
        } finally {
            // finally block used to close resources
            try {
                 MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("ActorDAO -- getActorById --  DataBase closing exception occurred; message: " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getActorById", this.toString());
            }
        }
        return actorBean;
    }
    
    @Override
    public ActorBean getActorByNameExact(final String actorName) throws DatabaseException {
        ActorBean actorBean = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("ActorDAO -- getActorByNameExact -- DataSource obj is... " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("ActorDAO -- getActorByNameExact -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(ACTORBYNAMEEXACTSQL);
            preparedStatement.setString(1, actorName);
            LOGGER.info("ActorDAO -- getActorByNameExact -- Database query  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            MovieDAOImpl mdImpl = new MovieDAOImpl();
            
            while (resultSet.next()) {
                actorBean = new ActorBean();
                actorBean.setActorId(resultSet.getLong("actorId"));
                actorBean.setActorName(resultSet.getString("actorName"));
                List<MovieBean> movieBeanList = mdImpl.getMoviesByActorId(actorBean.getActorId());
                actorBean.setMovies(movieBeanList);
            }
        } catch (SQLException | NamingException se) {
            LOGGER.error("ActorDAO -- getActorByNameExact --  SQL or Naming exception occurred; message: " + se.getMessage());
            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getActorByNameExact", this.toString());
        } finally {
            // finally block used to close resources
            try {
                 MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("ActorDAO -- getActorByNameExact --  DataBase closing exception occurred; message: " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getActorByNameExact", this.toString());
            }
        }
        return actorBean;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> getActorByName(final String actorName) throws DatabaseException {
        ActorBean actorBean = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        List<ActorBean> actorBeanList = new ArrayList<>();
        Connection connection = null;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("ActorDAO -- getActorByName -- DataSource obj is... " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("ActorDAO -- getActorByName -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(ACTORBYNAMESQL);
            preparedStatement.setString(1, "%" + actorName + "%");
            LOGGER.info("ActorDAO -- getActorByName -- Database query  " + preparedStatement.toString());
            resultSet = preparedStatement.executeQuery();
            MovieDAOImpl mdImpl = new MovieDAOImpl();
            while (resultSet.next()) {
                actorBean = new ActorBean();
                actorBean.setActorId(resultSet.getLong("actorId"));
                actorBean.setActorName(resultSet.getString("actorName"));
                List<MovieBean> movieBeanList = mdImpl.getMoviesByActorId(actorBean.getActorId());
                actorBean.setMovies(movieBeanList);
                actorBeanList.add(actorBean);
            }
        } catch (SQLException | NamingException se) {
            LOGGER.error("ActorDAO -- getActorByName --  SQL or Naming exception occurred; message: " + se.getMessage());
            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getActorById", this.toString());
        } finally {
            // finally block used to close resources
            try {
                 MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("ActorDAO -- getActorByName --  DataBase closing exception occurred; message: " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getActorById", this.toString());
            }
        }
        return (List<T>) actorBeanList;
    }
//    
//    @Override
//    public ActorBean getActorByName(final String actorName) throws DatabaseException {
//        ActorBean actorBean = null;
//        ResultSet resultSet = null;
//        PreparedStatement preparedStatement = null;
//        Connection connection = null;
//        try {
//            DataSource dataSource = MySQLDataSource.getDataSource();
//            LOGGER.info("ActorDAO -- getActorByName -- DataSource obj is... " + dataSource);
//            connection = dataSource.getConnection();
//            LOGGER.info("ActorDAO -- getActorByName -- Database connection status " + connection.toString());
//            preparedStatement = connection.prepareStatement(ACTORBYNAMESQL);
//            preparedStatement.setString(1, "%" + actorName + "%");
//            LOGGER.info("ActorDAO -- getActorByName -- Database query  " + preparedStatement.toString());
//            resultSet = preparedStatement.executeQuery();
//            MovieDAOImpl mdImpl = new MovieDAOImpl();
//            while (resultSet.next()) {
//                actorBean = new ActorBean();
//                actorBean.setActorId(resultSet.getLong("actorId"));
//                actorBean.setActorName(resultSet.getString("actorName"));
//                List<MovieBean> movieBeanList = mdImpl.getMoviesByActorId(actorBean.getActorId());
//                actorBean.setMovies(movieBeanList);
//            }
//        } catch (SQLException | NamingException se) {
//            LOGGER.error("ActorDAO -- getActorByName --  SQL or Naming exception occurred; message: " + se.getMessage());
//            throw new DatabaseException("Database Exception: " + se.getMessage(), this.getClass().getName(), "getActorById", this.toString());
//        } finally {
//            // finally block used to close resources
//            try {
//                 MySQLDataSource.close(resultSet, preparedStatement, connection);
//            } catch (SQLException se2) {
//                LOGGER.error("ActorDAO -- getActorByName --  DataBase closing exception occurred; message: " + se2.getMessage());
//                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "getActorById", this.toString());
//            }
//        }
//        return actorBean;
//    }
}