package dbService.entity;

import com.google.gson.JsonObject;

public class NetworkStatus extends Entity{

    JsonObject data;

    public NetworkStatus(JsonObject data) {
        this.data = data;;
    }

    public boolean getCameraStatus() {
        try {
            if(data.get("network_map").getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("device_type")
                    .toString()
                    .equals("\"camera\"")) return true;
            else return false;
        } catch (Exception e) { return false; }
    }

    public boolean getDLSStatus() {
        try {
            if(data.get("network_map").getAsJsonArray()
                    .get(1)
                    .getAsJsonObject()
                    .get("device_type")
                    .toString()
                    .equals("\"dls\"")) return true;
            else return false;
        } catch (Exception e) { return false; }
    }
}
