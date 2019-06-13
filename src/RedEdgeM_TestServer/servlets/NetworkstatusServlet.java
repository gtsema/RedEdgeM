package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NetworkstatusServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String json = "{\"network_map\":[{\"bands\":[475,560,668,840,717],\"device_type\":\"camera\",\"gps_source\":\"\",\"mode\":\"master\",\"serial\":\"RM01-1831048-SC\",\"sw_version\":\"v3.4.3\"},{\"bands\":[475,560,668,840,717],\"device_type\":\"dls\",\"gps_source\":\"direct\",\"mode\":\"slave\",\"serial\":\"DL06-1829079-SC\",\"sw_version\":\"v1.0.1\"}]}";
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        pw.print(json);
    }

}
