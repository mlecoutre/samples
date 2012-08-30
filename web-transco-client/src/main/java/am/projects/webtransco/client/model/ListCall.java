package am.projects.webtransco.client.model;

import java.io.Serializable;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 21/08/12
 * Time: 09:53
 */
public class ListCall implements Serializable {
    private String functionName;
    private List<String> values;

    public ListCall(){

    }

    public ListCall(String functionName, List<String> values){
        this.functionName= functionName;
        this.values = values;
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

    @Override
    public String toString() {
        return "ListCall{" +
                "functionName='" + functionName + '\'' +
                ", values=" + values +
                '}';
    }
}
