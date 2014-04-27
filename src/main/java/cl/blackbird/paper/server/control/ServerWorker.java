package cl.blackbird.paper.server.control;

import cl.blackbird.paper.server.Server;

import javax.swing.*;

/**
 * Le permite al servidor funcionar en el hilo de fondo de la interfaz, de esta manera no interrumpe la captura de
 * evento de los controles y el servidor puede ser lanzado y detenido desde la GUI.
 */
public class ServerWorker extends SwingWorker<Void, Boolean> {
    private Server server;

    public ServerWorker(int port){
        this.server = new Server(port);
    }

    @Override
    protected Void doInBackground() throws Exception {
        this.server.start();
        return null;
    }

    public void stop(){
        this.server.stop();
    }
}
