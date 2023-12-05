package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.View.ViewDTO;

@CrossOrigin
@RestController
@RequestMapping("/vp2/testing")
public class ConsoleTestingController {

    private ConsoleController consoleController;

    public ConsoleTestingController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @GetMapping(value="/{cmdWord}/{nonce}")
    public ViewDTO getUnique(@PathVariable String cmdWord, String nonce) {
        System.out.println("\nController received unique request for testing with id= \n" + nonce);
        return consoleController.getConsoleTest(cmdWord);
    }


}
