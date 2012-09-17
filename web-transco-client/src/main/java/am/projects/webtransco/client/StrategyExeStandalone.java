package am.projects.webtransco.client;

import am.projects.webtransco.client.model.TranscoDatastore;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * StrategyExeStandalone
 * User: mlecoutre
 * Date: 21/08/12
 */
public class StrategyExeStandalone extends AbstractExecutionStrategy {
    private static Logger logger = LoggerFactory.getLogger((StrategyExeStandalone.class.getName()));
    public static final String EH_CACHE_XML = "/ehCache.xml";
    public static final String TRANSCO_CACHE = "TranscoCache";
    private CacheManager manager = null;

    /**
     * default constructor
     */
    public StrategyExeStandalone() {
        initCache();
    }

    private void initCache() {
        manager = CacheManager.newInstance(getClass().getResource(EH_CACHE_XML).getFile());
        boolean cacheExist = manager.cacheExists(TRANSCO_CACHE);
        logger.debug("Init cache in standalone mode: " + cacheExist);
    }

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws SQLException {
        TranscoContext ctx = TranscoClient.getInstance().getContext();
        TranscoDatastore datastore = ctx.getDatastores().get(dataStoreAliasName);
        //Loading the driver...
        //  Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

        //Buiding a Connection
        logger.debug("Get connection for data store" + datastore.getUrl());
        Connection mConn = DriverManager.getConnection(datastore.getUrl(), datastore.getUser(), datastore.getPassword());

        return mConn;
    }

    @Override
    public Cache retrieveCache(String cacheName) {
        Cache cache = null;
        if (manager != null) {
            if (cacheName == null || cacheName.equals("")) {
                logger.debug("Use default cache " + TRANSCO_CACHE);
                cache = manager.getCache(TRANSCO_CACHE);

            } else {
                cache = manager.getCache(cacheName);
            }
        }
        return cache;
    }
}
