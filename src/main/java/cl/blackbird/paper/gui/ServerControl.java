package cl.blackbird.paper.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import cl.blackbird.paper.server.Server;
import com.jgoodies.forms.*;

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
        panel1.setBorder(new EmptyBorder(10, 10, 10, 10));
        SpinnerNumberModel model1 = new SpinnerNumberModel(80, 0, 65535, 1);
        puertoSpinner.setModel(model1);
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

    public JPanel getPanel(){
        return panel1;
    }

}
