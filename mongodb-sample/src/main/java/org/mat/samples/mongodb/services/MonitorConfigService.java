package org.mat.samples.mongodb.services;

import org.mat.samples.mongodb.vo.HttpFile;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * User: E010925
 * Date: 28/11/12
 * Time: 11:21
 */
@Path("/MonitorConfig")
@Produces(MediaType.APPLICATION_JSON)
public class MonitorConfigService {

    @GET
    @Path("/{appliName}/{serverName}/{asName}")
    public List<String> listDataSources(@PathParam("appliName") String appliName, @PathParam("serverName") String serverName, @PathParam("asName") String asName) {
        return MonitorService.listDataSources(appliName, serverName, asName);
    }

    @POST
    @Path("/{applicationName}")
    public String loadData(List<HttpFile> files, @PathParam("applicationName") String applicationName) {
        long sum = 0;
        for (HttpFile f : files) {
            //extract asName and serverName from the fileName
            String[] arr = f.getFileName().split("/");
            String asName = arr[arr.length - 2];
            String serverName = arr[2];
            sum += MonitorService.batchInsert(f.getFileName(), applicationName, serverName, asName);
        }
        return sum + "Elements loaded";

    }
}
