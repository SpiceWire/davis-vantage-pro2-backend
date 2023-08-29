package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.Model.SerialSettingsDTO;
import spicewire.davisinterface.Services.SerialSettingsService;

@RestController
@RequestMapping("/vp2")
public class DavisVP2Controller  {

    private ConsoleController consoleController;
    private SerialSettingsDTO serialSettingsDTO;
    private SerialSettingsService serialSettingsService;

    @GetMapping(path = "/settings")
    public SerialSettingsDTO getComPortSettings(){

        return serialSettingsService.getCurrentSettings();
    }

    @PostMapping(path= "/settings")
    public SerialSettingsDTO setSettings(@RequestBody SerialSettingsDTO newSettings){

    }




    }
    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
