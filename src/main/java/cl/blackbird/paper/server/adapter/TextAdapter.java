package cl.blackbird.paper.server.adapter;

import java.io.OutputStream;
import java.io.PrintWriter;


/**
 * Un adaptador para enviar texto plano directo al cliente.
 * También puede decir que el texto es HTML con un flag especial.
 */
public class TextAdapter implements ContentAdapter {
    private String content;
    public boolean isHTML = false;

    public TextAdapter(){
        this.content = "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getContentType() {
        if(isHTML){
            return "text/html";
        }
        return "text/plain";
    }

    @Override
    public void writeContent(OutputStream out) {
        PrintWriter writer = new PrintWriter(out, true);
        writer.println(content);
    }
}
