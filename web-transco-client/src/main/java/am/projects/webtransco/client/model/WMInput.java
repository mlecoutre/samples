package am.projects.webtransco.client.model;

import java.util.List;

/**
 * User: mlecoutre
 * Date: 24/08/12
 */
public class WMInput {

    private List<ListCall> calls;
    private List<String> defaultValues;
    private String datastoreAlias;
    private boolean throwException;

    public WMInput(List<ListCall> calls,  List<String> defaultValues, String datastoreAlias, boolean throwException) {
        this.calls = calls;
        this.defaultValues = defaultValues;
        this.datastoreAlias = datastoreAlias;
        this.throwException = throwException;
    }



    public List<ListCall> getCalls() {
        return calls;
    }

    public void setCalls(List<ListCall> calls) {
        this.calls = calls;
    }


    public String getDatastoreAlias() {
        return datastoreAlias;
    }

    public void setDatastoreAlias(String datastoreAlias) {
        this.datastoreAlias = datastoreAlias;
    }

    public boolean isThrowException() {
        return throwException;
    }

    public void setThrowException(boolean throwException) {
        this.throwException = throwException;
    }

    public List<String> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(List<String> defaultValues) {
        this.defaultValues = defaultValues;
    }

    @Override
    public String toString() {
        return "WMInput{" +
                "calls=" + calls +
                ", defaultValues=" + defaultValues +
                ", datastoreAlias='" + datastoreAlias + '\'' +
                ", throwException=" + throwException +
                '}';
    }
}
