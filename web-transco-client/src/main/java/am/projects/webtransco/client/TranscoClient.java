package am.projects.webtransco.client;

import am.projects.webtransco.client.model.ListCall;
import am.projects.webtransco.client.model.ListResponse;
import am.projects.webtransco.client.model.NoResultException;
import am.projects.webtransco.client.model.TranscoDatastore;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Singleton entry point for web-transco-client API.
 * This Class propose two static services callTransco and callTranscoWithCache that could be used
 * in any java program of via a webMethods service.
 *
 * @author mlecoutre
 */
public class TranscoClient {

    private static Logger logger = LoggerFactory.getLogger(TranscoClient.class.getName());

    public static final String MODE_STANDALONE = "modestandalone";
    public static final String MODE_WM = "modewm";
    public static final String MODE_DEFAULT = MODE_STANDALONE;
    private static final String CONFIGURATION_FILE = "transco-configuration.xml";

    private static TranscoClient instance = new TranscoClient();
    public String executionMode = MODE_DEFAULT;
    private ExecutionStrategy exeStrategy = null;
    private TranscoContext context = null;

    //default constructor
    public TranscoClient() {
        initConfiguration();
    }

    public static TranscoClient getInstance() {
        return instance;
    }


    /**
     * Read the configuration file (transco-configuration.xml to get list of datastore)
     * and inializare TranscoContext
     *
     * @return TranscoContext
     */
    public TranscoContext initConfiguration() {


        XMLConfiguration config = null;
        context = new TranscoContext();
        try {
            config = new XMLConfiguration(CONFIGURATION_FILE);


            List<HierarchicalConfiguration> datastores =
                    config.configurationsAt("standalone-configuration.datastore");
            HashMap<String, TranscoDatastore> mapDatastores = new HashMap<String, TranscoDatastore>();
            for (HierarchicalConfiguration dshc : datastores) {
                String alias = dshc.getString("[@alias]");
                String url = dshc.getString("[@url]");
                String user = dshc.getString("[@user]");
                String password = dshc.getString("[@password]");
                int timeout = dshc.getInt("[@timeout]");
                TranscoDatastore datastore = new TranscoDatastore(alias, url, user, password, timeout);
                mapDatastores.put(alias, datastore);
            }
            context.setDatastores(mapDatastores);
            logger.debug("InitConfiguration: found " + mapDatastores.size() + " data stores.");

        } catch (ConfigurationException e) {
            logger.error("ERROR initConfiguration", e);
        }
        return context;
    }

    /**
     * Regarding the executionMode given in parameter, we decide to use the
     * standalone or the embedded webMethods mode.
     *
     * @param executionMode MODE_STANDALONE || MODE_WM
     * @return execution strategy
     */
    public ExecutionStrategy initStrategy(String executionMode) {
        this.executionMode = executionMode;
        if (exeStrategy == null) {
            if (MODE_STANDALONE.equals(executionMode)) {
                exeStrategy = new StrategyExeStandalone();
            } else if (MODE_WM.equals(executionMode)) {
                exeStrategy = new StrategyExeWM();
            } else {
                //default strategy
                exeStrategy = new StrategyExeStandalone();
            }
        }
        return exeStrategy;
    }

    /**
     * @param dataStoreAliasName Transco_TBO, Transco_DOMCOM
     * @param throwException     boolean value
     * @param parameters         list of input parameters
     * @param defaultValues      list of default values
     * @return List<ListResponse>
     */
    public static List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException {

        try {

            return getInstance().getExeStrategy().callTransco(dataStoreAliasName, throwException, parameters, defaultValues);
        } catch (NoResultException nre) {
            if (throwException) {
                throw nre;
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                nre.printStackTrace(pw);
                String error = sw.toString() + "executionMode: " + TranscoClient.getInstance().executionMode;
                List<ListResponse> response = new ArrayList<ListResponse>();
                List<String> values = new ArrayList<String>();
                values.add(error);
            }
        }
        return null;
    }

    /**
     * @param dataStoreAliasName Transco_TBO, Transco_DOMCOM, Transco_BOSS
     * @param throwException     boolean value
     * @param parameters         list of input parameters
     * @param defaultValues      list of default values
     * @return List<ListResponse>
     */
    public static List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException {
        return callTranscoWithCache(dataStoreAliasName, "", throwException, parameters, defaultValues);

    }

    /**
     * @param dataStoreAliasName Transco_TBO, Transco_DOMCOM, Transco_BOSS
     * @param cacheName          optional
     * @param throwException     boolean value
     * @param parameters         list of input parameters
     * @param defaultValues      list of default values
     * @return List<ListResponse>
     */
    public static List<ListResponse> callTranscoWithCache(String dataStoreAliasName, String cacheName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) throws NoResultException {

        try {
            return TranscoClient.getInstance().getExeStrategy().callTranscoWithCache(dataStoreAliasName, cacheName, throwException, parameters, defaultValues);
        } catch (NoResultException nre) {
            if (throwException) {
                throw nre;
            } else {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                nre.printStackTrace(pw);
                String error = sw.toString() + "executionMode: " + TranscoClient.getInstance().executionMode;
                List<ListResponse> response = new ArrayList<ListResponse>();
                List<String> values = new ArrayList<String>();
                values.add(error);
            }
        }
        return null;
    }

    public TranscoContext getContext() {
        return context;
    }

    /**
     * Remove all element from cache
     *
     * @return true.
     */
    public static boolean cleanCache(String cacheName) {
        getInstance().getExeStrategy().retrieveCache(cacheName).removeAll();
        return true;
    }

    public ExecutionStrategy getExeStrategy() {
        if (exeStrategy == null) {
            ExecutionStrategy strategy = getInstance().initStrategy("");
        }
        return exeStrategy;
    }
}
