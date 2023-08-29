package spicewire.davisinterface.View;

import javax.swing.*;
import java.awt.*;

public class SwingFrame extends JFrame {


        //System.out.println("frame called on start");
        JFrame frame = new JFrame("Settings");

    public void run(String... arg0) throws Exception {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SwingFrame frame = new SwingFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
//        frame.setContentPane(view.getPanelMainJPanel());
//        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        frame.setSize(700, 600);
//        frame.setVisible(true);
}
