/*
 * MovieBuilder
 * @author paul
 */

package com.pab.challenge1.builder;

import com.pab.challenge1.model.MovieBean;
import com.pab.challenge1.resourcehelper.MovieResourceHelper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class MovieBuilder {

    /**
     * This method converts Helper to Bean
     * @param movieResourceHelper
     * @return
     */
    public MovieBean buildMovieBean(MovieResourceHelper movieResourceHelper) {
        MovieBean movieBean = null;
        if (movieResourceHelper != null) {
            movieBean = new MovieBean();
            movieBean.setMovieId(movieResourceHelper.getMovieId());
            movieBean.setMovieName(movieResourceHelper.getMovieName());
        }
        return movieBean;
    }

    /**
     * This method converts Bean to Helper
     * @param movieBean
     * @return
     */
    public MovieResourceHelper buildMovieResourceHelper(MovieBean movieBean) {

        MovieResourceHelper movieResourceHelper = null;
        if (movieBean != null) {
            movieResourceHelper = new MovieResourceHelper();
            movieResourceHelper.setMovieId(movieBean.getMovieId());
            movieResourceHelper.setMovieName(movieBean.getMovieName());
        }
        return movieResourceHelper;
    }

    /**
     * This method converts List of Beans to List of Resources.
     * @param movieBeanList
     * @return
     */
    public List<MovieResourceHelper> buildMovieResourceHelperList(List<MovieBean> movieBeanList) {

        List<MovieResourceHelper> movieResourceHelperList = null;
        if (movieBeanList != null) {
            movieResourceHelperList = new ArrayList<>();
            for (MovieBean movieBean : movieBeanList) {
                movieResourceHelperList.add(buildMovieResourceHelper(movieBean));
            }
        }
        return movieResourceHelperList;
    }

    /**
     * This method converts List of Resources to json output.
     * @param movieResourceHelperList
     * @return
     */
    public String buildJSONMovieResourceHelperList(List<MovieResourceHelper> movieResourceHelperList) {

        JSONArray ja = new JSONArray();
        
        if (movieResourceHelperList != null) {
            for (MovieResourceHelper movieResourceHelper : movieResourceHelperList) {
                ja.put(movieResourceHelper.toJSON());
            }
        }
        return ja.toString();
    }
}