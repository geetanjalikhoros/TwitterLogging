package com.geetanjali.twtdw.resources;

import com.codahale.metrics.annotation.Metered;
import com.geetanjali.twtdw.TwitterDropWizardApplication;
import com.geetanjali.twtdw.api.Representation;
import com.codahale.metrics.annotation.Timed;
import io.dropwizard.jersey.caching.CacheControl;
import io.dropwizard.jersey.params.*;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {
    private final String message;
    private Twitter twitter ;
    private Status status;
    private List<Status> statuses;
    private String consumerKey;
    private String consumerSecret;
    private String accessToken;
    private String accessTokenSecret;
    private static final Logger logger = LoggerFactory.getLogger(TwitterDropWizardApplication.class);


    public Resource(String message, String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret) {
        this.message = message;
        this.statuses = new ArrayList<Status>();

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.accessToken = accessToken;
        this.accessTokenSecret = accessTokenSecret;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(this.consumerKey)
                .setOAuthConsumerSecret(this.consumerSecret)
                .setOAuthAccessToken(this.accessToken)
                .setOAuthAccessTokenSecret(this.accessTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Path("/timeline")
    @GET
    public Response fetchTweet() throws TwitterException, IOException
    {
        try
        {
            Paging page = new Paging(1,200);
            statuses.addAll(twitter.getHomeTimeline(page));
            int count = statuses.size();
            List<String> str = new ArrayList<String>();
            while(count > 0)
            {
                count--;
                System.out.println("Tweet "+count+"="+statuses.get(count).getText());
                str.add(statuses.get(count).getText());
            }
            logger.info("Fetch successful.");
            return Response.ok().entity(str).build();
        }
        catch (TwitterException e)
        {
            return Response.serverError().entity("Error in retrieving tweets").build();
        }
    }

    @Path("/tweet")
    @POST
    public Response postTweet() throws TwitterException, IOException
    {
        try {
            status = twitter.updateStatus(message);
            System.out.println("Status update successful to " + status.getText() + "\n");
            return Response.ok().entity("Status updated successfully").build();
        } catch (TwitterException e) {
            return Response.serverError().entity("Error in updating status").build();
        }
    }
}