package cl.blackbird.paper.server.handler;

import cl.blackbird.paper.server.adapter.TextAdapter;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import cl.blackbird.paper.server.ServerException;

/**
 * La clase base para los controladores. Se encarga de usar el método HTTP correcto para cada solicitud en particular.
 * Guarda una referencia a la solicitud y la respuesta que son utilizadas por las subclases para enviar información al
 * cliente. Los modos HTTP soportados hasta ahora son GET, POST, PUT y DELETE.
 *
 * @see cl.blackbird.paper.server.protocol.Request
 * @see cl.blackbird.paper.server.protocol.Response
 */
public abstract class RequestHandler {
    protected Request request;
    protected Response response;

    public RequestHandler(Request request, Response response){
        this.response = response;
        this.request = request;
    }

    public abstract void get() throws ServerException;
    public abstract void post() throws ServerException;
    public abstract void put() throws ServerException;
    public abstract void delete() throws ServerException;

    /**
     * Usa el método adecuado del manejador dependiendo del modo especificado en la solicitud.
     */
    public void dispatch() {
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
            this.error(501);
        } catch (ServerException e){
            this.error(e.getCode());
        }
    }

    /**
     * En el caso de un error, altera la respuesta de forma que se escriba el código de error en la pantalla del cliente.
     * @param code el código de error
     */
    private void error(int code){
        this.response.setCode(code);
        ((TextAdapter) response.getAdapter()).setContent(
                String.format(
                        "<html><head><title>Error %d</title></head>\n" +
                                "<body><h1>Error %d</h1>\n" +
                                "<p>%s</p>\n" +
                                "<p>Sorry &lt;/3</p></body></html>\n",
                        response.getCode(), response.getCode(), response.getStatusMessage()));
        ((TextAdapter) response.getAdapter()).isHTML = true;
        response.write();
    }

    @Override
    public String toString() {
        return this.request.toString() + " " + this.response.toString();
    }

    public boolean isError() {
        return this.response.isError();
    }
}
