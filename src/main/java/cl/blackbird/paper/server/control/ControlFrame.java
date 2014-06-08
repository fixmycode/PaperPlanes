package cl.blackbird.paper.server.control;

import cl.blackbird.paper.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * La clase que controla la interfaz gráfica del servidor.
 * Posee métodos para iniciar e interactuar con los controles.
 */
public class ControlFrame extends JFrame {
    private JSpinner portSpinner;
    private JLabel portLabel;
    private JToggleButton controlButton;
    private ServerWorker serverWorker;
    private boolean serverListening = false;
    private JButton configButton;
    private JTextField pathField;
    private JButton saveButton;
    private JLabel pathLabel;
    private JLabel hostLabel;
    private JTextField hostField;
    private JLabel hostPortLabel;
    private JSpinner hostSpinner;

    public ControlFrame(){
        initComponents();
    }

    private void initComponents() {
        portSpinner = new JSpinner();
        portLabel = new JLabel("Puerto");
        controlButton = new JToggleButton("Iniciar", serverListening);
        configButton = new JButton("Configurar");
        pathLabel = new JLabel("Home");
        pathField = new JTextField(Server.getConfiguration().getHomeDir());
        hostLabel = new JLabel("Servidor");
        hostField = new JTextField("127.0.0.1");
        hostPortLabel = new JLabel("Puerto");
        hostSpinner = new JSpinner();
        saveButton = new JButton("Guardar");

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("PaperServer");

        portSpinner.setModel(new SpinnerNumberModel(7070, 7000, 9000, 1));
        portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner, "0000"));

        hostSpinner.setModel(new SpinnerNumberModel(7777, 7000, 9000, 1));
        hostSpinner.setEditor(new JSpinner.NumberEditor(hostSpinner, "0000"));

        controlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeServerStatus(e);
            }
        });
        configButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConfiguration(e);
            }
        });

        FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
        getContentPane().setLayout(layout);
        getContentPane().add(controlButton);
        getContentPane().add(configButton);

        setResizable(false);
        pack();
    }

    private void showConfiguration(ActionEvent e) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveConfiguration(e);
            }
        });
        getContentPane().add(pathLabel);
        getContentPane().add(pathField);
        getContentPane().add(portLabel);
        getContentPane().add(portSpinner);
        getContentPane().add(hostLabel);
        getContentPane().add(hostField);
        getContentPane().add(hostPortLabel);
        getContentPane().add(hostSpinner);
        getContentPane().add(saveButton);
        configButton.setEnabled(false);
        controlButton.setEnabled(false);
        pack();
    }

    private void saveConfiguration(ActionEvent e) {
        Server.getConfiguration().setHomeDir(pathField.getText());
        getContentPane().remove(pathField);
        getContentPane().remove(pathLabel);
        getContentPane().remove(portLabel);
        getContentPane().remove(portSpinner);
        getContentPane().remove(hostLabel);
        getContentPane().remove(hostField);
        getContentPane().remove(hostPortLabel);
        getContentPane().remove(hostSpinner);
        getContentPane().remove(saveButton);
        configButton.setEnabled(true);
        controlButton.setEnabled(true);
        pack();
    }

    private void changeServerStatus(ActionEvent e) {
        if(!this.serverListening && serverWorker == null){
            serverWorker = new ServerWorker(
                    (Integer) this.portSpinner.getValue(),
                    this.hostField.getText(),
                    (Integer) this.hostSpinner.getValue());
            serverWorker.execute();
            this.controlButton.setText("Detener");
            if(Desktop.isDesktopSupported()){
                try {
                    Desktop.getDesktop().browse(
                            new URI("http://localhost:"+String.valueOf(this.portSpinner.getValue())+"/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
            pack();
        } else if(serverWorker != null){
            serverWorker.cancel(true);
            serverWorker.stop();
            serverWorker = null;
            this.controlButton.setText("Iniciar");
        }
        this.serverListening = !this.serverListening;
        this.configButton.setEnabled(!this.serverListening);
        this.controlButton.setSelected(this.serverListening);


    }
}
