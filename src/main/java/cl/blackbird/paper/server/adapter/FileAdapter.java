package cl.blackbird.paper.server.adapter;

import cl.blackbird.paper.server.Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.util.HashMap;

public class FileAdapter implements ContentAdapter {
    private Path fullPath;
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
        String path = this.fullPath.toString();
        String[] extension = path.split("\\.");

        return FileAdapter.MIMETypeMap.get(extension[extension.length - 1]);
    }

    @Override
    public void writeContent(OutputStream out) {
        FileInputStream in = null;

        try {
            in = new FileInputStream(this.fullPath.toString());

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
