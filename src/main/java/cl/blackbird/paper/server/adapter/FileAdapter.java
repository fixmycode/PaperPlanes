package cl.blackbird.paper.server.adapter;

import cl.blackbird.paper.server.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

public class FileAdapter implements ContentAdapter {
    private Path fullPath;

    public FileAdapter(){
        this(null);
    }

    public FileAdapter(String route){
        this.setRoute(route);
    }

    private Path createPath(String partialPath) throws InvalidPathException {
        return FileSystems.getDefault().getPath(Server.getConfiguration().getHomeDir(), partialPath);
    }

    public void setRoute(String route){
        this.fullPath = createPath(route);
    }

    public String getFullPath(){
        return this.fullPath.toString();
    }

    @Override
    public String getContentType() {
        if(this.fullPath != null){
            try {
                return Files.probeContentType(this.fullPath);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    public void writeContent(OutputStream out) {
        //TODO Mandar el archivo
    }
}
