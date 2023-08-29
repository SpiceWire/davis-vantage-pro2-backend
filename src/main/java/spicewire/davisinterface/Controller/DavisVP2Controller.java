package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class DavisVP2Controller  {

    private ConsoleController consoleController;
    public static String API_BASE_URL = " http://localhost:8080/console/";





    @GetMapping(path = "/portSettings")
    public String getComPortSettings(){
        return ConsoleController.getComPortSettings();
    }


    public void setSettings(){


        System.out.println("Weather record sent from server.");

    }
    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
