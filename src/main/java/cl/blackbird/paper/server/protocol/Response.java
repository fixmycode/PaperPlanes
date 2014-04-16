package cl.blackbird.paper.server.protocol;

import cl.blackbird.paper.server.adapter.ContentAdapter;
import cl.blackbird.paper.server.adapter.TextAdapter;

import java.io.OutputStream;
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
    private OutputStream outputStream;
    private ContentAdapter adapter;

    public void setAdapter(ContentAdapter adapter){
        this.adapter = adapter;
    }

    public ContentAdapter getAdapter(){
        return this.adapter;
    }

    public int getCode() {
        return code;
    }

    public boolean isError() {
        return (this.code >= 400);
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Response(){
        this(200);
    }

    private Response(int code){
        this(code, new TextAdapter());
    }

    private Response(int code, ContentAdapter adapter){
        this.setCode(code);
        this.setAdapter(adapter);
    }

    public static Response createForOutput(OutputStream outputStream){
        Response response = new Response();
        response.outputStream = outputStream;
        return response;
    }

    public static Response createFromError(OutputStream outputStream, int code){
        Response response = new Response(code);
        response.outputStream = outputStream;
        return response;
    }

    public String getStatusMessage(){
        String msg;
        if((msg = Response.statusMap.get(this.getCode())) != null){
            return msg;
        }
        return "Unknown Status";
    }

    public void write(){
        this.writeHeaders();
        this.adapter.writeContent(outputStream);
    }

    public void writeHeaders(){
        PrintWriter writer = new PrintWriter(outputStream, true);
        writer.println(String.format("HTTP/1.1 %d %s", this.getCode(), this.getStatusMessage()));
        writer.println(String.format("Content-Type: %s; charset=utf-8", this.adapter.getContentType()));
        writer.println();
    }
}
