package cl.blackbird.paper.server;

import cl.blackbird.paper.server.protocol.Response;

import java.io.PrintWriter;


public class ServerException extends Exception {
    private Response response;

    public ServerException(int code) {
        this.response = new Response(code);
    }

    public void write(PrintWriter writer){
        this.response.write(writer);
    }
}
