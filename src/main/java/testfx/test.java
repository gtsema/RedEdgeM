package testfx;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class test {
    public static void main(String[] args) {

        try {
            URL obj = new URL("http://" + "10.10.1.103" + "/status");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            System.out.println(con.getResponseCode());
        } catch (IOException e) {
            System.out.println("exception");
        }

    }
}
