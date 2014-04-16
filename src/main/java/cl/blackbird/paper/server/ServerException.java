package cl.blackbird.paper.server;

import cl.blackbird.paper.server.adapter.TextAdapter;
import cl.blackbird.paper.server.protocol.Response;

import java.io.OutputStream;


public class ServerException extends Exception {
    private int code;

    public ServerException(int code) {
        this.code = code;
    }

    public void write(OutputStream out){
        System.out.println(String.format("Throwing %d Error", this.code));
        Response response = Response.createFromError(out, this.code);
        ((TextAdapter) response.getAdapter()).setContent(
                String.format(
                        "<html><head><title>Error %d</title></head>\n" +
                        "<body><h1>Error %d</h1>\n" +
                        "<p>%s</p>\n" +
                        "<p>Sorry &lt;/3</p></body></html>\n",
                        response.getCode(), response.getCode(), response.getStatusMessage()));
        ((TextAdapter) response.getAdapter()).isHTML = true;
        response.write();
    }
}
