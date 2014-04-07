package cl.blackbird.paper.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerControl {
    private JPanel panel1;
    private JSpinner puertoSpinner;
    private JButton iniciarButton;
    private JButton detenerButton;

    private void startStop(){
        puertoSpinner.setEnabled(!puertoSpinner.isEnabled());
        detenerButton.setEnabled(!detenerButton.isEnabled());
        iniciarButton.setEnabled(!iniciarButton.isEnabled());
    }

    public ServerControl() {
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startStop();
            }
        });
        detenerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startStop();
            }
        });

    }
}
