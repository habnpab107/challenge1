/*
 * ActorResourceHelper
 * @author paul
 */
package com.pab.challenge1.resourcehelper;

import com.pab.challenge1.model.MovieBean;
import java.io.Serializable;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;

public class ActorResourceHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long actorId;
    private String actorName;
    private List<MovieBean> movies;

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public List<MovieBean> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieBean> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "ActorResourceHelper{" + 
                 "actorId=" + actorId + ", " + 
                 "actorName=\"" + actorName + "\"," + 
                 "movies=" + movies + "" +
               "}";
    }
    public String toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("Actor", actorName);
        if (getMovies() != null) {
            JSONArray ja = new JSONArray();
            for (int i=0; i < movies.size(); i++) {
                MovieBean mb = movies.get(i);
                ja.put(mb.getMovieName());
            }
            jo.put("Movies", ja);
        }
        //System.out.println(jo.toString());
        return jo.toString();
    }
}