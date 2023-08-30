package spicewire.davisinterface.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spicewire.davisinterface.Model.SerialSettingsDTO;
import spicewire.davisinterface.Model.SerialStatus;
import spicewire.davisinterface.Services.SerialSettingsService;


@RestController
@RequestMapping("/vp2")
public class DavisVP2Controller  {

    private ConsoleController consoleController;
    private SerialSettingsDTO serialSettingsDTO;
    private SerialSettingsService serialSettingsService;

    @GetMapping(path = "/settings")
    public SerialStatus getComPortSettings(){
        return serialSettingsService.getCurrentSettings();
    }

    @PostMapping(path= "/settings")
    public SerialStatus setSettings(@RequestBody SerialSettingsDTO newSettings)  {
        try {
            boolean consoleParamsSet = serialSettingsService.applySettings(newSettings);
            if (!consoleParamsSet) {
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Settings were not applied, " +
                        "console might have been sleeping. Try again. ");
            }
            return serialSettingsService.getCurrentSettings();
        } catch (HttpMessageNotReadableException e) {
            System.out.println("New settings are blank.");
            newSettings = new SerialSettingsDTO(19200, 8, 1, 0,
                    0, 0, 0, 0,
                    new String[]{"COM4"});
            boolean consoleParamsSet = serialSettingsService.applySettings(newSettings);
            if (!consoleParamsSet) {
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Settings were not applied, " +
                        "console might have been sleeping. Try again. ");
            }
            return serialSettingsService.getCurrentSettings();
        }


    }

}
