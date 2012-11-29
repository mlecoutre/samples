package org.mat.samples.mongodb.services;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mat.samples.mongodb.listener.MongoListener;

import static org.junit.Assert.*;

import java.util.List;

/**
 * User: E010925
 * Date: 27/11/12
 * Time: 14:56
 */
public class MonitorServiceTest {
    private static final String MONGO_SERVER = "dun-tst-devf01";
    private static final int MONGO_PORT = 27017;
    private static final String MONGO_DB = "mydbTest";
    private static final String RESOURCE_FILE = "/j2eeMonitoring.log";
    private static final String SERVER_NAME = "appcfm51";
    public static final String AS_NAME = "AS_STEELUSER";
    public static final String APPLICATION_NAME = "SteelUserTest";

    private MongoListener ml = new MongoListener(MONGO_SERVER, MONGO_PORT, MONGO_DB);

    @Before
    public void setUp() throws Exception {
        ml.contextInitialized(null);
        MonitorService.batchInsert(MongoListener.class.getResource(RESOURCE_FILE).toExternalForm(), APPLICATION_NAME, SERVER_NAME, AS_NAME);
    }

    @After
    public void tearDown() throws Exception {
        MonitorService.purgeDB(APPLICATION_NAME);
        ml.contextDestroyed(null);
    }

    @Test
    public void testDistinctDataSource() {
        List<String> mList = MonitorService.listDataSources(APPLICATION_NAME, SERVER_NAME, AS_NAME);
        System.out.println("testDistinctDataSource : " + mList);
        assertTrue("We should have 11 DS in DB ", mList.size() == 11);
    }

    @Test
    public void testRequestUsedConnection() throws Exception {
        MonitorService.requestUsedConnection("DS_STEELUSER_MASTER", APPLICATION_NAME, SERVER_NAME, AS_NAME, System.out);
    }

    @Test
    public void testRequestTotalThreads() throws Exception {
        MonitorService.requestTotalThreads(APPLICATION_NAME, SERVER_NAME, AS_NAME, System.out);
    }

    @Test

    public void testGetServerInfo() {
        String[] array = "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-26".split("/");
        System.out.println(array[2] + "/" + array[array.length - 2]);
    }
}
