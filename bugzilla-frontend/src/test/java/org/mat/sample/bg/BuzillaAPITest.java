package org.mat.sample.bg;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.junit.Test;

public class BuzillaAPITest {
	private static final String serverURL = "http://bugtracking.arcelor.net/";

	private static final String BUG_ID = "19288";

	@Test
	public void testBug() {
		HashMap result = login(serverURL,
				"matthieu.lecoutre@arcelormittal.com", "IsanIzard27!");
	}

	private XmlRpcClient getClient(String serverURL) {
		try {
			String apiURL = serverURL + "xmlrpc.cgi";
			XmlRpcClient rpcClient;
			XmlRpcClientConfigImpl config;

			config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL(apiURL));
			rpcClient = new XmlRpcClient();
			rpcClient.setConfig(config);

			return rpcClient;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap login(String serverURL, String login, String password) {
		try {
			XmlRpcClient rpcClient = getClient(serverURL);
			ArrayList<Object> params = new ArrayList<Object>();
			Hashtable<String, Object> executionData = new Hashtable<String, Object>();
			executionData.put("login", login);
			executionData.put("password", password);
			executionData.put("remember", true);
			params.add(executionData);

			HashMap result = (HashMap) rpcClient.execute("User.login", params);
			System.out.println(result);
			return result;
		} catch (XmlRpcException e) {
			e.printStackTrace();
		}
		return null;
	}

}
