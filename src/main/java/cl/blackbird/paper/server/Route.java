package cl.blackbird.paper.server;

import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Una clase que representa una ruta en el sistema para efectos de separar la configuración de la implementación del
 * servidor.
 *
 * @see cl.blackbird.paper.server.Router
 */
public class Route {
    private Pattern pattern;
    private Class<?> handlerClass;

    public Route(String expression, String handlerClassName) throws ClassNotFoundException {
        this(expression, Class.forName(handlerClassName));
    }

    public Route(String expression, Class<?> handlerClass) {
        this.pattern = Pattern.compile(expression);
        this.handlerClass = handlerClass;
    }

    public RequestHandler createHandler(Request request, Response response) {
        try {
            Constructor<?> constructor = handlerClass.getConstructor(Request.class, Response.class);
            return (RequestHandler) constructor.newInstance(request, response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Determina si una ruta a un archivo coincide con la expresión regular que define esta ruta
     *
     * @param path la ruta a un archivo
     * @return verdadero en el caso de que la ruta coincida con la expresión regular.
     */
    public boolean matchingPath(String path){
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }
}
