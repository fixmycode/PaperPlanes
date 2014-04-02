package cl.blackbird.paper.protocol.error;

import cl.blackbird.paper.protocol.Response;

import java.io.PrintWriter;

public class ErrorResponse extends Response {
    int code;
    String message;

    @Override
    public void sendToWriter(PrintWriter writer) {
        writer.println(String.format("HTTP/1.1 %d %s", this.code, this.message));
    }

    private ErrorResponse(){
        this.code = 501;
        this.message = "Not Implemented";
    }

    public static ErrorResponse fail(int error) {
        return new ErrorResponse();
    }
}
