package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;

@RestController
public class DavisVP2Controller  {


    //private static DavisVP2 davisVP2;
    //private static DataSource datasource;
    private ConsoleController consoleController;



    public void get(){


        System.out.println("Weather record sent from server.");

    }


    @GetMapping(path = "/portSettings")
    public String getComPortSettings(){
        return ConsoleController.getComPortSettings();
    }

    /*
    @RequestMapping(path = "/davisvp2/comportsettings", method = RequestMethod.PUT)
    public void*/
    //how to make key-value pairs?

}
