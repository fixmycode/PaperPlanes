package cl.blackbird.paper.server.protocol;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

/**
 * Modelo de una solicitud HTTP. Se encarga de guardar los datos relacionados con una llamada al servidor, como el
 * archivo solicitado, el modo de la solicitud y tiene un mapeo de los datos ingresados.
 */
public class Request {
    private String mode;
    private String path;
    private String protocol;
    private HashMap<String, String> data;

    /**
     * Retorna el protocolo con el que se ha hecho la solicutud.
     * @return el protocolo usado por esta solicitud
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * Asigna el protocolo con el que se va a realizar esta solicitud.
     * @param protocol el string del protocolo, en la forma NOMBRE/VERSION
     */
    private void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * Retorna el modo en que se hizo la solicitud.
     * @return GET, POST, PUT, DELETE, HEAD
     */
    public String getMode() {
        return mode;
    }

    /**
     * Asigna el modo en que se realiza la solicitud.
     * @param mode el string que representa el modo.
     */
    private void setMode(String mode) {
        this.mode = mode.toUpperCase();
    }

    /**
     * Retorna la ruta que se solicitó.
     * @return un string con la ruta
     */
    public String getPath() {
        return path;
    }

    /**
     * Asigna la ruta de la solicitud. Es privado para que se cree
     * solamente una solicitud por ruta.
     * @param path un string con la ruta solicitada.
     */
    private void setPath(String path) {
        this.path = path;
    }

    /**
     * Separa las partes de la ruta en trozos que pueden ser significantes.
     * Por ejemplo la ruta '/foo/bar.jpeg' queda como el arreglo ['foo', 'bar.jpeg']
     * @return un arreglo con las partes de la ruta.
     */
    public String[] getPathParts(){
        String[] parts = this.path.split("/");
        return parts;
    }

    /**
     * Obtiene la última parte de la ruta.
     * En el caso de la ruta '/foo/bar.jpeg', este método retorna 'bar.jpeg'
     * @return un string con el final de la ruta.
     */
    public String getLastPart(){
        String[] parts = getPathParts();
        return parts[parts.length-1];
    }

    /**
     * Obtiene un parámetro del request por nombre
     * @param key el nombre del parámetro
     * @return el valor del parámetro
     */
    public String getParam(String key){
        return data.get(key);
    }

    /**
     * Agrega una nueva entrada al mapa de parámetros
     * @param key el nombre del parámetro
     * @param value el valor del parámetro
     */
    private void setParam(String key, String value){
        data.put(key, value);
    }

    /**
     * El constructor de solicitudes es privado, solo se espera que los
     * las nuevas solicitudes sean creadas por un socket
     * @param mode el modo de la solicitud
     * @param route la ruta de la solicitud
     */
    private Request(String mode, String route){
        this.data = new HashMap<String, String>();
        this.setMode(mode);
        if(route.equals("/"))
            this.setPath("/index.html");
        else
            this.setPath(route);
    };

    /**
     * Un método para crear solicitudes a través de una entrada de socket.
     * @param input la entrada del socket
     * @return una instancia de solicitud
     * @throws IOException en el caso de que no se pueda leer la entrada
     */
    public static Request createFromInput(InputStream input) throws IOException {
        Request request = null;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int ch;

        while((ch = input.read()) != -1) {
            outputStream.write(ch);
            if (ch == '\n') {
                ch = input.read();
                if (ch == '\n' || ch == '\r')
                    break;
                outputStream.write(ch);
            }
        }

        String headerRequest = new String(outputStream.toByteArray(), "UTF-8");
        String[] lines = headerRequest.split("\\n");
        String[] status = lines[0].split("\\s");

        // Crear el request
        try {
            request = new Request(status[0], status[1]);
        } catch (ArrayIndexOutOfBoundsException e){
            throw new IOException("Malformed Request: "+headerRequest);
        }
        request.setProtocol(status[2]);

        // Llenar los valores del header
        for(int i = 1 ; i < lines.length; i++) {
            String[] keyValue = lines[i].split(": ", 2);
            request.setParam(keyValue[0], keyValue[1]);
        }

        // Sacar el payload del InputStream
        if (request.getMode().equals("POST") || request.getMode().equals("PUT")) {
            outputStream = new ByteArrayOutputStream();
            ch = input.read();
            while((ch = input.read()) != -1) {
                outputStream.write(ch);
                if (ch == '\n' || ch == '\r' || ch == '}'){
                    break;
                }
            }
            String payload = new String(outputStream.toByteArray(), "UTF-8");
            request.setParam("payload", payload);
            System.out.println(request.getParam("payload"));
        }

        return request;
    }

    @Override
    public String toString() {
        return String.format("%s %s", this.getMode(), this.getPath());
    }
}
