/* 
 * CatalogDAO
 * @author paul
 */
package com.pab.challenge1.dao;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.model.CatalogBean;

public interface CatalogDAO extends CommonDBInterface {
    CatalogBean insert(CatalogBean object) throws DatabaseException;
//    <T extends Serializable> List<T> getCatalogsByActorId(Long movieId) throws DatabaseException;
//    CatalogBean getCatalogById(Long movieId) throws DatabaseException;
}