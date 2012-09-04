package am.projects.webtransco.client;

import am.projects.webtransco.client.model.ListCall;
import am.projects.webtransco.client.model.ListResponse;
import am.projects.webtransco.client.model.NoResultException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

/**
 * This API is used either in standalone mode, either within a webMethods Integration Server.
 * In this last case, we get the connection within the JDBC Adapter pool, and try to retrieve cache configured
 * in the admin console.
 * User: mlecoutre
 * Date: 21/08/12
 */
public abstract class AbstractExecutionStrategy implements ExecutionStrategy {

    //Used to store the SQL connection for parallel execution (required for wm)
    public static final ThreadLocal userThreadLocal = new ThreadLocal();
    private static Logger logger = LoggerFactory.getLogger(AbstractExecutionStrategy.class.getName());


    /**
     * @param dataStoreAliasName datastore alias (e.g. Transco_DOMCOM)
     * @param throwException     boolean if the transco should return an exception if no result are found
     * @param parameters         input parameters
     * @param defaultValues      default values if no result
     * @return output result of the transco
     */
    @Override
    public List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException {
        List<ListResponse> response = new ArrayList<ListResponse>();
        int index = 0;
        for (ListCall lc : parameters) {
            //TODO check if lc.getValues < 9
            String defaultValue = null;
            if (defaultValues != null && defaultValues.size() > index) {
                defaultValue = defaultValues.get(index);
            }
            ListResponse lr = callUnikTransco(dataStoreAliasName, throwException, lc, defaultValue);
            response.add(lr);
            index++;
        }
        return response;
    }

    /**
     * @param dataStoreAliasName alias of the transco repository
     * @param throwException     boolean to know if we have to throw exception if no value are found
     * @param lc                 listCall
     * @param defaultValue       default values to return if no value are found.
     * @return transco output values
     */
    private ListResponse callUnikTransco(String dataStoreAliasName, boolean throwException, ListCall lc, String defaultValue) throws NoResultException {
        List<String> results = new ArrayList<String>();
        String mResult = null;
        try {
            Connection connection = retrieveConnection(dataStoreAliasName);

            StringBuffer buffer = new StringBuffer("exec tra_Routage1_10 "
            ).append("@VAR_1='").append(lc.getFunctionName()).append("'");

            int i = 2;
            for (String param : lc.getValues()) {
                buffer.append(", @VAR_").append(i++).append("='").append(param).append("'");
            }
            //complete with blank all stored procedure parameters
            for (int j = i; j <= 10; j++) {
                buffer.append(", @VAR_").append(j).append("=''");
            }

            PreparedStatement pstmt = connection.prepareStatement(buffer.toString());

            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();
            boolean hasNext = rs.next();
            if (hasNext) {
                mResult = rs.getString(1);
            } else {
                //assign default value if given in parameter
                if (defaultValue != null) {
                    mResult = defaultValue;
                } else if (throwException) {        // invalid input
                    throw new NoResultException(NoResultException.EX_MSG_NO_RESULT, lc.getFunctionName(), lc.getValues());
                } else {
                    Formatter formatter = new Formatter();
                    mResult = formatter.format(NoResultException.EX_MSG_NO_RESULT, lc.getFunctionName(), NoResultException.listStringToString(lc.getValues())).toString();
                }
            }
            if (mResult == null || mResult.startsWith("UnknownFunction")) {
                if (throwException) {
                    throw new NoResultException(NoResultException.EX_MSG_UNKNOWN_FCT, lc.getFunctionName(), lc.getValues());
                } else {
                    mResult = "Unknown Function : " + lc.getFunctionName();
                }
            }

            results.add(mResult);
        } catch (SQLException se) {
            logger.error("SQL Exception", se);
        }

        ListResponse lr = new ListResponse();
        lr.setFunctionName(lc.getFunctionName());
        lr.setValues(results);
        return lr;
    }


    /**
     * @param lc input parameters
     * @return cache transco key
     */
    public static String createCacheTranscoKey(ListCall lc) {
        StringBuffer key = new StringBuffer();

        if (lc.getValues() != null && lc.getValues().size() > 0 && lc.getFunctionName() != null) {
            key.append(lc.getFunctionName().trim());
            for (String p : lc.getValues()) {
                if (p == null) {
                    key.append("|").append(" ");
                } else {
                    key.append("|").append(p.trim());
                }
            }
        }
        return key.toString();
    }

    /**
     * transco output values
     *
     * @param dataStoreAliasName alias of the transco repository
     * @param throwException     boolean to know if we have to throw exception if no value are found
     * @param parameters         input parameters
     * @param defaultValues      default values to return if no value are found.
     * @return transco output values
     */
    @Override
    public List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException {
        Cache cache = retrieveCache();
        List<ListResponse> lr = new ArrayList<ListResponse>();
        int index = 0;

        for (ListCall lc : parameters) {
            ListResponse response = null;
            if (cache == null) {

                logger.warn("WARNING: no cache available.");
                String defaultValue = null;
                if (defaultValues != null && defaultValues.size() > index) {
                    defaultValue = defaultValues.get(index);
                }
                response = callUnikTransco(dataStoreAliasName, throwException, lc, null);
            } else {
                String key = createCacheTranscoKey(lc);
                Element elt = cache.get(key);

                if (elt == null) {
                    response = callUnikTransco(dataStoreAliasName, throwException, lc, null);
                    elt = new Element(key, response);
                    cache.put(elt);
                } else {
                    response = (ListResponse) elt.getValue();
                }
            }
            lr.add(response);
            index++;
        }
        return lr;
    }

    public Connection getConnection() {
        return (Connection) userThreadLocal.get();
    }

    public void setConnection(Connection connection) {
        userThreadLocal.set(connection);
    }

}
