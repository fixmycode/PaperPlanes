package cl.blackbird.paper.server;


import java.util.List;

public interface Configuration {
    public void initRoutes();
    public List<Route> getRoutes();
    public String getHomeDir();
    public void setHomeDir(String homeDir);
    public void addRoute(String expression, String className) throws ClassNotFoundException;
}
