/*
 * CatalogBean
 * @author paul
 */
package com.pab.challenge1.model;

import java.io.Serializable;

public class CatalogBean implements Serializable {

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
        return "CatalogBean{" + 
                 "actorId=" + actorId + ", " +
                 "movieId=" + movieId + " " +
                "}";
    }
}