package cl.blackbird.paper.server;

import cl.blackbird.paper.server.protocol.Response;

import java.io.OutputStream;


public class ServerException extends Exception {
    private Response response;

    public ServerException(int code) {
        this.response = new Response(code);
    }

    public void write(OutputStream out){
        this.response.write();
    }
}
