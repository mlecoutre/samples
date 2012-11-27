package org.mat.samples.mongodb.listener;

import com.mongodb.DB;
import com.mongodb.Mongo;
import org.mat.samples.mongodb.Constants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Initialize EntityManager Factory
 * For Heroku, try to find the environment property DATABASE_URL, and transform
 * it into a valid jdbc URL to initialize properly the DB.
 *
 * @author mlecoutre
 *
 * TODO Check best practices for pooling or connection sharing
 */
@WebListener
public class MongoListener implements ServletContextListener, Constants {

    private static DB mongoDB;

    private static Mongo mongo;


    public MongoListener() {
        System.out.println("Initialize connection to mongo DB");
    }


    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            mongo = new Mongo(MONGO_SERVER, MONGO_PORT);
            mongoDB = mongo.getDB(MONGO_DB);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the entity manager
     *
     * @param event ServletContextEvent not used
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        mongo.close();
    }

    public static DB getMongoDB() {
        return mongoDB;
    }
}