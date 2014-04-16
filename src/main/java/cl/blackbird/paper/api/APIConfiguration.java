package cl.blackbird.paper.api;

import cl.blackbird.paper.server.Configuration;
import cl.blackbird.paper.server.Route;

import java.util.ArrayList;
import java.util.List;


public class APIConfiguration implements Configuration {
    private ArrayList<Route> routes;
    public final String handlerClassPath = "cl.blackbird.paper.api.handler";
    public final String homeDir = "/Users/mammut/Desktop/paper/";

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
    public String getHomeDir() {
        return this.homeDir;
    }

    @Override
    public void addRoute(String expression, String className) throws ClassNotFoundException {
        this.routes.add(new Route(expression, handlerClassPath+"."+className));
    }
}
