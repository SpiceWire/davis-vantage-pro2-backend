package spicewire.davisinterface.View;

import spicewire.davisinterface.Model.Command;
import spicewire.davisinterface.Services.DataProcessor;

import static spicewire.davisinterface.Services.DataProcessor.getSerialData;

/**
 * This class generates text messages to send to the View.
 */
public class TextAreas {
    //Console OK message is usually <Linefeed><Carriage Return>OK<Linefeed><Carriage Return>
    private static final String CONSOLE_OK_MESSAGE = "\n\rOK\n\r";
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String consoleFriendlyText(Command command) {
        StringBuilder friendlyText = new StringBuilder();
        if (getSerialData().length() < CONSOLE_OK_MESSAGE.length()) {
            friendlyText.append("No valid data was returned. Something went wrong. Msg length was:").append(getSerialData().length());
            return friendlyText.toString();
        }
        switch (command.getWord()) {
            case "TEST":
                friendlyText.append("Successful if TEST is returned.\n");
                if (getSerialData().trim().equalsIgnoreCase("TEST")) {
                    friendlyText.append("It appears the test was successful.");
                }
                break;
            case "NVER":
                friendlyText.append("Successful if a number is returned.\n");
                friendlyText.append("Firmware number installed is ");
                friendlyText.append(getSerialData().substring(CONSOLE_OK_MESSAGE.length() - 1));
                break;
            case "RECEIVERS":
                friendlyText.append("You are listening to station number: ");
                String msg = getSerialData().substring(CONSOLE_OK_MESSAGE.length());
                for (char c : msg.toCharArray()) {
                    friendlyText.append(Integer.toBinaryString(c));
                }
                break;  //todo capture binary info and extract assigned receivers
            case "VER":
                friendlyText.append("Successful if a date is returned.\n");
                friendlyText.append("Firmware date code is: ");
                friendlyText.append(getSerialData().substring(CONSOLE_OK_MESSAGE.length() - 1));
                break;
            case "RXTEST":
                friendlyText.append("Successful if OK is returned.\n");
                if (getSerialData().equalsIgnoreCase(CONSOLE_OK_MESSAGE)) {
                    friendlyText.append("Test appears successful.");
                }
                break;
            case "RXCHECK":
                friendlyText.append("Successful if several numbers were returned.\n");
                String serialString = getSerialData();
                String diagnosticData = serialString.substring(serialString.indexOf("K")+1).trim();
                String[] drAry = diagnosticData.split(" ");
                friendlyText.append("Total packets received: ").append(drAry[0]).append(LINE_SEPARATOR);
                friendlyText.append("Total packets missed: ").append(drAry[1]).append(LINE_SEPARATOR);
                friendlyText.append("Number of resynchronizations: ").append(drAry[2]).append(LINE_SEPARATOR);
                friendlyText.append("Largest number of packets received in a row: ").append(drAry[3]).append(LINE_SEPARATOR);
                friendlyText.append("Number of CRC errors detected: ").append(drAry[4]);
                break;
            default:
                friendlyText.append("Something went wrong.");
        }
        return friendlyText.toString();
    }
}
