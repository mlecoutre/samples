package am.projects.webtransco.client;

import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
import com.wm.data.IData;
import com.wm.data.IDataFactory;
import com.wm.util.JournalLogger;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import java.sql.Connection;
import java.sql.SQLException;
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
    public static final String DATASTORE_CONNECTION_PACKKAGE = "awmf.transco.connections";
    private static CacheManager cm = null;

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws SQLException {

        IData input = IDataFactory.create(); //nothing in the pipeline
        String serviceName = DATASTORE_PREFIX + dataStoreAliasName;
        try{
        IData output = Service.doInvoke(DATASTORE_CONNECTION_PACKKAGE, serviceName, input);
        }catch (Exception e){
            JournalLogger.log(JournalLogger.ERROR, JournalLogger.FAC_FLOW_SVC, JournalLogger.ERROR, "[TRANSCO]  ERROR RetrieveConnection done. ", e);
        }
        //JournalLogger.log(JournalLogger.DEBUG, JournalLogger.FAC_FLOW_SVC, JournalLogger.DEBUG, "[TRANSCO] RetrieveConnection done. " + this.getConnection());

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
        Cache cache = null;
        boolean isCMFound = false;
        if (cm == null) {
            for (Iterator<CacheManager> it = CacheManager.ALL_CACHE_MANAGERS.iterator(); it.hasNext(); ) {
                cm = it.next();
                if (TRANSCO_CACHE_MANAGER.equals(cm.getName())) {
                    isCMFound = true;
                    break;
                }
            }
            if(isCMFound){
                JournalLogger.log(JournalLogger.INFO, JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, "[TRANSCO] CacheManager has been found on the Integration Server");
            } else{
                //TODO if not found, put a warn in the log and create a local cache manager
                JournalLogger.log(JournalLogger.WARNING, JournalLogger.FAC_FLOW_SVC, JournalLogger.WARNING, "[TRANSCO]CacheManager HAS NOT BEEN FOUND on the Integration Server");
            }
        }
        if (cm != null) {
            cache = cm.getCache(TRANSCO_CACHE);
            if (cache == null) {
                //should not be null if cm has been found (maybe configuration error)
                // TODO raise an exception
            }
        }

        return cache;
    }

}
