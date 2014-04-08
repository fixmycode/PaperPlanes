package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

/**
 * Controla la interacción con una lista de contactos almacenados en el sistema.
 * Para más información sobre los métodos revisa el wiki.
 */
public class ContactListHandler extends RequestHandler {
    public ContactListHandler() {
        this(null, null);
    }

    public ContactListHandler(Request request) {
        this(request, null);
    }

    public ContactListHandler(Request request, Response response) {
        super(request, response);
        this.response.setContentType("application/json");
    }

    @Override
    public void get() {
        //TODO respuesta real
        this.response.write("{message: \"it works!\"}");
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
