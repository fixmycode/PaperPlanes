package cl.blackbird.paper;

import cl.blackbird.paper.api.APIConfiguration;
import cl.blackbird.paper.gui.ServerControl;
import cl.blackbird.paper.server.Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    private static Logger LOG = Logger.getLogger("cl.blackbird.paper");
    private static ServerControl serverControl;
    private static JFrame mainFrame;

    public static void main(String[] args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                showGUI();
            }
        });
    }

    private static void showGUI() {
        mainFrame = new JFrame();

        /* Server Setup */
        Server.setConfiguration(new APIConfiguration());
        Server s = new Server();
        s.setPort(7070);
        Thread server = new Thread(s, "server");
        serverControl = new ServerControl();

        /* GUI Setup */
        mainFrame.setSize(200, 140);
        mainFrame.getContentPane().add(serverControl.getPanel());

        mainFrame.setVisible(true);
        server.run();
//        mainFrame.addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent windowEvent) {
//                System.exit(0);
//            }
//        });
    }
}
