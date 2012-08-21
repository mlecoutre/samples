package am.projects.webtransco.client;

import java.util.List;

/**
 * User: mlecoutre
 * Date: 21/08/12
 * Time: 09:53
 */
public class ListResponse {
    private String functionName;
    private List<String> values;

    public ListResponse(){

    }

    public ListResponse(String functionName, List<String> values){
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
        return "ListResponse{" +
                "functionName='" + functionName + '\'' +
                ", values=" + values +
                '}';
    }
}
