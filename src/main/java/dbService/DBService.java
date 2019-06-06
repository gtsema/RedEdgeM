package dbService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dbService.entity.NetworkStatus;
import dbService.entity.Status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DBService {

    private String IP;
    private Gson gson;

    public DBService(String IP) {
        this.IP = IP;
        gson = new Gson();
    }

    public ResponseObject<Status> getStatus() throws DBException {

        int responseCode = 418;

        try {

            URL obj = new URL("http://" + IP + "/status");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));


            return new ResponseObject<Status>(gson.fromJson(in.readLine(), Status.class), responseCode);

        } catch (IOException e) {
            throw new DBException("Ошибка соединения. Response code: " + responseCode, e);
        }
    }

    public ResponseObject<NetworkStatus> getNetworkStatus() throws DBException {

        int responseCode = 418;

        try {
            URL obj = new URL("http://" + IP + "/networkstatus");
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            responseCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonObject data = new JsonParser().parse(in.readLine()).getAsJsonObject();

            return new ResponseObject<NetworkStatus>(new NetworkStatus(data), responseCode);

        } catch (IOException e) {
            throw new DBException("Ошибка соединения. Response code: " + responseCode, e);
        }
    }
}
