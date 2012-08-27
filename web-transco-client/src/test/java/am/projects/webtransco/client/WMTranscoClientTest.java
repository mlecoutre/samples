package am.projects.webtransco.client;

import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * User: mlecoutre
 * Date: 24/08/12
 * Time: 15:11
 */
public class WMTranscoClientTest {

    @Test
    public void testWrapInputs() {

        // input
        IData input = IDataFactory.create();
        IDataCursor inputCursor = input.getCursor();

        // listCall
        IData[] listCall = new IData[1];
        listCall[0] = IDataFactory.create();
        IDataCursor listCallCursor = listCall[0].getCursor();
        IDataUtil.put(listCallCursor, "FunctionName", "FunctionName");
        String[] listValues = new String[1];
        listValues[0] = "listValues";
        IDataUtil.put(listCallCursor, "listValues", listValues);
        listCallCursor.destroy();
        IDataUtil.put(inputCursor, "listCall", listCall);
        IDataUtil.put(inputCursor, "throwException", "TRUE");
        IDataUtil.put(inputCursor, "aliasName", "aliasName");
        String[] defaultResultValue = new String[1];
        defaultResultValue[0] = "defaultResultValue";
        IDataUtil.put(inputCursor, "defaultResultValue", defaultResultValue);
        inputCursor.destroy();

        WMInput wmInput = WMTranscoClient.wrapInputs(input);

        assertTrue("wmInput shouldn't be null", wmInput != null);
        assertTrue("throwException should be set to TRUE", wmInput.isThrowException());
        assertTrue("DatastoreAlias Name should be correct", "aliasName".equals(wmInput.getDatastoreAlias()));
        assertTrue("ListCalls should have one element", wmInput.getCalls() != null && wmInput.getCalls().size() == 1);

    }

    @Test
    public void testWrapOutput() {
        List<ListResponse> listResponses = new ArrayList<ListResponse>();
        List<String> values = new ArrayList<String>();
        values.add("v1");
        values.add("v2");
        ListResponse lr = new ListResponse("myFunctionName", values);
        listResponses.add(lr);
        System.out.println("ListResponse: " + lr);
        IData pipeline = WMTranscoClient.wrapOutputs(listResponses);

        assertTrue("Output pipeline shouldn't be null", pipeline != null);

        IDataCursor inputCursor = pipeline.getCursor();

        IData[] responses = IDataUtil.getIDataArray(inputCursor, "listResponse");
        assertTrue("Output pipeline should have one response", responses != null && responses.length == 1);

        IDataCursor ic = responses[0].getCursor();
        assertTrue("FunctionName  should be set", "myFunctionName".equals(IDataUtil.getString(ic, "FunctionName")));
        assertTrue("We should have two values", IDataUtil.getStringArray(ic, "listValues").length == 2);
        ic.destroy();
        inputCursor.destroy();


    }
}
