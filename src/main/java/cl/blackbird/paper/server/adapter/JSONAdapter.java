package cl.blackbird.paper.server.adapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.PrintWriter;


/**
 * Un adaptador que toma una estructura de objetos JSON y lo devuelve en forma de texto al cliente.
 */
public class JSONAdapter implements ContentAdapter {
    private JSONObject jsonObject;
    private JSONArray jsonArray;

    public JSONAdapter(){
        this.jsonObject = new JSONObject();
    }

    public JSONAdapter(JSONObject obj){
        this.jsonObject = obj;
        this.jsonArray = null;
    }

    public JSONAdapter(JSONArray array){
        this.jsonArray = array;
        this.jsonObject = null;
    }

    public JSONArray getJSONArray(){
        return jsonArray;
    }

    public void setJSONArray(JSONArray array){
        this.jsonArray = array;
        this.jsonObject = null;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        this.jsonArray = null;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public void writeContent(OutputStream out) {
        PrintWriter writer = new PrintWriter(out, true);
        if(jsonObject != null){
            jsonObject.write(writer);
        } else if(jsonArray != null){
            jsonArray.write(writer);
        }
        writer.println();
    }
}
