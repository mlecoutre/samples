package am.projects.webtransco.client;

import com.wm.app.b2b.server.Service;
import com.wm.data.IData;
import com.wm.data.IDataFactory;
import com.wm.util.JournalLogger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import java.sql.Connection;
import java.util.Iterator;

/**
 * webMethods implementation strategy
 * User: mlecoutre
 * Date: 21/08/12
 */
public class StrategyExeWM extends AbstractExecutionStrategy {

    private static final String TRANSCO_CACHE_MANAGER = "TranscoCacheManager";
    private static final String TRANSCO_CACHE = "TranscoCache";

    public static final String DATASTORE_PREFIX = "retrieve";
    public static final String DATASTORE_CONNECTION_PACKKAGE = "transconeeds.connections";

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws Exception {

        IData input = IDataFactory.create(); //nothing in the pipeline
        String serviceName = DATASTORE_PREFIX + dataStoreAliasName;
        IData output = Service.doInvoke(DATASTORE_CONNECTION_PACKKAGE, serviceName, input);

//        IDataCursor outputCursor = output.getCursor();
//        outputCursor.destroy();


        JournalLogger.log(JournalLogger.INFO, JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, "retrieveConnection done."+this.getConnection());

         return this.getConnection();
    }

    /**
     * Initialize cache configuration.
     * Try to get the cache configuration of a webMethods integration server with standard name
     * (TranscoCacheManager and a TranscoCache instance). If not found it try to create a
     * local cache instance.
     *
     * @return cache instance
     */
    public Cache retrieveCache() {
        CacheManager cm = null;
        Cache cache = null;
        boolean isCMFound = false;
        for (Iterator<CacheManager> it = CacheManager.ALL_CACHE_MANAGERS.iterator(); it.hasNext(); ) {
            cm = it.next();
            if (TRANSCO_CACHE_MANAGER.equals(cm.getName())) {
                isCMFound = true;
                break;
            }
        }
        if (cm == null) {
            //TODO if not found, put a warn in the log and create a local cache manager
        } else {
            cache = cm.getCache(TRANSCO_CACHE);
            if (cache == null) {
                //should not be null if cm has been found (maybe configuration error)
                // TODO raise an exception
            }
        }
        return cache;
    }

}
