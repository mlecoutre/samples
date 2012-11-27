package org.mat.samples.mongodb.services;

import org.mat.samples.mongodb.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet(value = "/monitor", loadOnStartup = 1)
public class MonitorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            String action = request.getParameter("action");
            response.setContentType("application/json");
            if ("available-memory".equals(action)) {
                MonitorService.requestMemory(Constants.MEM_AVAILABLE, "appcfm51", response.getOutputStream());
            } else if ("total-memory".equals(action)) {
                MonitorService.requestMemory(Constants.MEM_TOTAL, "appcfm51", response.getOutputStream());
            } else if ("free-memory".equals(action)) {
                MonitorService.requestMemory(Constants.MEM_FREE, "appcfm51", response.getOutputStream());
            } else if ("max-memory".equals(action)) {
                MonitorService.requestMemory(Constants.MEM_MAX, "appcfm51", response.getOutputStream());
            }
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}