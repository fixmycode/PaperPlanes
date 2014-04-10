package cl.blackbird.paper.server.adapter;


import java.io.OutputStream;

public interface ContentAdapter {
    public String getContentType();
    public void writeContent(OutputStream out);
}
