/*
 * ActorServiceImpl
 * @author paul
 */
package com.pab.challenge1.service;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.pab.challenge1.builder.ActorBuilder;
import com.pab.challenge1.dao.ActorDAO;
import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.model.ActorBean;
import com.pab.challenge1.resourcehelper.ActorResourceHelper;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorDAO actorDAO;
    private final ActorBuilder actorBuilder;

    @Inject
    public ActorServiceImpl(ActorDAO actorDAO, ActorBuilder actorBuilder) {
        this.actorDAO = actorDAO;
        this.actorBuilder = actorBuilder;
    }

    @Override
    public ActorResourceHelper addActor(ActorResourceHelper actorResourceHelper) throws ServiceException, DatabaseException, SQLException {
        ActorBean actorBean = actorBuilder.buildActorBean(actorResourceHelper);
        ActorBean newActorBean = actorDAO.insert(actorBean);
        return actorBuilder.buildActorResourceHelper(newActorBean);
    }
//
//    @Override
//    public void updateActorObject(ActorResourceHelper queueProcessorResourceHelper) throws ServiceException, DatabaseException, SQLException {
//        ActorBean actorBean = actorBuilder.buildActorBean(queueProcessorResourceHelper);
//        actorDAO.update(actorBean);
//    }
//
    @Override
    public ActorResourceHelper getActorById(Long actorId) throws ServiceException, DatabaseException {
        ActorBean bean = actorDAO.getActorById(actorId);
        return actorBuilder.buildActorResourceHelper(bean);
    }
    
    @Override
    public ActorResourceHelper getActorByNameExact(String actorName) throws ServiceException, DatabaseException {
        ActorBean bean = actorDAO.getActorByNameExact(actorName);
        return actorBuilder.buildActorResourceHelper(bean);
    }
    
    @Override
    public List<ActorResourceHelper> getActorByName(String actorName) throws ServiceException, DatabaseException {
        List<ActorBean> actorBeanList = actorDAO.getActorByName(actorName);
        List<ActorResourceHelper> actorResourceHelperList = actorBuilder.buildActorResourceHelperList(actorBeanList);
        return actorResourceHelperList;
    }

    @Override
    public List<ActorResourceHelper> getAllActors() throws ServiceException, DatabaseException, SQLException {
        List<ActorBean> actorBeanList = actorDAO.getAll();
        List<ActorResourceHelper> actorResourceHelperList = actorBuilder.buildActorResourceHelperList(actorBeanList);
        return actorResourceHelperList;
    }
}