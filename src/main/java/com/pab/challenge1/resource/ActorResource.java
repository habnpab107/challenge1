/*
 * ActorResource
 * @author paul
 */
package com.pab.challenge1.resource;

import com.pab.challenge1.builder.ActorBuilder;
import com.pab.challenge1.util.Constants;
import com.pab.challenge1.database.exception.DatabaseException;
import com.pab.challenge1.exception.ServiceException;
import com.pab.challenge1.resourcehelper.ActorResourceHelper;
import com.pab.challenge1.resourcehelper.CatalogResourceHelper;
import com.pab.challenge1.resourcehelper.MovieResourceHelper;
import com.pab.challenge1.service.ActorService;
import com.pab.challenge1.service.CatalogService;
import com.pab.challenge1.service.MovieService;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

@Produces(MediaType.APPLICATION_JSON)
@Path(Constants.ACTOR)
public class ActorResource {

    private static final Logger LOGGER = Logger.getLogger(ActorResource.class);

    private final ActorService actorService;
    private final CatalogService catalogService;
    private final MovieService movieService;
    private final ActorBuilder actorBuilder;

    @Inject
    public ActorResource(ActorService actorService, CatalogService catalogService, 
                         MovieService movieService, ActorBuilder actorBuilder) 
    {
        this.actorService = actorService;
        this.catalogService = catalogService;
        this.movieService = movieService;
        this.actorBuilder = actorBuilder;
    }

    /**
     * <b> http://serverIp:port/<contextpath>/webresources/actor?actorId=<value> </b>
     *
     * This method is used to find the actor and movies worked on by unique id
     *
     * @param actorId
     * @return Response
     */
    @GET
    public Response getActorById(@QueryParam("actorId") final Long actorId) {
        try {
//            if (actorId == null) throw new ServiceException("Missing argument");
//            if (actorId <= 0) throw new ServiceException("Invalid actorId " + actorId);
            if (actorId == null) return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Missing Argument\"}").type(MediaType.APPLICATION_JSON).build();
            if (actorId <= 0) return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Invalid actorId " + actorId + "\"}").type(MediaType.APPLICATION_JSON).build();
            
            ActorResourceHelper helper = actorService.getActorById(actorId);
            String out = "{\"Error\":\"No actors found by ID: " + actorId + "\"}";
            if (helper != null) {
                out = helper.toJSON();
            }
            return Response.status(Response.Status.OK).entity(out).type(MediaType.APPLICATION_JSON).build();

        } catch (ServiceException | DatabaseException exception) {
            LOGGER.error("ActorResource - getActorById - exception occurred: " + exception.getMessage());
            JSONObject jo = new JSONObject();
            jo.put("Error", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(jo.toString()).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
    }
    
    /**
     * http://serverIp:port/<contextpath>/webresources/actor/{actorName}
     *
     * This method is used to find the actor(s) and movies worked on by actorName - which could be partial
     *
     * @param actorName
     * @return Response
     */
    @GET
    @Path(Constants.ACTORNAME)
    public Response getActorByName(@PathParam("actorName") final String actorName) {
        try {
            if (actorName == null) return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Missing Argument\"}").type(MediaType.APPLICATION_JSON).build();
            List<ActorResourceHelper> resourceHelpersList = actorService.getActorByName(actorName);
            String out = "{\"Error\":\"No actors found by name: " + actorName + "\"}";
            if (resourceHelpersList != null && !resourceHelpersList.isEmpty()) {
                out = actorBuilder.buildJSONActorResourceHelperList(resourceHelpersList);
            }
            return Response.status(Response.Status.OK).entity(out).type(MediaType.APPLICATION_JSON).build();

        } catch (ServiceException | DatabaseException  exception) {
            LOGGER.error("ActorResource -- getActorByName -- exception occurred: " + exception.getMessage());
            JSONObject jo = new JSONObject();
            jo.put("Error", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(jo.toString()).
                    type(MediaType.APPLICATION_JSON).
                    build();
        }
    }
//    
//    /**
//     * http://serverIp:port/<contextpath>/webresources/actor/{actorName}
//     *
//     * This method is used to find the actor(s) and movies worked on by actorName - which could be partial
//     *
//     * @param actorName
//     * @return Response
//     */
//    @GET
//    @Path(Constants.ACTORNAME)
//    public Response getActorByName(@PathParam("actorName") final String actorName) {
//        try {
//            ActorResourceHelper helper = actorService.getActorByName(actorName);
//            String out = "No actors found by name: " + actorName;
//            if (helper != null) {
//                out = helper.toJSON();
//            }
////            System.out.println(out);
//            return Response.status(Response.Status.OK).entity(out).type(MediaType.APPLICATION_JSON).build();
//
//        } catch (ServiceException | DatabaseException | SQLException exception) {
//            LOGGER.error("ActorResource -- getActorByName -- exception occurred: " + exception.getMessage());
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
//                    entity(exception.getMessage()).
//                    type(MediaType.APPLICATION_JSON).
//                    build();
//        }
//    }

    /*
     * getActorList
     * http://serverIp:port/<contextpath>/webresources/actor/actorlist
     *
     * This method is used to get the actor objects List
     *
     * @return Response
     */
    @GET
    @Path(Constants.ACTOR_LIST)
    public Response getActorList() {

        try {
            List<ActorResourceHelper> resourceHelpersList = actorService.getAllActors();
            String out = "{\"Error\":\"No actors found\"}";
            if (resourceHelpersList != null && !resourceHelpersList.isEmpty()) {
                out = actorBuilder.buildJSONActorResourceHelperList(resourceHelpersList);
            }
            return Response.status(Response.Status.OK).entity(out).type(MediaType.APPLICATION_JSON).build();
        } catch (ServiceException | DatabaseException | SQLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).
                    entity(ex.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }
    
    /**
     * <b> http://serverIp:port/<contextpath>/webresources/actor/ </b>
     *
     * This method adds an actor
     *
     * @param data
     * @return Response
     */
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putXml(String data) {
        try {
            if (data == null || data.isEmpty()) return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Missing Argument\"}").type(MediaType.APPLICATION_JSON).build();
            JSONArray ja;
            try {
                if (data.startsWith("[")) {
                    ja = new JSONArray(data);
                } else if (data.startsWith("{")) {
                    JSONObject jo = new JSONObject(data);
                    ja = new JSONArray();
                    ja.put(jo);
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Invalid input: " + data + "\"}").type(MediaType.APPLICATION_JSON).build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"Invalid input: " + data + "\"}").type(MediaType.APPLICATION_JSON).build();
            }
            JSONArray addSummaryJA = new JSONArray();
            
            for (int i=0; i < ja.length(); i++) {
                JSONObject datajo = ja.getJSONObject(i);
                boolean newActor = false;
                boolean newMovie = false;
                
                // check actor
                String actorName = datajo.optString("ActorName");
                if (actorName == null || actorName.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"ActorName element required\"}").type(MediaType.APPLICATION_JSON).build();
                }
                
                // fetch actor 
                ActorResourceHelper actorResourceHelper = actorService.getActorByNameExact(actorName);
                
                if (actorResourceHelper == null) {
                    // create a new actor
                    actorResourceHelper = new ActorResourceHelper();
                    actorResourceHelper.setActorName(actorName);
                    actorResourceHelper = actorService.addActor(actorResourceHelper);
                    newActor = true;
                    JSONObject newActorJO = new JSONObject();
                    newActorJO.put("NewActor", actorResourceHelper.toJSON());
                    addSummaryJA.put(newActorJO);
                }
                
                // check movie
                String movieName = datajo.optString("MovieName");
                if (movieName == null || movieName.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("{\"Error\":\"MovieName element required\"}").type(MediaType.APPLICATION_JSON).build();
                }
                
                // fetch actor 
                MovieResourceHelper movieResourceHelper = movieService.getMovieByNameExact(movieName);
                
                if (movieResourceHelper == null) {
                    // create a new movie
                    movieResourceHelper = new MovieResourceHelper();
                    movieResourceHelper.setMovieName(movieName);
                    movieResourceHelper = movieService.addMovie(movieResourceHelper);
                    newMovie = true;
                    JSONObject newMovieJO = new JSONObject();
                    newMovieJO.put("NewMovie", movieResourceHelper.toJSON());
                    addSummaryJA.put(newMovieJO);
                }
                
                if (newActor || newMovie) {
                    // create new catalog
                    CatalogResourceHelper catalogResourceHelper = new CatalogResourceHelper();
                    catalogResourceHelper.setActorId(actorResourceHelper.getActorId());
                    catalogResourceHelper.setMovieId(movieResourceHelper.getMovieId());
                    catalogResourceHelper = catalogService.addCatalog(catalogResourceHelper);
                    JSONObject newCatalogJO = new JSONObject();
                    newCatalogJO.put("NewCatalog", catalogResourceHelper.toJSON());
                    addSummaryJA.put(newCatalogJO);
                }
                
            }
            String out = "{\"Error\":\"No data added\"}";
            if (addSummaryJA.length() > 0) {
                out = addSummaryJA.toString();
            }
            
            return Response.status(Response.Status.OK).entity(out).type(MediaType.APPLICATION_JSON).build();
        } catch (ServiceException | DatabaseException | SQLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).type(MediaType.APPLICATION_JSON).build();
        }
    }
}