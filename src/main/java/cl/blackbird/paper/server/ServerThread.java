package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        this.socket = socket;
    }

    public void run(){
        try {
            RequestHandler handler;
            try {
                handler = Router.getHandler(
                        Request.createFromInput(socket.getInputStream()),
                        Response.createForOutput(socket.getOutputStream()));
                handler.dispatch();
                System.out.println("Request dispatched!");
            } catch (ServerException e){
                e.printStackTrace();
                e.write(socket.getOutputStream());
            }
            socket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
