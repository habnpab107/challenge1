/*
 * CatalogServiceImpl
 * @author paul
 */
package com.pab.challenge1.service;

import java.sql.SQLException;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.pab.challenge1.builder.CatalogBuilder;
import com.pab.challenge1.dao.CatalogDAO;
import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.model.CatalogBean;
import com.pab.challenge1.resourcehelper.CatalogResourceHelper;

@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogDAO catalogDAO;
    private final CatalogBuilder catalogBuilder;

    @Inject
    public CatalogServiceImpl(CatalogDAO catalogDAO, CatalogBuilder catalogBuilder) {
        this.catalogDAO = catalogDAO;
        this.catalogBuilder = catalogBuilder;
    }

    @Override
    public CatalogResourceHelper addCatalog(CatalogResourceHelper catalogResourceHelper) throws ServiceException, DatabaseException, SQLException {
        CatalogBean catalogBean = catalogBuilder.buildCatalogBean(catalogResourceHelper);
        CatalogBean newCatalogBean = catalogDAO.insert(catalogBean);
        return catalogBuilder.buildCatalogResourceHelper(newCatalogBean);
    }
}