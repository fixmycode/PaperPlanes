package cl.blackbird.paper.protocol;

import java.io.PrintWriter;

public abstract class Response {
    public abstract void sendToWriter(PrintWriter writer);
}
