package cl.blackbird.paper.server.control;

import cl.blackbird.paper.server.ServerWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlFrame extends JFrame {
    private JSpinner portSpinner;
    private JLabel portLabel;
    private JToggleButton controlButton;
    private ServerWorker serverWorker;
    private boolean serverListening = false;

    public ControlFrame(){
        initComponents();
    }

    private void initComponents() {
        portSpinner = new JSpinner();
        portLabel = new JLabel("Puerto:");
        controlButton = new JToggleButton("Iniciar", serverListening);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PaperServer");

        portSpinner.setModel(new SpinnerNumberModel(7070, 7000, 9000, 1));
        portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "0000"));

        controlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeServerStatus(e);
            }
        });

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        getContentPane().setLayout(layout);
        getContentPane().add(portLabel);
        getContentPane().add(portSpinner);
        getContentPane().add(controlButton);

        setResizable(false);
        pack();
    }

    private void changeServerStatus(ActionEvent e) {
        if(!this.serverListening && serverWorker == null){
            serverWorker = new ServerWorker((Integer) this.portSpinner.getValue());
            serverWorker.execute();
            this.controlButton.setText("Detener");
            pack();
        } else if(serverWorker != null){
            serverWorker.cancel(true);
            serverWorker.stop();
            serverWorker = null;
            this.controlButton.setText("Iniciar");
        }
        this.serverListening = !this.serverListening;
        this.portSpinner.setEnabled(!this.serverListening);
        this.controlButton.setSelected(this.serverListening);


    }
}
