package am.projects.webtransco.client;

import am.projects.webtransco.client.model.TranscoDatastore;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;


import java.util.HashMap;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 29/08/12
 * Time: 15:49
 */
public class ConfigurationTest {

    Logger logger = LoggerFactory.getLogger(ConfigurationTest.class.getName());

    @Test
    public void testConfiguration() {

        TranscoContext ctx = TranscoClient.getInstance().initConfiguration();
        logger.debug("testConfiguration: " + ctx);
        assertTrue("Configuration context should have 3 datastores", ctx.getDatastores().size() == 3);
        assertTrue(ctx.getDatastores().get("dsalias1").getPassword().equals("dspassword1"));
        assertTrue(ctx.getDatastores().get("dsalias1").getUrl().equals("dsurl1"));
        assertTrue(ctx.getDatastores().get("dsalias1").getUser().equals("dsuser1"));
        assertTrue(ctx.getDatastores().get("dsalias1").getTimeout() == 10);

    }
}
