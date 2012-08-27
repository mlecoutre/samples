package am.projects.webtransco.client;

import java.io.StringWriter;
import java.util.Formatter;
import java.util.List;

/**
 * Manage throwException of the transco API
 * User: mlecoutre
 * Date: 27/08/12
 */
public class NoResultException extends Exception {

    static final String EX_MSG_NO_RESULT = "No result for function : %s with values : %s";
    static final String EX_MSG_UNKNOWN_FCT = "Unknown function : %s (values: %s)"; // old return value was 'unknow'

    private String exMsg;
    private String functionName;
    private List<String> values;

    public NoResultException() {
    }

    public NoResultException(String exMsg, String functionName, List<String> values) {
        this.exMsg = exMsg;
        this.functionName = functionName;
        this.values = values;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }


    public static String listStringToString(List<String> values) {
        StringBuffer buff = new StringBuffer();
        for (String v : values) {
            buff.append(v).append(",");
        }
        return buff.toString();
    }

    public String getMessage() {
        StringWriter writer = new StringWriter();
        Formatter formatter = new Formatter();

        return formatter.format(exMsg, functionName, listStringToString(values)).toString();
    }

    @Override
    public String toString() {
        return "NoResultException{" +
                "exMsg='" + exMsg + '\'' +
                ", functionName='" + functionName + '\'' +
                ", values=" + values +
                '}';
    }
}
