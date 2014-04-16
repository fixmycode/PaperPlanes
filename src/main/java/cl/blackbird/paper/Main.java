package cl.blackbird.paper;

import cl.blackbird.paper.server.control.ControlFrame;

public class Main {
    public static void main(String[] args){
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ControlFrame().setVisible(true);
            }
        });
    }
}
