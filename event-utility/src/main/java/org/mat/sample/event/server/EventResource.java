package org.mat.sample.event.server;

import com.google.inject.Inject;
import com.sun.jersey.api.NotFoundException;
import org.mat.sample.event.model.Event;
import org.mat.sample.event.services.EventService;

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
public class EventResource {

	@GET
	public Response index() {
		return Response.temporaryRedirect(URI.create("index.html")).build();
	}

	@Inject
	private EventService eventService;

	@GET
	@Path("events")
	@Produces("application/json;charset=UTF-8")
	public List<Event> tasks() throws Exception {
		// return transform(TasksService.list(), TO_COMMIT);
		return eventService.list();
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
