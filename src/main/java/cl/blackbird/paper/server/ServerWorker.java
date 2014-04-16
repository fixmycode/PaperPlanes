package cl.blackbird.paper.server;

import cl.blackbird.paper.api.APIConfiguration;

import javax.swing.*;

/**
 * ServerWorker allows the PaperServer to be run in the background of the GUI
 */
public class ServerWorker extends SwingWorker<Void, Boolean> {
    private Server server;

    public ServerWorker(int port){
        Server.setConfiguration(new APIConfiguration());
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
