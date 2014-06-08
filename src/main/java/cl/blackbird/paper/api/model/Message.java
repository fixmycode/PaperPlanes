package cl.blackbird.paper.api.model;


import org.json.JSONObject;

import java.io.Serializable;

public class Message implements Serializable {
    private String date;
    private String content;

    public Message(String date, String content) {
        this.date = date;
        this.content = content;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("date", this.date);
        json.put("content", this.content);
        return json;
    }
}
