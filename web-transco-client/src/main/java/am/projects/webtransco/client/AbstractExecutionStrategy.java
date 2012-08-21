package am.projects.webtransco.client;

import com.wm.util.JournalLogger;
import net.sf.ehcache.Cache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * This API is used either in standalone mode, either within a webMethods Integration Server.
 * In this last case, we get the connection within the JDBC Adapter pool, and try to retrieve cache configured
 * in the admin console.
 * User: mlecoutre
 * Date: 21/08/12
 */
public abstract class AbstractExecutionStrategy implements ExecutionStrategy {

    public static final ThreadLocal userThreadLocal = new ThreadLocal();


    /**
     * @param dataStoreAliasName datastore alias (e.g. Transco_DOMCOM)
     * @param throwException     boolean if the transco should return an exception if no result are found
     * @param parameters         input parameters
     * @param defaultValues      default values if no result
     * @return output result of the transco
     */
    @Override
    public List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws Exception {
        List<ListResponse> response = new ArrayList<ListResponse>();
        for (ListCall lc : parameters) {
            //TODO check if lc.getValues < 9

            List<String> results = new ArrayList<String>();
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

            JournalLogger.log(JournalLogger.INFO, JournalLogger.FAC_FLOW_SVC, JournalLogger.INFO, "request : " + buffer.toString());
            PreparedStatement pstmt = connection.prepareStatement(buffer.toString());

            pstmt.execute();

            ResultSet rs = pstmt.getResultSet();


            rs.next();
            String mResult = rs.getString("TRAN_VALEURCIBLE1");
            results.add(mResult);

            ListResponse lr = new ListResponse();
            lr.setFunctionName(lc.getFunctionName());
            lr.setValues(results);
            response.add(lr);

        }
        return response;
    }

    /**
     * transco output values
     *
     * @param dataStoreAliasName
     * @param throwException
     * @param parameters
     * @param defaultValues
     * @return transco output values
     */
    @Override
    public List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) {
        Cache cache = retrieveCache();

        return null;
    }

    public Connection getConnection() {
        return (Connection) userThreadLocal.get();
    }

    public void setConnection(Connection connection) {
        userThreadLocal.set(connection);
    }

}
