package cl.blackbird.paper.server.adapter;

import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintWriter;

public class JSONAdapter implements ContentAdapter {
    private JSONObject json;

    public JSONAdapter(){
        this.json = new JSONObject();
    }

    public JSONAdapter(JSONObject obj){
        this.json = obj;
    }

    public JSONObject getJSON(){
        return json;
    }

    public void setJSON(JSONObject json){
        this.json = json;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public void writeContent(OutputStream out) {
        PrintWriter writer = new PrintWriter(out, true);
        json.write(writer);
        writer.println();
    }
}
