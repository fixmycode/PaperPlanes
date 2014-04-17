package cl.blackbird.paper;

import cl.blackbird.paper.api.APIConfiguration;
import cl.blackbird.paper.server.Server;
import cl.blackbird.paper.server.control.ControlFrame;

public class Main {
    public static void main(String[] args){
        Server.setConfiguration(new APIConfiguration());
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ControlFrame().setVisible(true);
            }
        });
    }
}
