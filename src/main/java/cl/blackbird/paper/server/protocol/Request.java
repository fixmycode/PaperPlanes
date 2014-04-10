package cl.blackbird.paper.server.protocol;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Modelo de una solicitud HTTP. Se encarga de guardar los datos relacionados con una llamada al servidor, como el
 * archivo solicitado, el modo de la solicitud y tiene un mapeo de los datos ingresados.
 */
public class Request {
    private String mode;
    private String path;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String protocol;
    private HashMap<String, String> data;

    public String getMode() {
        return mode;
    }

    private void setMode(String mode) {
        this.mode = mode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParam(String key){
        return data.get(key);
    }

    private void setParam(String key, String value){
        data.put(key, value);
    }

    private Request(String mode, String route){
        this.data = new HashMap<String, String>();
        this.mode = mode;
        this.path = route;
    };

    public static Request createFromInput(InputStream input) throws IOException {
        Request request = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch;

        while((ch = input.read()) != -1) {
            baos.write(ch);
            if (ch == '\n') {
                ch = input.read();
                if (ch == '\n' || ch == '\r')
                    break;
                baos.write(ch);
            }
        }

        String headerRequest = new String(baos.toByteArray(), "UTF-8");
        String[] lines = headerRequest.split("\\n");
        String[] status = lines[0].split("\\s");

        // Crear el request
        request = new Request(status[0], status[1]);
        request.setProtocol(status[2]);

        // Llenar los valores del header
        for(int i = 1 ; i < lines.length; i++) {
            String[] keyValue = lines[i].split(": ", 2);
            request.setParam(keyValue[0], keyValue[1]);
        }

        // Sacar el payload del InputStream
        if (request.getMode().equals("POST") || request.getMode().equals("PUT")) {
            baos = new ByteArrayOutputStream();
            ch = input.read();
            while((ch = input.read()) != -1) {
                baos.write(ch);
                if (ch == '\n' || ch == '\r' || ch == '}'){
                    break;
                }
            }
            String payload = new String(baos.toByteArray(), "UTF-8");
            request.setParam("payload", payload);
        }

        return request;
    }
}
