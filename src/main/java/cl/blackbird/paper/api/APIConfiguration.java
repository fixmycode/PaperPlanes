package cl.blackbird.paper.api;

import cl.blackbird.paper.server.Configuration;
import cl.blackbird.paper.server.Route;

import java.util.ArrayList;
import java.util.List;


public class APIConfiguration implements Configuration {
    ArrayList<Route> routes;
    String handlerClassPath = "cl.blackbird.paper.api.handler";
    //TODO crear rutas de configuración para el StaticHandler

    public APIConfiguration() {
        this.routes = new ArrayList<Route>();
        this.initRoutes();
    }

    @Override
    public void initRoutes() {
        try {
            this.addRoute("/api/v1/contacts", "ContactListHandler");
            this.addRoute("/api/v1/contacts/(\\d+)", "ContactHandler");
            this.addRoute("/.*", "StaticHandler");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Route> getRoutes() {
        return routes;
    }

    @Override
    public void addRoute(String expression, String className) throws ClassNotFoundException {
        this.routes.add(new Route(expression, handlerClassPath+"."+className));
    }

    public String getHomeDir() { return "/Users/mammut/Desktop/paper"; }
}
