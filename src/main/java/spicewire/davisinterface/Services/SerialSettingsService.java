package spicewire.davisinterface.Services;

import spicewire.davisinterface.Model.CommPortModel;
import spicewire.davisinterface.Model.SerialSettingsDTO;

public class SerialSettingsService {
    private SerialSettingsDTO serialSettingsDTO;

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

    public void
}
