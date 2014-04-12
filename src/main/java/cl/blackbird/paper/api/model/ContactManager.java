package cl.blackbird.paper.api.model;

import cl.blackbird.paper.data.ModelManager;
import cl.blackbird.paper.server.Server;

import java.nio.file.FileSystems;

public class ContactManager extends ModelManager<Contact> {
    public static final String dataFileName = "contacts.odb";

    public ContactManager(){
        super(FileSystems.getDefault().getPath(Server.getConfiguration().getHomeDir(), ContactManager.dataFileName).toFile());
    }
}
