package controllers;

public class ControllerMediator implements IMediateControllers {

    private StatusController statusController;
    private CaptureController captureController;
    private SettingsController settingsController;
    private BarController barController;

    @Override
    public void registerStatusController(StatusController statusController) {
        this.statusController = statusController;
    }

    @Override
    public void registerCaptureController(CaptureController captureController) {
        this.captureController = captureController;
    }

    @Override
    public void registerSettingsController(SettingsController settingsController) {
        this.settingsController = settingsController;
    }

    @Override
    public void registerBarController(BarController barController) {
        this.barController = barController;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Override
    public void setGUIConnect() {
        barController.setGreenLamp();
        barController.setTextConInfoLbl("Подключено");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    @Override
    public void setGUIDisconnect() {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Отключено");
        statusController.setTextConnectButton("Подключить");
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }

    @Override
    public void setGUIWaiting() {
        barController.setYellowLamp();
        barController.setTextConInfoLbl("Соединение");
        barController.setTextInfoLbl("");
        statusController.setTextConnectButton("Отключить");
        statusController.setIPFieldDisable(true);
    }

    @Override
    public void setGUIError(String message) {
        barController.setRedLamp();
        barController.setTextConInfoLbl("Ошибка");
        statusController.setTextConnectButton("Подключить");
        barController.setTextInfoLbl(message);
        statusController.setIPFieldDisable(false);
        statusController.setGUIDefaultState();
    }

    //-----------------------------------------------------------------------------------------------------------------

    public StatusController getStatusController() {
        return statusController;
    }

    public CaptureController getCaptureController() {
        return captureController;
    }

    public SettingsController getSettingsController() {
        return settingsController;
    }

    public BarController getBarController() {
        return barController;
    }

    //-----------------------------------------------------------------------------------------------------------------



    //----------------------------------------------------Singleton----------------------------------------------------

    private ControllerMediator() {}

    public static ControllerMediator getInstance() {
        return ControllerMediatorHolder.INSTANCE;
    }

    private static class ControllerMediatorHolder {
        private static final ControllerMediator INSTANCE = new ControllerMediator();
    }
}
