/*
 * CatalogBuilder
 * @author paul
 */

package com.pab.challenge1.builder;

import com.pab.challenge1.model.CatalogBean;
import com.pab.challenge1.resourcehelper.CatalogResourceHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class CatalogBuilder {

    /**
     * This method converts Helper to Bean
     * @param catalogResourceHelper
     * @return
     */
    public CatalogBean buildCatalogBean(CatalogResourceHelper catalogResourceHelper) {
        CatalogBean catalogBean = null;
        if (catalogResourceHelper != null) {
            catalogBean = new CatalogBean();
            catalogBean.setActorId(catalogResourceHelper.getActorId());
            catalogBean.setMovieId(catalogResourceHelper.getMovieId());
        }
        return catalogBean;
    }

    /**
     * This method converts Bean to Helper
     * @param catalogBean
     * @return
     */
    public CatalogResourceHelper buildCatalogResourceHelper(CatalogBean catalogBean) {

        CatalogResourceHelper catalogResourceHelper = null;
        if (catalogBean != null) {
            catalogResourceHelper = new CatalogResourceHelper();
            catalogResourceHelper.setActorId(catalogBean.getActorId());
            catalogResourceHelper.setMovieId(catalogBean.getMovieId());
        }
        return catalogResourceHelper;
    }

    /**
     * This method converts List of Beans to List of Resources.
     * @param catalogBeanList
     * @return
     */
    public List<CatalogResourceHelper> buildCatalogResourceHelperList(List<CatalogBean> catalogBeanList) {

        List<CatalogResourceHelper> catalogResourceHelperList = null;
        if (catalogBeanList != null) {
            catalogResourceHelperList = new ArrayList<>();
            for (CatalogBean catalogBean : catalogBeanList) {
                catalogResourceHelperList.add(buildCatalogResourceHelper(catalogBean));
            }
        }
        return catalogResourceHelperList;
    }

    /**
     * This method converts List of Resources to json output.
     * @param catalogResourceHelperList
     * @return
     */
    public String buildJSONCatalogResourceHelperList(List<CatalogResourceHelper> catalogResourceHelperList) {

        JSONArray ja = new JSONArray();
        
        if (catalogResourceHelperList != null) {
            for (CatalogResourceHelper catalogResourceHelper : catalogResourceHelperList) {
                ja.put(catalogResourceHelper.toJSON());
            }
        }
        return ja.toString();
    }
}