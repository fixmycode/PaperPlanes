package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.NoRouteHandler;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

import java.util.ArrayList;

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
     */
    public static RequestHandler getHandler(Request request, Response response) {
        try {
            Route route = Router.solvePath(request.getPath());
            return route.createHandler(request, response);
        } catch (NullPointerException e){
            return new NoRouteHandler(request, response);
        }
    }

    /**
     * Compara todas las rutas almacenadas en el sistema con una ruta de archivo indicada.
     *
     * @param path la ruta al archivo que se comparará
     * @return El controlador de esa ruta de archivo
     */
    private static Route solvePath(String path) {
        ArrayList<Route> routes = (ArrayList<Route>) Server.getConfiguration().getRoutes();
        for (Route nextRoute : routes) {
            if (nextRoute.matchingPath(path)) {
                return nextRoute;
            }
        }
        return null;
    }
}
