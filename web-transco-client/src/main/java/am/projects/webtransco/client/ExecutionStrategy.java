package am.projects.webtransco.client;

import am.projects.webtransco.client.model.ListCall;
import am.projects.webtransco.client.model.ListResponse;
import am.projects.webtransco.client.model.NoResultException;
import net.sf.ehcache.Cache;

import java.sql.Connection;
import java.sql.SQLException;
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
    Connection retrieveConnection(String dataStoreAliasName) throws SQLException;

    /**
     * Use by webMethods to store the ref of the JDBC adapter connection
     * @param connection
     */
    void setConnection(Connection connection);

    /**
     *  Retrieve cache instance or create a standalone one if not available
     * @return   cache instance
     */
    Cache retrieveCache();

    /**
     *
     * @param dataStoreAliasName alias of the transco repository
     * @param throwException     boolean to know if we have to throw exception if no value are found
     * @param parameters         input parameters
     * @param defaultValues      default values to return if no value are found.
     * @return transco output values
     */
    List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException;

    /**
     *
     * @param dataStoreAliasName alias of the transco repository
     * @param throwException     boolean to know if we have to throw exception if no value are found
     * @param parameters         input parameters
     * @param defaultValues      default values to return if no value are found.
     * @return transco output values
     */
    List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException;
}
