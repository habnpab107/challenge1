/*
 * MovieBean
 * @author paul
 */
package com.pab.challenge1.model;

import java.io.Serializable;

public class MovieBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long movieId;
    private String movieName;

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Override
    public String toString() {
        return "MovieBean{" + 
                 "movieId=" + movieId + ", " +
                 "movieName=\"" + movieName + "\"" + 
                "}";
    }
}