package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

public class StaticHandler extends RequestHandler{

    public StaticHandler(Request request, Response response){
        super(request, response);
        //TODO leer configuración
    }

    @Override
    public void get() {
        //TODO responder con un archivo
        this.response.write();
    }

    @Override
    public void post() {
        throw new UnsupportedOperationException();
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
