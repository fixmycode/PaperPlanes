package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONObject;

/**
 * Controla la interacción con una lista de contactos almacenados en el sistema.
 * Para más información sobre los métodos revisa el wiki.
 */
public class ContactListHandler extends RequestHandler {
    private JSONAdapter adapter;

    public ContactListHandler() {
        this(null, null);
    }

    public ContactListHandler(Request request) {
        this(request, null);
    }

    public ContactListHandler(Request request, Response response) {
        super(request, response);
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
    public void post() {
        //TODO persistir contacto
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
