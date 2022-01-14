/*
 * CatalogService
 * @author paul
 */
package com.pab.challenge1.service;

import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.resourcehelper.CatalogResourceHelper;
import java.sql.SQLException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CatalogService {

    CatalogResourceHelper addCatalog(CatalogResourceHelper catalogResourceHelper) throws ServiceException, DatabaseException, SQLException;
}
