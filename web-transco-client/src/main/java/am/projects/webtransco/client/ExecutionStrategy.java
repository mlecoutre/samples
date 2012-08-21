package am.projects.webtransco.client;

import net.sf.ehcache.Cache;

import java.sql.Connection;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 21/08/12
 * Time: 10:08
 */
public interface ExecutionStrategy {

    /**
     * @param dataStoreAliasName  name of the datastore alias
     * @return SQLConnection
     */
    Connection retrieveConnection(String dataStoreAliasName) throws Exception;

    /**
     * Use by webMethods to store the ref of the JDBC adapter connection
     * @param connection
     */
    void setConnection(Connection connection);

    /**
     *
     * @return   cache instance
     */
    Cache retrieveCache();

    /**
     *
     * @param dataStoreAliasName
     * @param throwException
     * @param parameters
     * @param defaultValues
     * @return
     */
    List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws Exception;

    /**
     *
     * @param dataStoreAliasName
     * @param throwException
     * @param parameters
     * @param defaultValues
     * @return
     */
    List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues);
}
