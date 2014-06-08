package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.Message;
import cl.blackbird.paper.client.ClientSocket;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class MessageHandler extends RequestHandler {

    public MessageHandler(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void get() throws ServerException {
        String[] parts = this.request.getPathParts();
        String ipAddress = parts[parts.length-2];
        String port = parts[parts.length-1];
        ArrayList<Message> messages = ClientSocket.getMessages(ipAddress + ":" + port);
        JSONArray array = new JSONArray();
        for(Message m : messages) {
            array.put(m.toJSON());
        }
        this.response.setAdapter(new JSONAdapter(array));
        this.response.write();
    }

    @Override
    public void post() throws ServerException {
        JSONObject json = new JSONObject(this.request.getParam("payload"));
        String ipAddress = json.getString("ip");
        String port = String.valueOf(json.getInt("port"));
        String content = json.getString("content");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Message message = new Message(dateFormat.format(new Date()), content);
        ClientSocket.postMessage(ipAddress+":"+port, message);
        this.response.setAdapter(new JSONAdapter(message.toJSON()));
        this.response.write();
    }

    @Override
    public void put() throws ServerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() throws ServerException {
        throw new UnsupportedOperationException();
    }
}
