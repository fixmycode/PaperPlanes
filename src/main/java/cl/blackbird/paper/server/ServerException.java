package cl.blackbird.paper.server;

public class ServerException extends Exception {
    private int code;

    public ServerException(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }
}
