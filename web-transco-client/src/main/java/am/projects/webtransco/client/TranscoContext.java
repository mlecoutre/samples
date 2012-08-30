package am.projects.webtransco.client;

import am.projects.webtransco.client.model.TranscoDatastore;

import java.util.HashMap;

/**
 * User: mlecoutre
 * Date: 29/08/12
 * Time: 15:52
 */
public class TranscoContext {

    private HashMap<String, TranscoDatastore> datastores;
    private String cacheFileConfig;

    public TranscoContext() {
    }

    public TranscoContext(HashMap<String, TranscoDatastore> datastores, String cacheFileConfig) {
        this.datastores = datastores;
        this.cacheFileConfig = cacheFileConfig;
    }

    @Override
    public String toString() {
        return "TranscoContext{" +
                "datastores=" + datastores +
                ", cacheFileConfig='" + cacheFileConfig + '\'' +
                '}';
    }

    public HashMap<String, TranscoDatastore> getDatastores() {
        return datastores;
    }

    public void setDatastores(HashMap<String, TranscoDatastore> datastores) {
        this.datastores = datastores;
    }

    public String getCacheFileConfig() {
        return cacheFileConfig;
    }

    public void setCacheFileConfig(String cacheFileConfig) {
        this.cacheFileConfig = cacheFileConfig;
    }
}
