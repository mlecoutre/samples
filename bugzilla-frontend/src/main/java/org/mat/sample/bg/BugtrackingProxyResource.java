package org.mat.sample.bg;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import java.io.File;
import java.net.URI;
import java.util.List;
import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class BugtrackingProxyResource {

	@GET
	public Response index() {
		return Response.temporaryRedirect(URI.create("index.html")).build();
	}

	@Inject
	private TasksService tasksService;

	@GET
	@Path("tasks")
	@Produces("application/json;charset=UTF-8")
	public List<Task> tasks() throws Exception {
		// return transform(TasksService.list(), TO_COMMIT);
		return tasksService.list();
		// return
		// "[{\"taskId\":\"1\",\"summary\":\"summary1\"},{\"taskId\":\"2\",\"summary\":\"summary2\"}]";
	}

	@GET
	@Path("tasksjs")
	@Produces("text/csv;charset=UTF-8")
	public String tasksjs() throws Exception {
		// return transform(TasksService.list(), TO_COMMIT);
		return tasksService.listjs();
		// return
		// "[{\"taskId\":\"1\",\"summary\":\"summary1\"},{\"taskId\":\"2\",\"summary\":\"summary2\"}]";
	}

	@GET
	@Path("{path : .*}")
	public Response staticResource(@PathParam("path") String path) {
		File file = new File("web", path);
		if (!file.exists()) {
			throw new NotFoundException();
		}
		String mimeType = new MimetypesFileTypeMap().getContentType(file);

		return Response.ok(file, mimeType).build();
	}

}
