package am.projects.webtransco.client;

import am.projects.webtransco.client.model.ListCall;
import am.projects.webtransco.client.model.ListResponse;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: E010925
 * Date: 22/08/12
 * Time: 10:33
 */
public class CacheTest {

    Logger logger = LoggerFactory.getLogger(CacheTest.class.getName());

    @Test
    public void testCreateCacheTranscoKey() {
        List<String> values = new ArrayList<String>();
        values.add("p1");
        values.add(null);
        values.add(" ");
        values.add("p2");
        ListCall lc = new ListCall("functionName", values);
        String key = AbstractExecutionStrategy.createCacheTranscoKey(lc);
        logger.debug("testCreateCacheTranscoKey: key=" + key);
        assertTrue("The cache key has to be correct", "functionName|p1| ||p2".equals(key));
    }

    @Test
    public void testStandaloneCache() {
        CacheManager manager = CacheManager.newInstance(getClass().getResource("/ehCache.xml").getFile());
        boolean cacheExist = manager.cacheExists("TranscoCache");
        CacheConfiguration cc = manager.getCache("TranscoCache").getCacheConfiguration();
        logger.debug("testStandaloneCache:  cache found.");
        assertTrue("Configuration file is found", cacheExist);
    }

    @Test
    public void testSeveralCallsWithCache() throws Exception {

        List<Long> times = new ArrayList<Long>();

        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        params.add("APP");
        params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
        ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);
        calls.add(lc);
        TranscoClient.getInstance().getExeStrategy().retrieveCache().removeAll();
        for (int i = 0; i < 10; i++) {

            List<ListResponse> results = null;

            long start = System.currentTimeMillis();
            results = TranscoClient.callTranscoWithCache("Transco_TBO", true, calls, null);
            long time = System.currentTimeMillis() - start;
            Formatter format = new Formatter();
            logger.debug(format.format("testSimpleCallWithCache: (%d ms)=> %s", time, results.toString()).toString());
            assertTrue("This call should have a result", results != null && results.size() == 1);
            assertTrue("This call should have one response", results.get(0).getValues() != null && results.get(0).getValues().size() == 1);
        }

    }


}
