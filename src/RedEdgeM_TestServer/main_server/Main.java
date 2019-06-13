import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.NetworkstatusServlet;
import servlets.StatusServlet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new StatusServlet()), "/status");
        context.addServlet(new ServletHolder(new NetworkstatusServlet()), "/networkstatus");

        server.setHandler(context);
        server.start();
        server.join();
    }
}
