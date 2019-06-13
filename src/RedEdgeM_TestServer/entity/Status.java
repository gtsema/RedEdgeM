package entity;

import com.google.gson.annotations.Expose;

import java.util.Random;

public class Status {

    private Random rnd;

    @Expose
    private String bus_volts = "0.00";
    @Expose
    private String sd_gb_free = "0.00";
    @Expose
    private String sd_gb_total = "0.00";
    @Expose
    private String sd_status = "Error";
    @Expose
    private String sd_warn = "false";

    public Status() {
        rnd = new Random();
    }

    public void generateData() {

        double bus_volts_min = 3.0;
        double bus_volts_max = 3.8;
        bus_volts = String.valueOf(bus_volts_min + (bus_volts_max - bus_volts_min) * rnd.nextDouble());

        sd_gb_free = "10.23";
        sd_gb_total = "16.08";
        sd_status = "Ok";
        sd_warn = "false";
    }
}
