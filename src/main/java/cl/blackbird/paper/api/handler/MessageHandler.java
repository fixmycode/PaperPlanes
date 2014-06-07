package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.ContactManager;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONObject;


public class MessageHandler extends RequestHandler {

    public MessageHandler(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void get() throws ServerException {
        JSONObject json = new JSONObject();
        json.put("id", "123");
        json.put("from", "1");
        json.put("to", "2");
        json.put("message", "hola como estas?");
        this.response.setAdapter(new JSONAdapter(json));
        this.response.write();
    }

    @Override
    public void post() throws ServerException {

    }

    @Override
    public void put() throws ServerException {

    }

    @Override
    public void delete() throws ServerException {

    }
}
