package am.projects.webtransco.client;

/**
 *  This class allow us to store the context of the JDBC database connection
 *  in order to reuse the webMethods JDBC adapter connection if needed.
 *  If not in a webMethods context, we try to use the file stored configuration.
 *
 * @author mlecoutre
 * Date: 20/08/12
 */
public class DataSourceContext {
    public static String datasourceURL = "Empty db context";


}
