package spicewire.davisinterface.Controller;

import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.View.ViewDTO;

/**
 * This class maps a request for current data (e.g. "LOOP" or "LPS"). It returns a
 * Data Transfer Object for the View's text fields.
 */
@CrossOrigin
@RestController
@RequestMapping("/vp2/currentData")
public class ConsoleDataController {

    private ConsoleController consoleController;

    public ConsoleDataController(ConsoleController consoleController) {
        this.consoleController = consoleController;
    }

    @GetMapping(value="/{cmdWord}/{nonce}")
    public ViewDTO getUnique(@PathVariable String cmdWord, Integer nonce) {
        System.out.println("\nController received unique request for data with id= \n" + nonce);
        System.out.println("\nController received unique request for data with word= \n" + cmdWord);
        return consoleController.getCurrentData(cmdWord);
    }
}
