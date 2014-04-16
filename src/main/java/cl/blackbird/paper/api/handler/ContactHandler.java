package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONObject;

/**
 * Controla la interacción con un usuario en particular dentro del sistema.
 * Para más información de los métodos revisa el wiki.
 */
public class ContactHandler extends RequestHandler {

    public ContactHandler(Request request, Response response) {
        super(request, response);
    }
    @Override
    public void get() {
        //TODO una respuesta real
        JSONObject json = new JSONObject();
        json.put("message", "it works!");
        this.response.setAdapter(new JSONAdapter(json));
        this.response.write();
    }

    @Override
    public void post() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put() {
        //TODO actualizar un contacto
    }

    @Override
    public void delete() {

    }
}
