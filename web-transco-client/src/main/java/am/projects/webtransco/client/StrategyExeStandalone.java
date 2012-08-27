package am.projects.webtransco.client;

import net.sf.ehcache.Cache;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * User: mlecoutre
 * Date: 21/08/12
 */
public class StrategyExeStandalone extends AbstractExecutionStrategy {

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws SQLException {
        return getConnection();
    }

    @Override
    public Cache retrieveCache() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
