package am.projects.webtransco.client;

import java.util.ArrayList;
import java.util.List;

/**
 * JAVA Extension for XSLT
 * User: mlecoutre
 * Date: 21/08/12
 * Time: 12:12
 */
public class TranscoXSLExt {

    public static String callTransco(String datastoreAlias, String functionName, String thrEx, String  p1) throws NoResultException{

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();
        values.add(p1);
        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);

        //call the transcodification
        String res = runTransco(datastoreAlias, functionName, throwException, defaultValues, values);
        return res;
    }
    public static String callTransco(String datastoreAlias, String functionName, String thrEx, String  p1, String p2) throws NoResultException{

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();

        values.add(p1);
        values.add(p2);

        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);

        //call the transcodification
        String res = runTransco(datastoreAlias, functionName, throwException, defaultValues, values);
        return res;
    }
    public static String callTransco(String datastoreAlias, String functionName, String thrEx, String  p1, String p2, String p3) throws NoResultException{
        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();
        values.add(p1);
        values.add(p2);
        values.add(p3);
        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);
        //call the transcodification
        String res = runTransco(datastoreAlias, functionName, throwException, defaultValues, values);
        return res;
    }
    public static String callTransco(String datastoreAlias, String functionName, String thrEx, String  p1, String p2, String p3,String p4) throws NoResultException{
        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();
        values.add(p1);
        values.add(p2);
        values.add(p3);
        values.add(p4);
        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);
        //call the transcodification
        String res = runTransco(datastoreAlias, functionName, throwException, defaultValues, values);
        return res;
    }

    public static String callTranscoWithCache(String datastoreAlias, String functionName, String thrEx, String  p1)throws NoResultException {
        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();
        values.add(p1);
        String res = runTranscoWithCache(datastoreAlias, functionName, throwException, defaultValues, values);
        return  res;


    }
    public static String callTranscoWithCache(String datastoreAlias, String functionName, String thrEx, String  p1, String p2)throws NoResultException {

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();

        values.add(p1);
        values.add(p2);

        String res = runTranscoWithCache(datastoreAlias, functionName, throwException, defaultValues, values);
        return  res;


    }
    public static String callTranscoWithCache(String datastoreAlias, String functionName, String thrEx, String  p1, String p2, String p3)throws NoResultException {

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();

        values.add(p1);
        values.add(p2);
        values.add(p3);

        String res = runTranscoWithCache(datastoreAlias, functionName, throwException, defaultValues, values);
        return  res;
    }
    public static String callTranscoWithCache(String datastoreAlias, String functionName, String thrEx, String  p1, String p2, String p3, String p4)throws NoResultException {

        boolean throwException = "TRUE".equalsIgnoreCase(thrEx);
        List<String> defaultValues = null; //not yet managed
        List<String> values = new ArrayList<String>();

        values.add(p1);
        values.add(p2);
        values.add(p3);
        values.add(p4);

        String res = runTranscoWithCache(datastoreAlias, functionName, throwException, defaultValues, values);
        return  res;
    }


    private static String runTransco(String datastoreAlias, String functionName, boolean throwException, List<String> defaultValues, List<String> values) throws NoResultException {
        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);

        //call the transcodification
        List<ListResponse> response = TranscoClient.callTransco(datastoreAlias, throwException, calls, defaultValues);

        // manage only one response at this time
        ListResponse lr = response.get(0);
        return lr.getValues().get(0);
    }
    private static String runTranscoWithCache(String datastoreAlias, String functionName, boolean throwException, List<String> defaultValues, List<String> values) throws NoResultException {
        ListCall lc = new ListCall(functionName, values);
        List<ListCall> calls = new ArrayList<ListCall>();
        calls.add(lc);

        //call the transcodification
        List<ListResponse> response = TranscoClient.callTranscoWithCache(datastoreAlias, throwException, calls, defaultValues);

        // manage only one response at this time
        ListResponse lr = response.get(0);
        return lr.getValues().get(0);
    }


}
