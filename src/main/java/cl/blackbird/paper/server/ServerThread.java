package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * Se encarga de realizar la interacción multi-usuario del servidor. En este tipo de arquitectura, cada usuario que
 * realiza solicitudes al servidor recibe un thread.
 *
 * @see cl.blackbird.paper.server.Server
 */
public class ServerThread extends Thread {
    private Socket socket = null;

    public ServerThread(Socket socket) {
        super("ServerThread");
        System.out.println("Creating thread to handle request...");
        this.socket = socket;
    }

    @Override
    public void run(){
        try {
            try {
                socket.setSoTimeout(1000);
                RequestHandler handler = Router.getHandler(
                        Request.createFromInput(socket.getInputStream()),
                        Response.createForOutput(socket.getOutputStream()));
                handler.dispatch();
                System.out.println("Request dispatched!");
            } catch (ServerException e){
                e.write(socket.getOutputStream());
            } catch (SocketTimeoutException e){
                System.out.println("Thread stopped blocking");
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
