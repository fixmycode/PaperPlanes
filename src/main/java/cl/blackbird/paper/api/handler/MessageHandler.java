package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.Contact;
import cl.blackbird.paper.api.model.ContactManager;
import cl.blackbird.paper.api.model.Message;
import cl.blackbird.paper.client.ClientSocket;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class MessageHandler extends RequestHandler {

    private final ContactManager manager;

    public MessageHandler(Request request, Response response) {
        super(request, response);
        manager = new ContactManager();
    }

    @Override
    public void get() throws ServerException {
        int id = Integer.valueOf(this.request.getLastPart());
        Contact contact = this.manager.getInstance(id);
        if(contact != null){
            ArrayList<Message> messages = ClientSocket.getMessages(contact.getIpAddress() + ":" + contact.getPort());
            JSONArray array = new JSONArray();
            for(Message m : messages) {
                array.put(m.toJSON());
            }
            this.response.setAdapter(new JSONAdapter(array));
            this.response.write();
        } else {
            throw new ServerException(404);
        }

    }

    @Override
    public void post() throws ServerException {
        JSONObject json = new JSONObject(this.request.getParam("payload"));
        String ipAddress = json.getString("ip");
        String port = String.valueOf(json.getInt("port"));
        String content = json.getString("message");
        Message message = ClientSocket.postMessage(ipAddress+":"+port, content);
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
