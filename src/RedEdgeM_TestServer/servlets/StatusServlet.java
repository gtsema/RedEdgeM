package servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entity.Status;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class StatusServlet extends HttpServlet {

    private Gson gson;
    Status status;

    public StatusServlet() {
        gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        status = new Status();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        status.generateData();
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(gson.toJson(status, Status.class).replaceAll("\n", ""));
    }
}
