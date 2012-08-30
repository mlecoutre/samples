package am.projects.webtransco.client.model;

/**
 * User: mlecoutre
 * Date: 30/08/12
 * Time: 10:48
 */
public class TranscoDatastore {

    private String alias;
    private String url;
    private String user;
    private transient String password;
    private int timeout;

    public TranscoDatastore(String alias, String url, String user, String password, int timeout) {
        this.alias = alias;
        this.url = url;
        this.user = user;
        this.password = password;
        this.timeout = timeout;


    }

    @Override
    public String toString() {
        return "TranscoDatastore{" +
                "alias='" + alias + '\'' +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", timeout=" + timeout +
                '}';
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
