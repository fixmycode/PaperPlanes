package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.Contact;
import cl.blackbird.paper.api.model.ContactManager;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

/**
 * Controla la interacción con una lista de contactos almacenados en el sistema.
 * Para más información sobre los métodos revisa el wiki.
 */
public class ContactListHandler extends RequestHandler {
    private JSONAdapter adapter;
    private ContactManager manager;

    public ContactListHandler(Request request, Response response) {
        super(request, response);
        this.manager = new ContactManager();
    }

    @Override
    public void get() {
        //TODO respuesta real
        JSONObject json = new JSONObject();
        json.put("message", "it works!");
        this.response.setAdapter(new JSONAdapter(json));
        this.response.write();
    }

    @Override
    public void post() throws ServerException {
        JSONObject json = new JSONObject(this.request.getParam("payload"));
        Contact contact = new Contact();
        contact.setCreatedAt(new Date());
        contact.setPort(json.getInt("port"));
        contact.setIpAddress(json.getString("ipAddress"));
        contact.setName(json.getString("name"));
        try {
            manager.saveInstance(contact);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServerException(500);
        }
    }

    @Override
    public void put() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException();
    }
}
