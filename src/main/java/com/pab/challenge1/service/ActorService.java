/*
 * ActorService
 * @author paul
 */
package com.pab.challenge1.service;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.resourcehelper.ActorResourceHelper;
import java.sql.SQLException;
import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ActorService {

    ActorResourceHelper addActor(ActorResourceHelper actorResourceHelper) throws ServiceException, DatabaseException, SQLException;
//
//    void updateActor(ActorResourceHelper actorResourceHelper) throws ServiceException, DatabaseException, SQLException;
//
    ActorResourceHelper getActorById(Long actorId) throws ServiceException, DatabaseException;
    
    ActorResourceHelper getActorByNameExact(String actorName) throws ServiceException, DatabaseException;
    List<ActorResourceHelper> getActorByName(String actorName) throws ServiceException, DatabaseException;

    List<ActorResourceHelper> getAllActors() throws ServiceException, DatabaseException, SQLException;
}
