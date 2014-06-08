package cl.blackbird.paper.server.control;

import cl.blackbird.paper.client.ClientSocket;
import cl.blackbird.paper.server.Server;

import javax.swing.*;
import java.io.IOException;

/**
 * Le permite al servidor funcionar en el hilo de fondo de la interfaz, de esta manera no interrumpe la captura de
 * evento de los controles y el servidor puede ser lanzado y detenido desde la GUI.
 */
public class ServerWorker extends SwingWorker<Void, Boolean> {
    private final String host;
    private final int hostPort;
    private Server server;

    public ServerWorker(int port, String host, int hostPort){
        this.host = host;
        this.hostPort = hostPort;
        this.server = new Server(port);
    }

    @Override
    protected Void doInBackground() throws Exception {
        ClientSocket.init(this.host, this.hostPort);
        this.server.start();
        return null;
    }

    public void stop(){
        this.server.stop();
        try {
            ClientSocket.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
