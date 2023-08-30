package spicewire.davisinterface.Services;

import spicewire.davisinterface.Controller.ConsoleController;
import spicewire.davisinterface.Model.CommPortModel;
import spicewire.davisinterface.Model.SerialSettingsDTO;

import java.io.Console;

public class SerialSettingsService {
    private SerialSettingsDTO serialSettingsDTO;
    private ConsoleController consoleController;
    public SerialSettingsDTO getCurrentSettings() {
        Integer baud = CommPortModel.getBaudRate();
        Integer dataBits = CommPortModel.getDataBits();
        Integer stopBits = CommPortModel.getStopBits();
        Integer parity = CommPortModel.getParity();
        Integer comPortIndex = CommPortModel.getComPortIndex();
        Integer timeoutMode = CommPortModel.getTimeoutMode();
        Integer readTimeout = CommPortModel.getReadTimeout();
        Integer writeTimeout = CommPortModel.getWriteTimeout();
        String[] comPortList = CommPortModel.getCommPortList();
        return new SerialSettingsDTO(baud, dataBits, stopBits, parity, comPortIndex,
                timeoutMode, readTimeout, writeTimeout, comPortList);
    }

    /**
     * Applies settings through the consoleController.
     * @param settingsDTO
     * @return boolean true if settings are successfully set.
     */
    public boolean applySettings(SerialSettingsDTO settingsDTO){
        CommPortModel.setBaudRate(settingsDTO.getBaud());
        CommPortModel.setDataBits(settingsDTO.getDataBits());
        CommPortModel.setStopBits(settingsDTO.getStopBits());
        CommPortModel.setParity(settingsDTO.getStopBits());
        CommPortModel.setComPortIndex(settingsDTO.getComPortIndex());
        CommPortModel.setCommPortList(settingsDTO.getComPortList());
        CommPortModel.setTimeoutMode(settingsDTO.getTimeoutMode());
        CommPortModel.setReadTimeout(settingsDTO.getReadTimeout());
        CommPortModel.setWriteTimeout(settingsDTO.getWriteTimeout());
        return consoleController.setPortParams(settingsDTO);
    }
}
