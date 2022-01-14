/*
 * CatalogDAOImpl
 * @author paul
 */
package com.pab.challenge1.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.datasource.MySQLDataSource;
import com.pab.challenge1.model.CatalogBean;
import java.util.List;
import javax.naming.NamingException;

public class CatalogDAOImpl implements CatalogDAO {

    Logger LOGGER = Logger.getLogger(CatalogDAOImpl.class);

    private static final String CATALOG_INSERT = "insert into catalog (actorId, movieId) values (?,?)";
    
    /**
     * insert
     * @param object
     * @return CatalogBean
     * @throws DatabaseException
     */
    @Override
    public CatalogBean insert(CatalogBean object) throws DatabaseException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        long result = 0;
        CatalogBean catalogBean = (CatalogBean) object;
        try {
            DataSource dataSource = MySQLDataSource.getDataSource();
            LOGGER.info("CatalogDAO -- insert -- DataSource obj is  " + dataSource);
            connection = dataSource.getConnection();
            LOGGER.info("CatalogDAO -- insert -- Database connection status " + connection.toString());
            preparedStatement = connection.prepareStatement(CATALOG_INSERT);
            preparedStatement.setLong(1, catalogBean.getActorId());
            preparedStatement.setLong(2, catalogBean.getMovieId());
            LOGGER.info("CatalogDAO -- insert -- Database query  " + preparedStatement.toString());
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("CatalogDAO -- insert -- DB Exception occurred and message is " + e.getMessage());
            throw new DatabaseException("Database Exception: " + e.getMessage(), this.getClass().getName(), "insert", this.toString());
        } catch (NamingException se2) {
            LOGGER.error("CatalogDAO -- insert -- db freeing resources exception " + se2.getMessage());
            throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
        } finally {
            // finally block used to close resources
            try {
                MySQLDataSource.close(resultSet, preparedStatement, connection);
            } catch (SQLException se2) {
                LOGGER.error("CatalogDAO -- insert -- db freeing resources exception " + se2.getMessage());
                throw new DatabaseException("Database Exception: " + se2.getMessage(), this.getClass().getName(), "insert", this.toString());
            }
        }
        return catalogBean;
    }

    /**
     * getAll
     * @param <T>
     * @return list of objects
     * @throws DatabaseException
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T extends Serializable> List<T> getAll() throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * update
     * @param <T>
     * @param object
     * @return result status in 1 or 0
     * @throws DatabaseException
     */
    @Override
    public <T extends Serializable> Long update(T object) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * delete
     * @param key
     * @throws DatabaseException
     */
    @Override
    public void delete(Long key) throws DatabaseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}