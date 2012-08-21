package am.projects.webtransco.client;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 21/08/12
 * Time: 12:12
 */
public class TranscoXSLExt {

  // public static final int MIN_ARGS = 3;

    public static String callTransco(String datastoreAlias, String functionName, String thrEx, String  p1, String p2) {

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();

        values.add(p1);
        values.add(p2);

        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);

        //call the transcodification
        List<ListResponse> response = TranscoClient.callTransco(datastoreAlias, throwException, calls, defaultValues);

        // manage only one response at this time
        ListResponse lr = response.get(0);
        String res = lr.getValues().get(0);
        return  res;


    }
}
