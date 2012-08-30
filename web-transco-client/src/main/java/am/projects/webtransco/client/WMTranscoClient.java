package am.projects.webtransco.client;

import am.projects.webtransco.client.model.ListCall;
import am.projects.webtransco.client.model.ListResponse;
import am.projects.webtransco.client.model.NoResultException;
import am.projects.webtransco.client.model.WMInput;
import com.wm.app.b2b.server.ServiceException;
import com.wm.data.IData;
import com.wm.data.IDataCursor;
import com.wm.data.IDataFactory;
import com.wm.data.IDataUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: E010925
 * Date: 24/08/12
 * Time: 10:15
 */
public class WMTranscoClient {

    /**
     * Allow to map IData structure to the default API Format input.
     *
     * @param pipeline webMethods IData Structure
     * @return translated WMInput
     */
    static WMInput wrapInputs(IData pipeline) {

        // pipeline
        IDataCursor pipelineCursor = pipeline.getCursor();

        // listCall
        IData[] listCall = IDataUtil.getIDataArray(pipelineCursor, "listCall");
        List<ListCall> calls = new ArrayList<ListCall>();
        if (listCall != null) {
            for (IData aListCall : listCall) {
                IDataCursor listCallCursor = aListCall.getCursor();
                String functionName = IDataUtil.getString(listCallCursor, "FunctionName");
                String[] listValues = IDataUtil.getStringArray(listCallCursor, "listValues");
                List<String> params = new ArrayList<String>();
                if (listValues != null) {
                    Collections.addAll(params, listValues);
                    listCallCursor.destroy();
                    ListCall lc = new ListCall(functionName, params);
                    calls.add(lc);
                }
            }
        }
        String throwException = IDataUtil.getString(pipelineCursor, "throwException");
        boolean thE = "TRUE".equalsIgnoreCase(throwException);
        String aliasName = IDataUtil.getString(pipelineCursor, "aliasName");
        String[] defaultResultValue = IDataUtil.getStringArray(pipelineCursor, "defaultResultValue");
        pipelineCursor.destroy();

        List<String> defaultValues = new ArrayList<String>();
        if (defaultResultValue != null) {
            Collections.addAll(defaultValues, defaultResultValue);
        }
        WMInput input = new WMInput(calls, defaultValues, aliasName, thE);
        return input;
    }

    /**
     * Allow to wrap output return value from the API toward webMethods IData format
     *
     * @param lResponses output return value
     * @return IData structure
     */
    static IData wrapOutputs(List<ListResponse> lResponses, IData pipeline) {
        //IData pipeline = IDataFactory.create();
        // pipeline
        IDataCursor pipelineCursor_1 = pipeline.getCursor();

        // listResponse
        IData[] listResponse = new IData[lResponses.size()];
        int i = 0;
        for (ListResponse lr : lResponses) {
            listResponse[i] = IDataFactory.create();
            IDataCursor listResponseCursor = listResponse[i].getCursor();
            IDataUtil.put(listResponseCursor, "FunctionName", lr.getFunctionName());
            String[] valuesArray = new String[lr.getValues().size()];
            int j = 0;
            List<String> values = lr.getValues();
            for (int i1 = 0, valuesSize = values.size(); i1 < valuesSize; i1++) {
                String value = values.get(i1);
                valuesArray[j++] = value;
            }
            IDataUtil.put(listResponseCursor, "listValues", valuesArray);
            listResponseCursor.destroy();
        }
        IDataUtil.put(pipelineCursor_1, "listResponse", listResponse);
        pipelineCursor_1.destroy();
        return pipeline;
    }

    public static void callTransco(IData pipeline) throws ServiceException {
        List<ListResponse> lResponses = null;
        WMInput input = wrapInputs(pipeline);
        try {
            lResponses = TranscoClient.callTransco(input.getDatastoreAlias(), input.isThrowException(), input.getCalls(), input.getDefaultValues());
        } catch (NoResultException nre) {
            if (input.isThrowException()) {
                throw new ServiceException(nre.getMessage());
            }
        }

        pipeline = wrapOutputs(lResponses, pipeline);


    }

    public static boolean cleanCache() {
        return TranscoClient.cleanCache();
    }

    public static void callTranscoWithCache(IData pipeline) throws ServiceException {

        List<ListResponse> lResponses = null;
        WMInput input = wrapInputs(pipeline);
        try {
            lResponses = TranscoClient.callTranscoWithCache(input.getDatastoreAlias(), input.isThrowException(), input.getCalls(), input.getDefaultValues());
        } catch (NoResultException nre) {
            if (input.isThrowException()) {
                throw new ServiceException(nre.getMessage());
            }
        }
        pipeline = wrapOutputs(lResponses, pipeline);

    }
}
