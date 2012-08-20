package am.projects.webtransco.client;

import com.wm.app.b2b.server.Service;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import java.util.Iterator;

/**
 * web-transco-client API.
 * This Class propose two static services callTransco and callTranscoWithCache thaht could be used
 * in any java program of via a webMethods service.
 *
 * @author mlecoutre
 */
public class TranscoClient {
    private static final String TRANSCO_CACHE_MANAGER = "TranscoCacheManager";
    private static final String TRANSCO_CACHE = "TranscoCache";

    /**
     * @param param
     * @return
     */
    public static String callTransco(String param) {


        return null;
    }

    /**
     * @param param
     * @return
     */
    public static String callTranscoWithCache(String param) {

        //TODO configuring TranscoCacheManager cache


        // input
        IData input = IDataFactory.create();
        IDataCursor inputCursor = input.getCursor();
        IDataUtil.put(inputCursor, "name", "name");
        inputCursor.destroy();


        // output
        IData output = IDataFactory.create();
        try {
            output = Service.doInvoke("transconeeds.pub", "doTest", input);
        } catch (Exception e) {
            return e.getMessage();

        }
        IDataCursor outputCursor = output.getCursor();


        return "called doTest";
    }

    /**
     * Initialize cache configuration.
     * Try to get the cache configuration of a webMethods integration server with standard name
     * (TranscoCacheManager and a TranscoCache instance). If not found it try to create a
     * local cache instance.
     * @return cache
     */
    private Cache initCacheConfiguration() {
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
            //if not found, put a warn in the log and create a local cache manager
        } else {
            cache = cm.getCache(TRANSCO_CACHE);
            if (cache == null) {
                //should not be null if cm has been found (maybe configuration error)
                // raise an exception
            }
        }
        return cache;
    }
}
