package com.geetanjali.twtdw;
//import com.geetanjali.twtdw.health.HealthCheckup;
import com.geetanjali.twtdw.resources.Resource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitterDropWizardApplication extends Application<TwitterDropWizardConfiguration> {

    public static void main(final String[] args) throws Exception {
        new TwitterDropWizardApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<TwitterDropWizardConfiguration> bootstrap) {

    }

    @Override
    public void run(final TwitterDropWizardConfiguration configuration,
                    final Environment environment) {
        final Resource resource = new Resource(configuration.getMessage(), configuration.getConsumerKey(), configuration.getConsumerSecret(),
                configuration.getAccessToken(), configuration.getAccessTokenSecret());
        environment.jersey().register(resource);
    }

}
