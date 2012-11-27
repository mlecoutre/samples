package org.mat.samples.mongodb.services;

import com.mongodb.*;
import com.mongodb.util.JSON;
import org.apache.commons.lang.ArrayUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 */
public class MonitorService {

    public static final String MONGO_SERVER = "dun-tst-devf01";
    public static final int MONGO_PORT = 27017;
    public static final String MONGO_DB = "mydb";
    public static final String COLLECTION_NAME = "stuResources";
    public static final String MEM_FREE = "free_memory";
    public static final String MEM_MAX = "max_memory";
    public static final String MEM_TOTAL = "total_memory";
    public static final String MEM_AVAILABLE = "available_memory";

    public static void main(String[] args) throws Exception {
        // purgeDB();
        //loadBatchInsert();
        // requestUsedConnection("DS_STEELUSER_COLDFUSION", "appcfm51");
        requestMemory(MEM_AVAILABLE, "appcfm51", System.out);
    }


    /**
     *
     */
    public static void loadBatchInsert() {
        String[] files_server1 = new String[]{
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-27",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-26",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-25",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-24",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-23",
                "http://appcfm51/log/WebSphere/AppServer/appcfm51Node/AS_STEELUSER/j2eeMonitoring.log"};
        String[] files_server2 = new String[]{
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-27",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-26",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-25",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-24",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log.2012-11-23",
                "http://appcfm52/log/WebSphere/AppServer/appcfm52Node/AS_STEELUSER/j2eeMonitoring.log"
        };
        for (int i = 0; i < files_server1.length; i++) {
            System.out.println("** START " + files_server1[i] + " ** ");
            batchInsert(files_server1[i], "appcfm51");
            System.out.println("** END " + files_server1[i] + " ** \n ");
        }

        for (int i = 0; i < files_server1.length; i++) {
            System.out.println("** START " + files_server1[i] + " ** ");
            batchInsert(files_server2[i], "appcfm52");
            System.out.println("** END " + files_server1[i] + " ** \n ");
        }
    }

    public static void requestMemory(String memory, String serverName, OutputStream writer) throws Exception {

        Mongo m = new Mongo(MONGO_SERVER, MONGO_PORT);
        DB db = m.getDB(MONGO_DB);
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        System.out.println("Nb elts: " + coll.count());

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


    public static void purgeDB() throws Exception {
        Mongo m = new Mongo(MONGO_SERVER, MONGO_PORT);
        DB db = m.getDB(MONGO_DB);
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        System.out.println("Nb elts before: " + coll.count());
        coll.drop();
        System.out.println("Nb elts after: " + coll.count());
    }


    public static void requestUsedConnection(String idObject, String serverName) throws Exception {

        Mongo m = new Mongo(MONGO_SERVER, MONGO_PORT);
        DB db = m.getDB(MONGO_DB);
        DBCollection coll = db.getCollection(COLLECTION_NAME);
        System.out.println("Nb elts: " + coll.count());

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
        BasicDBObject sortDBO = new BasicDBObject();
        sortDBO.put("timestamp", "-1");
        DBCursor cursor = coll.find(filter, fields).sort(sortDBO);

        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    /**
     * Batch load of J2EE Resources
     *
     * @param strUrl
     */
    public static void batchInsert(String strUrl, String serverName) {
        URL u;
        BufferedReader bufferedReader = null;

        try {
            u = new URL(strUrl);
            URLConnection yc = u.openConnection();
            bufferedReader = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            String line = null;
            Mongo m = new Mongo(MONGO_SERVER, MONGO_PORT);
            DB db = m.getDB(MONGO_DB);
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println("  >" + line);
                DBCollection coll = db.getCollection(COLLECTION_NAME);
                DBObject doc = (DBObject) JSON.parse(line);
                doc.put("server", serverName);
                coll.insert(doc);
            }

        } catch (IOException ioe) {
            System.out.println("Ouch - a FileNotFoundException happened.");
            ioe.printStackTrace();
            System.exit(1);
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ioe) {

                }
            }
        }
    }
}
