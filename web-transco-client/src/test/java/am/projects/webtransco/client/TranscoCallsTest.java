package am.projects.webtransco.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

/**
 * User: mlecoutre
 * Date: 27/08/12
 */
public class TranscoCallsTest {
    private static final String strServerIPAddress = "dun-tst-eai01";
    private static final String strDatabaseName = "TRANSCO_TBO";
    private static final String strUserName = "TRANSCO_TBO_User";
    private static final String strPassword = "sqluser";

    private Connection mConn = null;

    @Before
    public void initDBConnection() throws Exception {
        String url = "jdbc:sqlserver://" + strServerIPAddress + ":1433" +
                ";DatabaseName=" + strDatabaseName;
        //Loading the driver...
        //  Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");

        //Buiding a Connection
        mConn = DriverManager.getConnection(url, strUserName, strPassword);
        TranscoClient.getInstance().initStrategy(TranscoClient.MODE_STANDALONE);
        TranscoClient.getInstance().getExeStrategy().setConnection(mConn);

    }

    @After
    public void releaseDBConnection() throws Exception {
        if (mConn != null) {
            mConn.close();
        }
    }

    @Test
    public void testSimpleCall() throws Exception {
        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        params.add("APP");
        params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
        ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);
        calls.add(lc);
        List<ListResponse> results = null;

        results = TranscoClient.callTransco("Transco_TBO", true, calls, null);
        System.out.println("testSimpleCall: " + results.toString());
        assertTrue("This call should have a result", results != null && results.size() == 1);
        assertTrue("This call should have one response", results.get(0).getValues() != null && results.get(0).getValues().size() == 1);

    }

    @Test
    public void testThrowExceptionWithUnknownFunctionName() throws Exception {
        boolean isExceptionThrown = false;
        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        params.add("APP");
        params.add("MyUnknownFunction");
        ListCall lc = new ListCall("MyUnknownFunction", params);
        calls.add(lc);
        List<ListResponse> results = null;
        try {
            results = TranscoClient.callTransco("Transco_TBO", true, calls, null);
        } catch (NoResultException nre) {
            System.out.println("testThrowExceptionWithUnknownFunctionName: " + nre);
            isExceptionThrown = true;
            assertTrue("Exception message should indicate 'Unknown function'", nre.getMessage().startsWith("Unknown function"));
            assertTrue("Exception message should contain the name of the function given as input parameter", nre.getMessage().contains("MyUnknownFunction"));
        }
        assertTrue("An exception should be thrown", isExceptionThrown);
    }

    @Test
    public void testDontThrowExceptionWithUnknownFunction() {
        boolean isExceptionThrown = false;
        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        params.add("APP");
        params.add("MyUnknownFunction");
        ListCall lc = new ListCall("MyUnknownFunction", params);
        calls.add(lc);
        List<ListResponse> results = null;
        try {
            results = TranscoClient.callTransco("Transco_TBO", false, calls, null);
        } catch (NoResultException nre) {
            isExceptionThrown = true;
        }
        System.out.println("testDontThrowExceptionWithUnknownFunction: " + results);
        assertTrue("An exception shouldn't be thrown.", !isExceptionThrown);
        assertTrue("FunctionName should indicate the name of the function", results.get(0).getFunctionName().equals("MyUnknownFunction"));
        assertTrue("First result of return values should say UnknownFunction : <functionName>", results.get(0).getValues().get(0).contains("UnknownFunction : MyUnknownFunction"));

    }

    @Test
    public void testThrowExceptionWithIncorrectInput() {
        boolean isExceptionThrown = false;
        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        try {
            params.add("InvalidInput");
            params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
            ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);
            calls.add(lc);
            List<ListResponse> results = null;

            results = TranscoClient.callTransco("Transco_TBO", true, calls, null);
            System.out.println("testThrowExceptionWithIncorrectInput : " + results.toString());
        } catch (NoResultException nre) {
            System.out.println("testThrowExceptionWithIncorrectInput: " + nre);
            isExceptionThrown = true;
            assertTrue("Error msg should contains 'No result for function :'", nre.getMessage().contains("No result for function :"));
        }
        assertTrue("An exception should be thrown", isExceptionThrown);
    }

    @Test
    public void testDontThrowExceptionWithIncorrectInput() {
        boolean isExceptionThrown = false;
        List<String> params = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        List<ListResponse> results = null;
        try {
            params.add("InvalidInput");
            params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
            ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);
            calls.add(lc);


            results = TranscoClient.callTransco("Transco_TBO", false, calls, null);
            System.out.println("testDontThrowExceptionWithIncorrectInput : " + results.toString());
        } catch (NoResultException nre) {
            System.out.println("testDontThrowExceptionWithIncorrectInput: " + nre);
            isExceptionThrown = true;
            assertTrue("Error msg should contains 'No result for function :'", nre.getMessage().contains("No result for function :"));

        }
        assertTrue("An exception shouldn't be thrown", !isExceptionThrown);
        assertTrue("We should have one result.", results != null && results.size() == 1);

    }

    @Test
    public void testDefaultValues() throws Exception {
        List<String> params = new ArrayList<String>();
        List<String> defaultValues = new ArrayList<String>();
        List<ListCall> calls = new ArrayList<ListCall>();
        defaultValues.add("myDefaultValue");
        params.add("InvalidParam");
        params.add("ArcelorMittal.Logistic.Metris.LCE.EndPoint.BelvalWS.ReceiveLogisticEvent");
        ListCall lc = new ListCall("Metris.DetermineWebServiceUrl.METRISTransformTransco001", params);
        calls.add(lc);
        List<ListResponse> results = null;

        results = TranscoClient.callTransco("Transco_TBO", false, calls, defaultValues);
        System.out.println("testDefaultValues: " + results.toString());
        assertTrue("This call should have a result", results != null && results.size() == 1);
        assertTrue("This call should have one response", results.get(0).getValues() != null && results.get(0).getValues().size() == 1);

        assertTrue("The response should be 'myDefaultValue'", results.get(0).getValues().get(0).equals("myDefaultValue"));
    }

}
