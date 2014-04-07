package cl.blackbird.paper.server.protocol;

import java.io.PrintWriter;

public class Response {
    private int code;
    private String message;
    private boolean isError;
    private PrintWriter writer;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public boolean isError() {
        return isError;
    }

    public void setCode(int code) {
        this.code = code;
        if(code >= 400){
            this.isError = true;
            this.message = Response.status_message(code);
        }
    }

    public Response(){
        this(200);
    }

    public Response(int code){
        this.setCode(code);
    }

    public static Response createForOutput(PrintWriter writer){
        Response response = new Response();
        response.writer = writer;
        return response;
    }

    public static String status_message(int code){
        //TODO define responses
        return "Unknown";
    }

    public void write(){
        if(this.writer != null){
            this.write(this.writer);
        }
    }

    public void write(PrintWriter writer){
        //TODO response structure
    }
}
