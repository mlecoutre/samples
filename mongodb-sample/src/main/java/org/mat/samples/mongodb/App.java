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

// or dun-tst-devf01
        Mongo m = new Mongo( "dun-tst-devf01" , 27017 );
// or, to connect to a replica set, supply a seed list of members
//        Mongo m = new Mongo(Arrays.asList(new ServerAddress("localhost", 27017),
//                new ServerAddress("localhost", 27018),
//                new ServerAddress("localhost", 27019)));

        DB db = m.getDB( "mydb" );

        BasicDBObject doc = new BasicDBObject();

        doc.put("firstname", "matthieu");
        doc.put("lastname", "lecoutre");


//        BasicDBObject info = new BasicDBObject();
//
//        info.put("x", 203);
//        info.put("y", 102);
//
//        doc.put("info", info);

        DBCollection coll = db.getCollection("persons");

        coll.insert(doc);
        DBObject myDoc = coll.findOne();
        System.out.println(myDoc);



    }
}
