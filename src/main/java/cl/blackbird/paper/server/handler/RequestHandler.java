package cl.blackbird.paper.server.handler;

import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import cl.blackbird.paper.server.ServerException;

/**
 * La clase base para los controladores. Se encarga de usar el método HTTP correcto para cada solicitud en particular.
 * Guarda una referencia a la solicitud y la respuesta que son utilizadas por las subclases para enviar información al
 * cliente.
 *
 * @see cl.blackbird.paper.server.protocol.Request
 * @see cl.blackbird.paper.server.protocol.Response
 */
public abstract class RequestHandler {
    protected Request request;
    protected Response response;


    public RequestHandler(){
        this(null, null);
    }

    public RequestHandler(Request request){
        this(request, null);
    }

    public RequestHandler(Request request, Response response){
        this.response = response;
        this.request = request;
    }

    public void error(int code){
        this.response = new Response(code);
    }

    public void abort(int code) throws ServerException {
        throw new ServerException(code);
    }

    public abstract void get() throws ServerException;
    public abstract void post() throws ServerException;
    public abstract void put() throws ServerException;
    public abstract void delete() throws ServerException;

    public void dispatch() throws ServerException {
        try {
            if (this.request.getMode().equals("GET")) {
                this.get();
            } else if (this.request.getMode().equals("POST")){
                this.post();
            } else if (this.request.getMode().equals("PUT")){
                this.put();
            } else if (this.request.getMode().equals("DELETE")){
                this.delete();
            }
        } catch (UnsupportedOperationException e){
            e.printStackTrace();
            throw new ServerException(501);
        }
    }

}
