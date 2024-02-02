package spicewire.davisinterface.Controller;


import org.springframework.web.bind.annotation.*;
import spicewire.davisinterface.View.ViewDTO;

/**
 * This class maps a request from frontend for Davis console tests. These
 * are Davis commands type 1 (TEST, RXTEST, RXCHECK, RECEIVERS,
 * VER, NVER). The class returns a Data Transfer Object for the View's text fields.
 */

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
        System.out.println("\nController received unique request for testing with word= \n" + cmdWord);
        System.out.println("\nController received unique request for testing with id= \n" + nonce);
        ViewDTO viewDTO = consoleController.getConsoleTest(cmdWord);
        System.out.println("CTC thinks viewDTO emsg is " + viewDTO.getErrorMessage());
        return viewDTO;
    }


}
