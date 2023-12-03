package spicewire.davisinterface.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spicewire.davisinterface.Model.SerialSettingsDTO;
import spicewire.davisinterface.Model.SerialStatus;
import spicewire.davisinterface.Services.SerialSettingsService;

@CrossOrigin
@RestController
@RequestMapping("/vp2/settings")
public class DavisVP2Controller  {
    @Autowired
    private ConsoleController consoleController;
    private SerialSettingsDTO serialSettingsDTO;
    @Autowired
    private SerialSettingsService serialSettingsService;

    @GetMapping(path = "/{val}")
    public SerialStatus getComPortSettings(){
        System.out.println("DavisVP2COntroller: getComPortSettings called from remote");
        return serialSettingsService.getCurrentSettings();
    }

    @PostMapping(path= "/{val}")
    public SerialStatus setSettings(@RequestBody SerialSettingsDTO newSettings)  {
        try {
            System.out.println("DavisVP2Controller: remote is trying to change settings with:");
            System.out.println(newSettings);
            boolean consoleParamsSet = serialSettingsService.applySettings(newSettings);
            if (!consoleParamsSet) {
                throw new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Settings were not applied, " +
                        "console might have been sleeping. Try again. ");
            }
            return serialSettingsService.getCurrentSettings();
        } catch (HttpMessageNotReadableException e) {
            System.out.println("New settings are blank.");
            newSettings = new SerialSettingsDTO(19200,
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
