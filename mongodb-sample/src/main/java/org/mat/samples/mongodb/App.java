package org.mat.samples.mongodb;

import com.mongodb.*;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )  throws Exception

    {

// or
        Mongo m = new Mongo( "localhost" , 27017 );
// or, to connect to a replica set, supply a seed list of members
//        Mongo m = new Mongo(Arrays.asList(new ServerAddress("localhost", 27017),
//                new ServerAddress("localhost", 27018),
//                new ServerAddress("localhost", 27019)));

        DB db = m.getDB( "mydb" );

        BasicDBObject doc = new BasicDBObject();

        doc.put("name", "MongoDB");
        doc.put("type", "database");
        doc.put("count", 1);

        BasicDBObject info = new BasicDBObject();

        info.put("x", 203);
        info.put("y", 102);

        doc.put("info", info);

        DBCollection coll = db.getCollection("testCollection");

        coll.insert(doc);
        DBObject myDoc = coll.findOne();
        System.out.println(myDoc);



    }
}
