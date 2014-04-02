package cl.blackbird.paper.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;


public class Request {
    private String mode;
    private String route;
    private HashMap<String, String> data;

    public String getMode() {
        return mode;
    }

    private void setMode(String mode) {
        this.mode = mode;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
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
        this.route = route;
    };

    public static Request createFromInput(BufferedReader input) throws IOException {
        Request request = null;
        String requestLine = input.readLine();
        System.out.println(requestLine);
        String[] requestParts = requestLine.split("\\s");
        if(requestParts[2].startsWith("HTTP")){
            request = new Request(requestParts[0], requestParts[1]);
            System.out.println(String.format("Client is asking for route %s", request.getRoute()));
        }
        return request;
    }

    public Response handle() {
        return null;
    }
}
