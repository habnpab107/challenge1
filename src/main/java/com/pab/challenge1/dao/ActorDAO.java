/* 
 * ActorDAO
 * @author paul
 */
package com.pab.challenge1.dao;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.model.ActorBean;
import java.io.Serializable;
import java.util.List;

public interface ActorDAO extends CommonDBInterface {
    ActorBean insert(ActorBean object) throws DatabaseException;
    ActorBean getActorById(Long actorId) throws DatabaseException;
    ActorBean getActorByNameExact(String actorName) throws DatabaseException;
    <T extends Serializable> List<T> getActorByName(String actorName) throws DatabaseException;
}