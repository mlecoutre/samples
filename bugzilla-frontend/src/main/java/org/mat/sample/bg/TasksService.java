package org.mat.sample.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class TasksService {

	String request = "http://bugtracking.arcelor.net/buglist.cgi?classification=ASDK&query_format=advanced&bug_status=UNCONFIRMED&bug_status=NEW&bug_status=ASSIGNED&bug_status=REOPENED&bug_status=RESOLVED&bug_status=VERIFIED&bug_status=CLOSED&product=P-2006-8821%20TEC%20DK%20AJF&target_milestone=2.2.0&ctype=csv&Bugzilla_login=is.ajf%40arcelor.com&Bugzilla_password=is.ajf";

	public List<Task> list() {
		List<Task> tasks = new ArrayList<Task>();
		Task task = new Task("1", "summary1");
		tasks.add(task);
		task = new Task("2", "summary2");
		tasks.add(task);
		// Create the client resource
		return tasks;
	}

	public String listjs() throws Exception {
		StringBuffer result = new StringBuffer();
		try {

			// Send data
			URL url = new URL(request);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write("");
			wr.flush();
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String line;

			while ((line = rd.readLine()) != null) {
				// Process line...
				result.append(line + "\n");
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			// TODO improve javascript content to display the error
			result.append(e.getMessage());
		}
		return result.toString();
	}

	// // REST API DISABLED on bugtracking
	// ClientResource resource = new ClientResource(
	// "http://bugtracking.arcelor.net/jsonrpc.cgi?method=Bugzilla.time");
	//
	// // Customize the referrer property
	// // resource.setReferrerRef("http://www.mysite.org");
	//
	// // Write the response entity on the console
	// try {
	// resource.post(null).write(System.out);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
}
