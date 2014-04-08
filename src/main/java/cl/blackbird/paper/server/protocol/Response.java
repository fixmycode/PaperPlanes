package cl.blackbird.paper.server.protocol;

import java.io.PrintWriter;
import java.util.HashMap;

public class Response {
    private static HashMap<Integer, String> statusMap;
    static {
        statusMap = new HashMap<Integer, String>();
        statusMap.put(200, "OK");
        statusMap.put(400, "Bad Request");
        statusMap.put(401, "Unauthorized");
        statusMap.put(402, "Request Failed");
        statusMap.put(404, "Not Found");
        statusMap.put(500, "Server Error");
        statusMap.put(501, "Not Implemented");
    }

    private int code;
    private boolean isError;
    private PrintWriter writer;
    private String contentType;


    public int getCode() {
        return code;
    }

    public boolean isError() {
        return isError;
    }

    public void setCode(int code) {
        this.code = code;
        if(code >= 400){
            this.isError = true;
        }
    }

    public void setContentType(String contentType){
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }

    public Response(){
        this(200);
    }

    public Response(int code){
        this(code, "text/plain");
    }

    public Response(int code, String contentType){
        this.setCode(code);
        this.setContentType(contentType);
    }

    public static Response createForOutput(PrintWriter writer){
        Response response = new Response();
        response.writer = writer;
        return response;
    }

    public String getStatusMessage(){
        String msg;
        if((msg = Response.statusMap.get(200)) != null){
            return msg;
        }
        return "Unknown Status";
    }

    public void write(){
        this.write(null);
    }

    public void write(String content){
        if(this.writer != null){
            this.write(this.writer, content);
        }
    }

    public void write(PrintWriter writer, String content){
        writer.println(String.format("HTTP/1.1 %d %s", this.getCode(), this.getStatusMessage()));
        writer.println(String.format("Content-Type: %s; charset=utf-8", this.getContentType()));
        writer.println("");
        writer.println(content);
        //TODO pensar en una forma para responder con un buffer de varias líneas.
    }
}
