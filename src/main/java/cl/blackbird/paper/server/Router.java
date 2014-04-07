package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

/**
 * Controla el acceso a los distintos controladores del API usando la información dispuesta por las solicitudes y
 * entregandole al controlador una referencia de esta y el objeto de respuesta que espera el cliente.
 *
 * @see cl.blackbird.paper.server.handler.RequestHandler
 * @see cl.blackbird.paper.server.Route
 */
public class Router {

    /**
     * Construye el manejador relacionado con una solicitud en particular.
     *
     * @param request la solicitud a revisar
     * @param response la referencia a la respuesta para el cliente
     * @return una referencia al controlador de la solicitud
     * @throws ServerException
     */
    public static RequestHandler getHandler(Request request, Response response) throws ServerException {
        try {
            Route route = Router.solvePath(request.getPath());
            return route.getHandler();
        } catch (NullPointerException e){
            throw new ServerException(404);
        }
    }

    /**
     * Compara todas las rutas almacenadas en el sistema con una ruta de archivo indicada.
     *
     * @param path la ruta al archivo que se comparará
     * @return El controlador de esa ruta de archivo
     */
    private static Route solvePath(String path) {
        return null;
    }
}
