package cl.blackbird.paper.server;

/**
 * Excepción generada por el servidor. Su objetivo es guardar el código del error para transportarlo por la cadena de
 * solicitud.
 */
public class ServerException extends Exception {
    private int code;

    public ServerException(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
