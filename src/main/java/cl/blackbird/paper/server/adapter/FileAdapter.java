package cl.blackbird.paper.server.adapter;

import cl.blackbird.paper.server.Server;
import cl.blackbird.paper.server.ServerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.HashMap;


/**
 * Un adaptador para devolver archivos estáticos que pueden ser leídos con los flujos de entrada de Java.
 */
public class FileAdapter implements ContentAdapter {
    private static HashMap<String, String> MIMETypeMap;
    static {
        MIMETypeMap = new HashMap<String, String>();
        MIMETypeMap.put("html", "text/html");
        MIMETypeMap.put("css",  "text/css");
        MIMETypeMap.put("js",   "application/javascript");
        MIMETypeMap.put("png",  "image/png");
        MIMETypeMap.put("jpg",  "image/jpeg");
        MIMETypeMap.put("jpeg", "image/jpeg");
        MIMETypeMap.put("gif",  "image/gif");
        MIMETypeMap.put("pdf",  "application/pdf");
        MIMETypeMap.put("map",  "application/x-navimap");
    }

    private Path fullPath;
    private FileInputStream inputStream;

    public FileAdapter(String route) throws ServerException {
        if(route != null) {
            this.setRoute(route);
        }
    }

    /**
     * Convierte una ruta de solicitud a una ruta de sistema de archivos
     * @param partialPath la ruta de la solicitud
     * @return la ruta del sistema de archivos partiendo desde el directorio base de la aplicación.
     * @throws InvalidPathException
     */
    private Path createPath(String partialPath) throws InvalidPathException {
        return FileSystems.getDefault().getPath(Server.getConfiguration().getHomeDir(), partialPath);
    }

    /**
     * Busca el archivo en el sistema de archivos de la máquina,
     * si no lo encuentra emite una excepción 404.
     * @param route la ruta del archivo en el sistema de archivos.
     * @throws ServerException
     */
    public void setRoute(String route) throws ServerException {
        this.fullPath = createPath(route);
        try {
            this.inputStream = new FileInputStream(this.fullPath.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new ServerException(404);
        }
    }

    public String getFullPath(){
        return this.fullPath.toString();
    }

    @Override
    public String getContentType() {
        String path = this.fullPath.toString();
        String[] extension = path.split("\\.");

        return FileAdapter.MIMETypeMap.get(extension[extension.length - 1]);
    }

    @Override
    public void writeContent(OutputStream out) {
        int c;
        try {
            while ((c = inputStream.read()) != -1) {
                out.write(c);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
