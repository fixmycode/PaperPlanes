package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.RequestHandler;

/**
 * Una clase que representa una ruta en el sistema para efectos de separar la configuración de la implementación del
 * servidor.
 *
 * @see cl.blackbird.paper.server.Router
 */
public class Route {
    private RequestHandler handler;
    private String expression;

    public RequestHandler getHandler() {
        return handler;
    }

    /**
     * Determina si una ruta a un archivo coincide con la expresión regular que define esta ruta
     *
     * @param path la ruta a un archivo
     * @return verdadero en el caso de que la ruta coincida con la expresión regular.
     */
    public boolean matchingPath(String path){
        return false;
    }
}
