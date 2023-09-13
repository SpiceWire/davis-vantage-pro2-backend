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
public class SerialSettingsService {
    private StringBuilder messageToViewFromSerial = new StringBuilder();
    private SerialSettingsDTO serialSettingsDTO;
    @Autowired
    private ConsoleController consoleController;
    public SerialStatus getCurrentSettings() {
        return consoleController.getSerialPortSettings();
    }

    /**
     * Applies settings through the consoleController.
     * @param settingsDTO
     * @return boolean true if settings are successfully set.
     */
    public boolean applySettings(SerialSettingsDTO settingsDTO){
        messageToViewFromSerial.setLength(0); //erases StringBuilder
        return (
                validateBaud(settingsDTO.getBaud()) &&
                validateDataBits(settingsDTO.getDataBits()) &&
                validateParity(settingsDTO.getParity()) &&
                validateStopBits(settingsDTO.getStopBits()) &&
                validateTimeoutMode(settingsDTO.getTimeoutMode()) &&
                validateReadWriteTimeout(settingsDTO.getReadTimeout()) &&
                validateReadWriteTimeout(settingsDTO.getWriteTimeout())&&
                        consoleController.setPortParams(settingsDTO)
                );

    }

    private boolean validateBaud(Integer baud){
        boolean baudValid = false;
        switch(baud) {
            case 1200: case 2400: case 4800: case 9600: case 14400: case 19200:
                baudValid = true;
                break;
            default: {
                baudValid = false;
                messageToViewFromSerial.append("Invalid baud value.\n");
            }
            break;
        }
        return baudValid;
    }
    private boolean validateDataBits(Integer data){
        if (data !=8 ){
            messageToViewFromSerial.append("Invalid data bits value. Value should be 8.\n");
        }
        return data == 8;
    }
    private boolean validateStopBits(Integer stop){
        if (stop != 1){
            messageToViewFromSerial.append("Invalid stop bits value. Value should be 1.\n");
        }
         return stop == 1;
    }
    private boolean validateParity(Integer parity){
        if (parity != 0){
            messageToViewFromSerial.append("Invalid parity value. Value should be 0, 1, 2, 3 or 4 " +
                    "(none, odd, even, mark, space)\n");
        }
        return parity == 0;
    }

    private boolean validateComPortIndex(Integer index){
        if (index == null){
            messageToViewFromSerial.append("Com port index must be an integer.");
        }
        return (index != null);
    }
    private boolean validateTimeoutMode(Integer mode){
        boolean timeoutModeValid = false;
        switch (mode){
            case 0: case 1: case 16: case 256: case 4096:
                timeoutModeValid = true;
                break;
            default: {
                messageToViewFromSerial.append("Timeout mode not valid. Valid values are 0, 1, 16, 256, 4096 " +
                        "(nonblocking, read semi-blocking, read blocking, write blocking, scanner");
                timeoutModeValid = false;
            }
            break;
        }
        return timeoutModeValid;
    }
    private boolean validateReadWriteTimeout(Integer time) {
        boolean timeValid = (time >= 0 && time < 2000);
        if (!timeValid){
            messageToViewFromSerial.append("Timeout must be between 0 and 2000 in milliseconds.");
        }
        return timeValid;
    }
}
