/*
 * ActorBean
 * @author paul
 */
package com.pab.challenge1.model;

import java.io.Serializable;
import java.util.List;

public class ActorBean implements Serializable {

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
        return "ActorBean{" + 
                 "actorId=" + actorId + ", " +
                 "actorName=\"" + actorName + "\"," + 
                 "movies=" + movies + "" +
                "}";
    }
}