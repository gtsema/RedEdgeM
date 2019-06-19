package dbService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dbService.entity.Config;
import dbService.entity.Exposure;
import dbService.entity.NetworkStatus;
import dbService.entity.Status;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

public class DBService {

    private static Logger log = Logger.getLogger(DBService.class.getName());

    private String IP;
    private Gson gson;

    public DBService(String IP) {
        this.IP = IP;
        gson = new Gson();
    }

    private synchronized JsonObject sendGET(String urlParameters) throws DBException {
        int responseCode = 418;

        try {
            URL obj = new URL(urlParameters);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            responseCode = con.getResponseCode();
            if(responseCode != 200) throw new IOException();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return new JsonParser().parse(in.readLine()).getAsJsonObject();

        } catch (IOException e) {
            throw new DBException("Ошибка соединения. Response code: " + responseCode, e);
        }
    }

    private synchronized JsonObject sendPOST(String url, String parameters) throws DBException {
        int responseCode = 418;

        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(parameters);
            wr.flush();
            wr.close();

            responseCode = con.getResponseCode();
            if(responseCode != 200) throw new IOException();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            JsonObject response = new JsonParser().parse(in.readLine()).getAsJsonObject();
            in.close();

            return response;

        } catch (IOException e) {
            throw new DBException("Ошибка соединения. Response code: " + responseCode, e);
        }
    }

    public Status getStatus() throws DBException {
        return gson.fromJson(sendGET("http://" + IP + "/status"), Status.class);
    }

    public NetworkStatus getNetworkStatus() throws DBException {
        return new NetworkStatus(sendGET("http://" + IP + "/networkstatus"));
    }

    public Exposure getExposure() throws DBException {
        return gson.fromJson(sendGET("http://" + IP + "/exposure"), Exposure.class);
    }

    public Config getConfig() throws DBException {
        return gson.fromJson(sendGET("http://" + IP + "/config"), Config.class);
    }

    public void formatSDCard() throws DBException {
        String request = "{\"erase_all_data\":true}";
        String url = "http://" + IP + "/reformatsdcard";
        JsonObject responce = sendPOST(url, request);

        if(!responce.get("reformat_status").getAsString().equals("success")) {
            log.warning("Ошибка форматирования SD-карты");
            throw new DBException("Ошибка форматирования SD-карты");
        }
    }
}
