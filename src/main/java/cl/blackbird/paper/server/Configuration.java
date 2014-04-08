package cl.blackbird.paper.server;


import java.util.List;

public interface Configuration {
    public void initRoutes();
    public List<Route> getRoutes();
    public void addRoute(String expression, String className) throws ClassNotFoundException;
}
