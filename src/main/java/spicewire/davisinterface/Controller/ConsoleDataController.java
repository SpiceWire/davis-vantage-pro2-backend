package spicewire.davisinterface.Controller;

import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.View.ViewDTO;

@CrossOrigin
@RestController
@RequestMapping("/vp2/currentData")
public class ConsoleDataController {

    private ConsoleController consoleController;

    public ConsoleDataController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @GetMapping(value="/{cmdWord}/{nonce}")
    public ViewDTO getUnique(@PathVariable String cmdWord, int nonce) {
        System.out.println("\nController received unique request for testing with id= \n" + nonce);
        return consoleController.getCurrentData(cmdWord);
    }
}
