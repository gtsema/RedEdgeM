package controllers;

public interface IMediateControllers {
    void registerStatusController(StatusController statusController);
    void registerCaptureController(CaptureController captureController);
    void registerSettingsController(SettingsController settingsController);
    void registerBarController(BarController barController);

    void setGUIConnect();
    void setGUIDisconnect();
    void setGUIWaiting();
    void setGUIError(String message);
}
