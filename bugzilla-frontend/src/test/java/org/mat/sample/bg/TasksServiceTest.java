package org.mat.sample.bg;

import java.util.List;
import org.junit.Test;

public class TasksServiceTest {

	@Test
	public void testJSONBugtrackingTime() throws Exception {

		TasksService service = new TasksService();
		List<Task> tasks = service.list();
		System.out.println("tt " + tasks.size());
	}

	@Test
	public void testBugtrackingConnectionBugtrackingTime() throws Exception {

		TasksService service = new TasksService();

		String result = service.listjs();
		System.out.println("tt " + result);
	}
}
