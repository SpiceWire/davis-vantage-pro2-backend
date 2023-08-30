package spicewire.davisinterface.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spicewire.davisinterface.Controller.ConsoleController;
import spicewire.davisinterface.Model.CommPortModel;
import spicewire.davisinterface.Model.SerialSettingsDTO;
import spicewire.davisinterface.Model.SerialStatus;

import java.io.Console;
@Service
//@Component
//@Configuration
public class SerialSettingsService {
    private SerialSettingsDTO serialSettingsDTO;
    @Autowired
    private ConsoleController consoleController;
    public SerialStatus getCurrentSettings() {
//        Integer baud = CommPortModel.getBaudRate();
//        Integer dataBits = CommPortModel.getDataBits();
//        Integer stopBits = CommPortModel.getStopBits();
//        Integer parity = CommPortModel.getParity();
//        Integer comPortIndex = CommPortModel.getComPortIndex();
//        Integer timeoutMode = CommPortModel.getTimeoutMode();
//        Integer readTimeout = CommPortModel.getReadTimeout();
//        Integer writeTimeout = CommPortModel.getWriteTimeout();
//        String[] comPortList = CommPortModel.getCommPortList();
//        return new SerialSettingsDTO(baud, dataBits, stopBits, parity, comPortIndex,
//                timeoutMode, readTimeout, writeTimeout, comPortList);
        return consoleController.getSerialPortSettings();
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
