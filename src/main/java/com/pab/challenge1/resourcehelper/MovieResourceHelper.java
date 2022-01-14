/*
 * MovieResourceHelper
 * @author paul
 */
package com.pab.challenge1.resourcehelper;

import com.pab.challenge1.model.MovieBean;
import java.io.Serializable;
import java.util.List;
import org.json.JSONObject;

public class MovieResourceHelper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long movieId;
    private String movieName;
    private List<MovieBean> movies;

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
        return "MovieResourceHelper{" + 
                 "movieId=" + movieId + ", " + 
                 "movieName=\"" + movieName + "" +
               "}";
    }
    public String toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("Movie", movieName);
        return jo.toString();
    }
}