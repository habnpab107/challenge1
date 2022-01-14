/*
 * CommonDBInterface
 * @author paul
 */
package com.pab.challenge1.dao;

import com.pab.challenge1.database.exception.DatabaseException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

public interface CommonDBInterface {

    <T extends Serializable> Long update(T object) throws DatabaseException, SQLException;

    <T extends Serializable> List<T> getAll() throws DatabaseException, SQLException;

    void delete(Long key) throws DatabaseException, SQLException;

}