package cl.blackbird.paper.api;

import cl.blackbird.paper.api.handler.ContactHandler;
import cl.blackbird.paper.api.handler.ContactListHandler;
import cl.blackbird.paper.server.Configuration;
import cl.blackbird.paper.server.Route;
import cl.blackbird.paper.server.handler.StaticHandler;

import java.util.ArrayList;
import java.util.List;

public class APIConfiguration implements Configuration {
    private ArrayList<Route> routes;
    public String homeDir = "/Users/oni/Desktop/paper/";

    public APIConfiguration() {
        this.routes = new ArrayList<Route>();
        this.initRoutes();
    }

    @Override
    public void initRoutes() {
        this.addRoute("/api/v1/contacts", ContactListHandler.class);
        this.addRoute("/api/v1/contacts/(\\d+)", ContactHandler.class);
        this.addRoute("/.*", StaticHandler.class);
    }

    @Override
    public List<Route> getRoutes() {
        return routes;
    }

    @Override
    public String getHomeDir() {
        return this.homeDir;
    }

    @Override
    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    /**
     * Añade rutas para manejadores resueltos en tiempo de compilación
     * @param expression la expresión regular relacionada
     * @param handlerClass la clase
     */
    public void addRoute(String expression, Class<?> handlerClass) {
        this.routes.add(new Route(expression, handlerClass));
    }
}
