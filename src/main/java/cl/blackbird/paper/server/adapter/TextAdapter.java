package cl.blackbird.paper.server.adapter;

import java.io.OutputStream;
import java.io.PrintWriter;

public class TextAdapter implements ContentAdapter {
    private String content;

    public TextAdapter(){
        this.content = "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContentType() {
        return "text/plain";
    }

    @Override
    public void writeContent(OutputStream out) {
        PrintWriter writer = new PrintWriter(out, true);
        writer.println(content);
    }
}
