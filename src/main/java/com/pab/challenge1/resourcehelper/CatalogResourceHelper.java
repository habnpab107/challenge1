/*
 * CatalogResourceHelper
 * @author paul
 */
package com.pab.challenge1.resourcehelper;

import com.pab.challenge1.model.MovieBean;
import java.io.Serializable;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CatalogResourceHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long actorId;
    private Long movieId;

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "ActorResourceHelper{" + 
                 "actorId=" + actorId + "," + 
                 "movieId=" + movieId + "" + 
               "}";
    }
    public String toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("ActorId", actorId);
        jo.put("MovieId", movieId);
        return jo.toString();
    }
}