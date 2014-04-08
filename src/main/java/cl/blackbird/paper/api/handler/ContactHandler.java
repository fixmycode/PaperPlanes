package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

/**
 * Controla la interacción con un usuario en particular dentro del sistema.
 * Para más información de los métodos revisa el wiki.
 */
public class ContactHandler extends RequestHandler {
    public ContactHandler() {
        this(null, null);
    }

    public ContactHandler(Request request) {
        this(request, null);
    }

    public ContactHandler(Request request, Response response) {
        super(request, response);
        this.response.setContentType("application/json");
    }
    @Override
    public void get() {
        //TODO una respuesta real
        this.response.write("{message: \"it works!\"}");
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
