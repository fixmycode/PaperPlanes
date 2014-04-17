package cl.blackbird.paper.server.handler;

import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

public class NoRouteHandler extends RequestHandler {

    public NoRouteHandler(Request request, Response response) {
        super(request, response);
    }

    @Override
    public void get() throws ServerException {
        throw new ServerException(404);
    }

    @Override
    public void post() throws ServerException {
        throw new ServerException(404);
    }

    @Override
    public void put() throws ServerException {
        throw new ServerException(404);
    }

    @Override
    public void delete() throws ServerException {
        throw new ServerException(404);
    }
}
