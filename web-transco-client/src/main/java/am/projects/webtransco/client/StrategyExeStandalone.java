package am.projects.webtransco.client;

import net.sf.ehcache.Cache;

import java.sql.Connection;

/**
 * User: mlecoutre
 * Date: 21/08/12
 */
public class StrategyExeStandalone extends AbstractExecutionStrategy {

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Cache retrieveCache() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
