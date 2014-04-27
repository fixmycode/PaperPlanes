package cl.blackbird.paper.server.handler;

import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.FileAdapter;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

/**
 * Manejador para archivos estáticos. Su misión es resolver rutas de archivo, cargarlas en un adaptador de archivos y
 * enviarlas al usuario.
 */
public class StaticHandler extends RequestHandler{

    public StaticHandler(Request request, Response response){
        super(request, response);
    }

    @Override
    public void get() throws ServerException {
        this.response.setAdapter(new FileAdapter(this.request.getPath()));
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
