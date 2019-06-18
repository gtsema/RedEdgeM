package dbService.entity;

public class Config extends Entity {

    private String streaming_enable;
    private String preview_band;
    private String Allowable;
    private String operating_alt;
    private String overlap_along_track;
    private String overlap_cross_track;
    private String auto_cap_mode;
    private String timer_period;
    private String ext_trigger_mode;
    private String pwm_trigger_threshold;
    private String enabled_bands_raw;
    private String enabled_bands_jpeg;
    private String enable_man_exposure;
    private String gain_exposure_crossover;
    private String ip_address;
    private String raw_format;
    private String network_mode;

    public String getStreaming_enable() {
        return streaming_enable;
    }

    public void setStreaming_enable(String streaming_enable) {
        this.streaming_enable = streaming_enable;
    }

    public String getPreview_band() {
        return preview_band;
    }

    public void setPreview_band(String preview_band) {
        this.preview_band = preview_band;
    }

    public String getAllowable() {
        return Allowable;
    }

    public void setAllowable(String allowable) {
        Allowable = allowable;
    }

    public String getOperating_alt() {
        return operating_alt;
    }

    public void setOperating_alt(String operating_alt) {
        this.operating_alt = operating_alt;
    }

    public String getOverlap_along_track() {
        return overlap_along_track;
    }

    public void setOverlap_along_track(String overlap_along_track) {
        this.overlap_along_track = overlap_along_track;
    }

    public String getOverlap_cross_track() {
        return overlap_cross_track;
    }

    public void setOverlap_cross_track(String overlap_cross_track) {
        this.overlap_cross_track = overlap_cross_track;
    }

    public String getAuto_cap_mode() {
        return auto_cap_mode;
    }

    public void setAuto_cap_mode(String auto_cap_mode) {
        this.auto_cap_mode = auto_cap_mode;
    }

    public String getTimer_period() {
        return timer_period;
    }

    public void setTimer_period(String timer_period) {
        this.timer_period = timer_period;
    }

    public String getExt_trigger_mode() {
        return ext_trigger_mode;
    }

    public void setExt_trigger_mode(String ext_trigger_mode) {
        this.ext_trigger_mode = ext_trigger_mode;
    }

    public String getPwm_trigger_threshold() {
        return pwm_trigger_threshold;
    }

    public void setPwm_trigger_threshold(String pwm_trigger_threshold) {
        this.pwm_trigger_threshold = pwm_trigger_threshold;
    }

    public String getEnabled_bands_raw() {
        return enabled_bands_raw;
    }

    public void setEnabled_bands_raw(String enabled_bands_raw) {
        this.enabled_bands_raw = enabled_bands_raw;
    }

    public String getEnabled_bands_jpeg() {
        return enabled_bands_jpeg;
    }

    public void setEnabled_bands_jpeg(String enabled_bands_jpeg) {
        this.enabled_bands_jpeg = enabled_bands_jpeg;
    }

    public String getEnable_man_exposure() {
        return enable_man_exposure;
    }

    public void setEnable_man_exposure(String enable_man_exposure) {
        this.enable_man_exposure = enable_man_exposure;
    }

    public String getGain_exposure_crossover() {
        return gain_exposure_crossover;
    }

    public void setGain_exposure_crossover(String gain_exposure_crossover) {
        this.gain_exposure_crossover = gain_exposure_crossover;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getRaw_format() {
        return raw_format;
    }

    public void setRaw_format(String raw_format) {
        this.raw_format = raw_format;
    }

    public String getNetwork_mode() {
        return network_mode;
    }

    public void setNetwork_mode(String network_mode) {
        this.network_mode = network_mode;
    }
}
