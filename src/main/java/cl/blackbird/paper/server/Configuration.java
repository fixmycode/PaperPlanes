package cl.blackbird.paper.server;


import java.util.List;

/**
 * Clase base para la configuración del servidor
 */
public interface Configuration {
    public void initRoutes();
    public List<Route> getRoutes();
    public String getHomeDir();
    public void setHomeDir(String homeDir);
}
