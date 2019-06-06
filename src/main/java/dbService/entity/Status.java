package dbService.entity;

public class Status extends Entity {

    private String alt_agl;
    private String alt_msl;
    private String auto_cap_active;
    private String bus_volts;
    private String course;
    private String gps_lat;
    private String gps_lon;
    private String gps_time;
    private String gps_used_sats;
    private String gps_vis_sats;
    private String gps_warn;
    private String p_acc;
    private String sd_gb_free;
    private String sd_gb_total;
    private String sd_status;
    private String sd_warn;
    private String vel_2d;

    public String getAlt_agl() {
        return alt_agl;
    }

    public String getAlt_msl() {
        return alt_msl;
    }

    public String getAuto_cap_active() {
        return auto_cap_active;
    }

    public String getBus_volts() {
        return bus_volts.substring(0, 4);
    }

    public String getCourse() {
        return course;
    }

    public String getGps_lat() {
        return gps_lat;
    }

    public String getGps_lon() {
        return gps_lon;
    }

    public String getGps_time() {
        return gps_time;
    }

    public String getGps_used_sats() {
        return gps_used_sats;
    }

    public String getGps_vis_sats() {
        return gps_vis_sats;
    }

    public String getGps_warn() {
        return gps_warn;
    }

    public String getP_acc() {
        return p_acc;
    }

    public String getSd_gb_free() {
        return sd_gb_free.substring(0, 4);
    }

    public String getSd_gb_total() {
        return sd_gb_total.substring(0, 4);
    }

    public String getSd_status() {
        return sd_status;
    }

    public String getSd_warn() {
        return sd_warn;
    }

    public String getVel_2d() {
        return vel_2d;
    }
}
