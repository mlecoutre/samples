package org.mat.samples.mongodb.services;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.mat.samples.mongodb.Constants;
import org.mat.samples.mongodb.listener.MongoListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 */
public class MonitorService implements Constants {

    private static MongoListener ml;

    public static void main(String[] args) throws Exception {
        ml = new MongoListener(MONGO_SERVER, MONGO_PORT, MONGO_DB);
        ml.contextInitialized(null);
        purgeDB("SteelUser");
        loadBatchInsert();
        // requestUsedConnection("DS_STEELUSER_COLDFUSION", "appcfm51");
        //requestMemory(MEM_AVAILABLE, "appcfm51", System.out);
    }


    /**
     *
     */
    public static void loadBatchInsert() {
        String[] files_server1 = new String[]{
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-29",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-28",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-27",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-26",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-25",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log"};
        String[] files_server2 = new String[]{
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-29",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-28",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-27",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-26",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-25",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log"
        };
        for (int i = 0; i < files_server1.length; i++) {
            System.out.println("** START " + files_server1[i] + " ** ");
            batchInsert(files_server1[i], "SteelUser", "appcfm51", "AS_STEELUSER");
            System.out.println("** END " + files_server1[i] + " ** \n ");
        }

        for (int i = 0; i < files_server1.length; i++) {
            System.out.println("** START " + files_server1[i] + " ** ");
            batchInsert(files_server2[i], "SteelUser", "appcfm52", "AS_STEELUSER");
            System.out.println("** END " + files_server1[i] + " ** \n ");
        }
    }

    /**
     * listDataSources
     *
     * @param applicationName Mongo collection to request
     * @param serverName      server name
     * @param asName          AS Name
     * @return List of dataSources
     */
    public static List<String> listDataSources(String applicationName, String serverName, String asName) {
        DB db = MongoListener.getMongoDB();
        DBCollection coll = db.getCollection(applicationName);
        BasicDBObject filter = new BasicDBObject();
        filter.put("type", "was.pool.ds");

        if (serverName != null)
            filter.put("server", serverName);

        if (asName != null)
            filter.put("asName", asName);

        List mList = coll.distinct("id", filter);
        System.out.println("listDataSources: " + mList.size());
        return mList;
    }

    /**
     * Write directly into the output stream the JS array for chart input data
     *
     * @param memory          type of memory @see org.mat.samples.mongodb.Constants
     * @param applicationName Mongo collection to be requested.
     * @param serverName      server Name
     * @param asName          Application server name
     * @param writer          output writer
     * @throws Exception return all error
     *                   <p/>
     *                   TODO manage cache or local file storage to avoid to call DB for each request.
     */
    public static void requestMemory(String memory, String applicationName, String serverName, String asName, OutputStream writer) throws Exception {


        DB db = MongoListener.getMongoDB();
        DBCollection coll = db.getCollection(applicationName);
        BasicDBObject fields = new BasicDBObject();
        fields.put("sizemb", 1);
        fields.put("timestamp", 1);
        fields.put("server", 1);
        fields.put("_id", 0);

        BasicDBObject filter = new BasicDBObject();
        filter.put("id", memory);
        if (serverName != null) {
            filter.put("server", serverName);
        }
        if (asName != null) {
            filter.put("asName", asName);
        }
        BasicDBObject sortDBO = new BasicDBObject();
        sortDBO.put("timestamp", "-1");
        DBCursor cursor = coll.find(filter, fields).sort(sortDBO);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            writer.write("[\n".getBytes());

            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String ts = (String) obj.get("timestamp");
                long milli = (sdf.parse(ts)).getTime();
                String size = (String) obj.get("sizemb");
                writer.write(String.format("[%s, %s]", milli, size).getBytes());
                if (cursor.hasNext())
                    writer.write(", \n".getBytes());
            }
            writer.write("]\n".getBytes());
        } finally {
            cursor.close();
        }
    }

    /**
     * Clear content of the collection
     *
     * @param applicationName Mongo collection to purge
     * @throws Exception
     */
    public static void purgeDB(String applicationName) throws Exception {
        DB db = MongoListener.getMongoDB();
        DBCollection coll = db.getCollection(applicationName);
        System.out.println("Nb elements before: " + coll.count());
        coll.drop();
        System.out.println("Nb elements after: " + coll.count());
    }

    /**
     * @param applicationName Mongo collection to request
     * @param serverName      filter on serverName (can be null)
     * @param asName          filter on asName (can be null)
     * @param writer          output writer
     * @throws Exception
     */
    public static void requestTotalThreads(String applicationName, String serverName, String asName, OutputStream writer) throws Exception {

        DB db = MongoListener.getMongoDB();
        DBCollection coll = db.getCollection(applicationName);

        BasicDBObject fields = new BasicDBObject();
        fields.put("count", 1);
        fields.put("timestamp", 1);
        fields.put("server", 1);
        fields.put("_id", 0);

        BasicDBObject filter = new BasicDBObject();
        filter.put("id", THREADS);
        if (serverName != null) {
            filter.put("server", serverName);
        }
        if (asName != null) {
            filter.put("asName", asName);
        }
        BasicDBObject sortDBO = new BasicDBObject();
        sortDBO.put("timestamp", "-1");
        DBCursor cursor = coll.find(filter, fields).sort(sortDBO);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            writer.write("[\n".getBytes());
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String ts = (String) obj.get("timestamp");
                long milli = (sdf.parse(ts)).getTime();
                String used = (String) obj.get("count");
                writer.write(String.format("[%s, %s]", milli, used).getBytes());
                if (cursor.hasNext())
                    writer.write(", \n".getBytes());
            }
            writer.write("]\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    /**
     * Write the used connection of a specific datasource on an output stream
     *
     * @param idObject        name of the dataSource
     * @param applicationName Mongo Collection to request
     * @param serverName      add filter on server (can be null)
     * @param asName          add filter on seName (can be null)
     * @param writer          output stream writer
     * @throws Exception TODO manage cache or local file storage to avoid to call DB for each request.
     */
    public static void requestUsedConnection(String idObject, String applicationName, String serverName, String asName, OutputStream writer) throws Exception {

        DB db = MongoListener.getMongoDB();
        DBCollection coll = db.getCollection(applicationName);

        BasicDBObject fields = new BasicDBObject();
        fields.put("used", 1);
        fields.put("timestamp", 1);
        fields.put("server", 1);
        fields.put("_id", 0);

        BasicDBObject filter = new BasicDBObject();
        filter.put("id", idObject);
        if (serverName != null) {
            filter.put("server", serverName);
        }
        if (asName != null) {
            filter.put("asName", asName);
        }
        BasicDBObject sortDBO = new BasicDBObject();
        sortDBO.put("timestamp", "-1");
        DBCursor cursor = coll.find(filter, fields).sort(sortDBO);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            writer.write("[\n".getBytes());
            while (cursor.hasNext()) {
                DBObject obj = cursor.next();
                String ts = (String) obj.get("timestamp");
                long milli = (sdf.parse(ts)).getTime();
                String used = (String) obj.get("used");
                writer.write(String.format("[%s, %s]", milli, used).getBytes());
                if (cursor.hasNext())
                    writer.write(", \n".getBytes());
            }
            writer.write("]\n".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
    }

    /**
     * Batch load of J2EE Resources
     *
     * @param strUrl          file URL to load
     * @param applicationName is used to gather all AS from a same application. One mongo collection per applicationName
     * @param serverName      server Name
     * @param asName          AS name
     */
    public static long batchInsert(String strUrl, String applicationName, String serverName, String asName) {
        URL u;
        BufferedReader bufferedReader = null;
        long nbElts = 0;

        try {
            u = new URL(strUrl);
            URLConnection yc = u.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String line = null;
            DB db = MongoListener.getMongoDB();
            DBCollection coll = db.getCollection(applicationName);
            while ((line = bufferedReader.readLine()) != null) {

                DBObject doc = (DBObject) JSON.parse(line);
                doc.put("server", serverName);
                doc.put("asName", asName);
                coll.insert(doc);
            }
            nbElts = coll.count();

        } catch (IOException ioe) {
            System.out.println("Ouch - a FileNotFoundException happened.");
            ioe.printStackTrace();
            System.exit(1);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    //
                }
            }
        }
        return nbElts;
    }
}
