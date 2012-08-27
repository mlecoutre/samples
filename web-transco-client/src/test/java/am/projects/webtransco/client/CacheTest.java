package am.projects.webtransco.client;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: E010925
 * Date: 22/08/12
 * Time: 10:33
 * To change this template use File | Settings | File Templates.
 */
public class CacheTest {


    @Test
    public void testCreateCacheTranscoKey(){
        List<String> values = new ArrayList<String>();
        values.add("p1");
        values.add(null);
        values.add(" ");
        values.add("p2");
        ListCall lc = new ListCall("functionName", values);
        String key = AbstractExecutionStrategy.createCacheTranscoKey(lc);
        System.out.println("key:"+key);
        assertTrue("The cache key has to be correct", "functionName|p1| ||p2".equals(key) );
    }
}
