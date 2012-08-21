package am.projects.webtransco.client;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton entry point for web-transco-client API.
 * This Class propose two static services callTransco and callTranscoWithCache that could be used
 * in any java program of via a webMethods service.
 *
 * @author mlecoutre
 */
public class TranscoClient {

    public static final String MODE_STANDALONE = "modestandalone";
    public static final String MODE_WM = "modewm";
    public static final String MODE_DEFAULT = MODE_STANDALONE;
    private static TranscoClient instance = new TranscoClient();
    public String executionMode = MODE_DEFAULT;
    private ExecutionStrategy exeStrategy = null;


    public static TranscoClient getInstance() {
        return instance;
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
                //TODO manage error
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
    public static List<ListResponse> callTransco(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) {

        try {
            return TranscoClient.getInstance().exeStrategy.callTransco(dataStoreAliasName, throwException, parameters, defaultValues);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String error = sw.toString() + "executionMode: " + TranscoClient.getInstance().executionMode;
            List<ListResponse> response = new ArrayList<ListResponse>();
            List<String> values = new ArrayList<String>();
            values.add(error);
        }
        return null;
    }

    /**
     * @param dataStoreAliasName Transco_TBO, Transco_DOMCOM
     * @param throwException     boolean value
     * @param parameters         list of input parameters
     * @param defaultValues      list of default values
     * @return List<ListResponse>
     */
    public static List<ListResponse> callTranscoWithCache(String dataStoreAliasName, boolean throwException, List<ListCall> parameters, List<String> defaultValues) {

        try {
            return TranscoClient.getInstance().exeStrategy.callTranscoWithCache(dataStoreAliasName, throwException, parameters, defaultValues);
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            String error = sw.toString() + "executionMode: " + TranscoClient.getInstance().executionMode;
            List<ListResponse> response = new ArrayList<ListResponse>();
            List<String> values = new ArrayList<String>();
            values.add(error);
        }
        return null;
    }

    /*
public static String callTranco(String param) {
   List<String> params = new ArrayList<String>();
   params.add("APP");
   params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
   ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);

   List<String> results = null;
   String error = "";
   try {
       results = TranscoClient.getInstance().exeStrategy.callTransco("Transco_TBO", false, lc, null);
   } catch (Exception e) {
       StringWriter sw = new StringWriter();
       PrintWriter pw = new PrintWriter(sw);
       e.printStackTrace(pw);
       error = sw.toString() + "executionMode: " + TranscoClient.getInstance().executionMode;
   }
   if (results == null) {
       return error;
   } else {
       return results.get(0);
   }
}
    */
    public ExecutionStrategy getExeStrategy() {
        return exeStrategy;
    }
}
