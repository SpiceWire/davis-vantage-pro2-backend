package spicewire.davisinterface.View;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spicewire.davisinterface.Controller.ConsoleController;
import spicewire.davisinterface.Model.Command;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ComsPanes implements ViewDao {

    private JPanel mainJPanel;
    private JComboBox cmbComPort;
    private JComboBox cmbBaud;
    private JComboBox cmbStopBits;
    private JComboBox cmbReadTimeout;
    private JComboBox cmbWriteTimeout;
    private JRadioButton rdo7DataBits;
    private JRadioButton rdo8DataBits;
    private JButton applyButton;
    private JButton refreshButton;
    private JComboBox cmbParity;
    private JTextArea currentDataTextArea;
    private JButton loopButton;
    private JButton LPSButton;
    private JButton hiLowsButton;
    private JButton putRainButton;
    private JButton putETButton;
    private JTextField tfLoop;
    private JTextField tfLPS;
    private JTextField tfNoParametersNeededTextField;
    private JTextField tfPutRain;
    private JTextField tfPutET;
    private JButton haltDataWakeUpButton;
    private JTextField tfEval;
    private JComboBox cmbTimeoutMode;
    private JTextArea consoleRawTextArea;
    private JTextArea consoleFriendlyTextArea;
    private JButton testButton;
    private JButton rxCheckButton;
    private JPanel pnlTestTextPanel;
    private JPanel testButtonPanel;
    private JButton rxTestButton;
    private JButton versionButton;
    private JButton receiversButton;
    private JButton nverButton;
    private JTextArea testDescriptionTextArea;
    private JTabbedPane mainPane;
    private JButton wakeUpButton;
    private ConsoleController consoleController;
    public final String LINE_SEPARATOR = System.getProperty("line.separator");

    public ComsPanes() {
        createComPortList();
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("test");
            }
        });
        rxCheckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("rxcheck");
            }
        });
        rxTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("rxtest");
            }
        });
        versionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("ver");
            }
        });
        receiversButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("receivers");
            }
        });
        nverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("nver");
            }
        });
        loopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("loop");
            }
        });
        LPSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runCommand("lps");
            }
        });
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setComPortParameters();
            }
        });
    }

    public boolean comPortParametersAreSet(){
        return consoleController.isComPortParametersAreSet();
    };

    public void runCommand(String command){
        consoleController.runCommand(command);
    }
    public void setComPortParameters(){
        consoleController.setComPortParameters(getComPortIndex(), getBaudRate(), getDataBits(),
                getStopBits(), getParity(), getTimeoutMode(), getWriteTimeout(),
                getReadTimeout());
    }

    private void createComPortList(){
        populateComPortList(consoleController.getComPortList());
    }

    public void populateComPortList(String[] comPortList){
        for (String str: comPortList){
            cmbComPort.addItem(str);
        }
    }

    public JPanel getPanelMainJPanel() {
        return mainJPanel;
    }

    public int getComPortIndex() {
        return cmbComPort.getSelectedIndex();
    }
    public String getComPortSelection() {
        return cmbComPort.getSelectedItem().toString();
    }

    public void clearComPortList() {
        if(cmbComPort.getItemCount()>0) {
            cmbComPort.removeAllItems();
        }
    }

    public JTextField getTfLoop() {
        return tfLoop;
    }
    public JTextField getTfEval(){
        return tfEval;
    }
    public JTextField getTfLPS() {return tfLPS;}
    public JTextField getTfPutET() {return tfPutET;}
    public JTextField getTfPutRain() {return tfPutRain;}
    public JTextField getTfNoParametersNeededTextField() {return tfNoParametersNeededTextField;}

    public void setTfEval(String evalMessage){
        tfEval.setText(evalMessage);
    }

    public JButton getTestButton() {
        return testButton;
    }

    public int getBaudRate() {
        return Integer.parseInt((String) cmbBaud.getSelectedItem());
    }

    public int getParity() {
        return cmbParity.getSelectedIndex(); //index values correspond to int parameters in Fazecast SerialPort package
    }

    public int getStopBits() {
        return cmbStopBits.getSelectedIndex(); //index values correspond to Fazecast Serial parameters
    }

    public int getDataBits() {
        int dataBits;
        if (rdo7DataBits.isSelected()) {
            dataBits = 7;
        } else dataBits = 8;
        return dataBits;
    }

    public int getTimeoutMode(){
        String timeoutModeStr = cmbTimeoutMode.getSelectedItem().toString();
        int timeoutMode = 0;
        switch (timeoutModeStr){
            case "Non-blocking": timeoutMode = 0;  //these int values are defined in Fazecast SerialPort package
                break;
            case "Write Blocking": timeoutMode = 256;
                break;
            case "Read Semi-blocking": timeoutMode = 1;
                break;
            case "Read Full-blocking": timeoutMode = 16;
                break;
            case "Scanner": timeoutMode = 4096;
                break;
        }
        return timeoutMode;
    }

    public void setConsoleFriendlyTextArea(String friendlyText){
        consoleFriendlyTextArea.setText(friendlyText);
    }
    public void setCurrentDataTextArea(String currentData){currentDataTextArea.setText(currentData);}

    public int getWriteTimeout(){
        return Integer.parseInt((String) cmbWriteTimeout.getSelectedItem());
    }
    public int getReadTimeout(){
        return Integer.parseInt((String) cmbReadTimeout.getSelectedItem());
    }
    public JButton getRefreshButton() {
        return refreshButton;
    }
    public JButton getApplyButton(){
        return applyButton;
    }
    public JButton getLoopButton(){
        return loopButton;
    }
    public JButton getRxCheckButton() {
        return rxCheckButton;
    }
    public JButton getRxTestButton(){
        return rxTestButton;
    }
    public JButton getVersionButton(){
        return versionButton;
    }
    public JButton getNverButton() {return nverButton;}
    public JButton getReceiversButton() {return receiversButton;}
    public JButton getLPSButton(){
        return LPSButton;
    }
    public JButton getHiLowsButton(){
        return hiLowsButton;
    }
    public JButton getPutRainButton(){
        return putRainButton;
    }
    public JButton getPutETButton(){
        return putETButton;
    }
    public JButton getHaltDataWakeUpButton() {
        return haltDataWakeUpButton;
    }
    public JButton getWakeUpButton() {return wakeUpButton;}
    public JPanel getTestButtonPanel() {return testButtonPanel;}
    public void setTestDescriptionTextArea(String description){
        testDescriptionTextArea.setText(description);
    }
    public void setConsoleRawTextArea(String rawData){
        consoleRawTextArea.setText(rawData);
    }
    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
