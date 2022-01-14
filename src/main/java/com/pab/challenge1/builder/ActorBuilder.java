/*
 * ActorBuilder
 * @author paul
 */

package com.pab.challenge1.builder;

import com.pab.challenge1.model.ActorBean;
import com.pab.challenge1.resourcehelper.ActorResourceHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class ActorBuilder {

    /**
     * This method converts Helper to Bean
     * @param actorResourceHelper
     * @return
     */
    public ActorBean buildActorBean(ActorResourceHelper actorResourceHelper) {
        ActorBean actorBean = null;
        if (actorResourceHelper != null) {
            actorBean = new ActorBean();
            actorBean.setActorId(actorResourceHelper.getActorId());
            actorBean.setActorName(actorResourceHelper.getActorName());
            actorBean.setMovies(actorResourceHelper.getMovies());
        }
        return actorBean;
    }

    /**
     * This method converts Bean to Helper
     * @param actorBean
     * @return
     */
    public ActorResourceHelper buildActorResourceHelper(ActorBean actorBean) {

        ActorResourceHelper actorResourceHelper = null;
        if (actorBean != null) {
            actorResourceHelper = new ActorResourceHelper();
            actorResourceHelper.setActorId(actorBean.getActorId());
            actorResourceHelper.setActorName(actorBean.getActorName());
            actorResourceHelper.setMovies(actorBean.getMovies());
        }
        return actorResourceHelper;
    }

    /**
     * This method converts List of Beans to List of Resources.
     * @param actorBeanList
     * @return
     */
    public List<ActorResourceHelper> buildActorResourceHelperList(List<ActorBean> actorBeanList) {

        List<ActorResourceHelper> actorResourceHelperList = null;
        if (actorBeanList != null) {
            actorResourceHelperList = new ArrayList<>();
            for (ActorBean actorBean : actorBeanList) {
                actorResourceHelperList.add(buildActorResourceHelper(actorBean));
            }
        }
        return actorResourceHelperList;
    }

    /**
     * This method converts List of Resources to json output.
     * @param actorResourceHelperList
     * @return
     */
    public String buildJSONActorResourceHelperList(List<ActorResourceHelper> actorResourceHelperList) {

        JSONArray ja = new JSONArray();
        
        if (actorResourceHelperList != null) {
            for (ActorResourceHelper actorResourceHelper : actorResourceHelperList) {
                ja.put(actorResourceHelper.toJSON());
            }
        }
        return ja.toString();
    }
}