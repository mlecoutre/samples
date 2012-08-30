package am.projects.webtransco.client;

import am.projects.webtransco.client.model.TranscoDatastore;
import net.sf.ehcache.Cache;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * User: mlecoutre
 * Date: 21/08/12
 */
public class StrategyExeStandalone extends AbstractExecutionStrategy {

    @Override
    public Connection retrieveConnection(String dataStoreAliasName) throws SQLException {
        TranscoContext ctx = TranscoClient.getInstance().getContext();
        TranscoDatastore datastore = ctx.getDatastores().get(dataStoreAliasName);
        //Loading the driver...
        //  Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

        //Buiding a Connection
       Connection mConn = DriverManager.getConnection(datastore.getUrl(), datastore.getUser(), datastore.getPassword());

        return mConn;
    }

    @Override
    public Cache retrieveCache() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
