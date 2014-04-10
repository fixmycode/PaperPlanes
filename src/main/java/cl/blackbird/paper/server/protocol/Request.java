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

    public static Request createFromInput(InputStream dis) throws IOException {
        Request request = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch;
        while((ch = dis.read()) != -1) {
            baos.write(ch);
            if (ch == '\n') {
                ch = dis.read();
                // the second newline??
                if (ch == '\n' || ch == -1)
                    break;
                baos.write(ch);
            }
        }
        System.out.println("OK");
        String header = new String(baos.toByteArray(), "UTF-8");
        String[] lines = header.split("\\n");
        Map<String, String> properties = new LinkedHashMap<String, String>();
        properties.put("status", lines[0]);
        properties.put("data", lines[lines.length - 1]);

        for(int i = 1 ; i < lines.length - 2; i++) {
            if (lines[i] != "") {
                String[] keyValue = lines[i].split(": ", 2);
                properties.put(keyValue[0], keyValue[1]);
            }
        }

        String[] status = properties.get("status").split("\\s");
        request = new Request(status[0], status[1]);


//        Request request = null;
//        String requestLine = input.readLine();
//        System.out.println(requestLine);
//        String[] requestParts = requestLine.split("\\s");
//        if(requestParts[2].startsWith("HTTP")){
//            request = new Request(requestParts[0], requestParts[1]);
//            System.out.println(String.format("Client is asking for path %s", request.getPath()));
//            //TODO entender el resto del request. Hay que expandir Request.
//        }
        return request;
    }
}
