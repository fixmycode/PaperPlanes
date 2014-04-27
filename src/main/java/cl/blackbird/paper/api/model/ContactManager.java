package cl.blackbird.paper.api.model;

import cl.blackbird.paper.data.ModelManager;
import cl.blackbird.paper.server.Server;

import java.nio.file.FileSystems;

/**
 * Esta subclase le indica al administrador de modelos donde buscar la información de los contactos.
 * Se ha configurado que los busque en el mismo directorio de partida.
 */
public class ContactManager extends ModelManager<Contact> {
    public static final String dataFileName = "contacts.odb";

    public ContactManager(){
        super(FileSystems.getDefault().getPath(Server.getConfiguration().getHomeDir(), ContactManager.dataFileName).toFile());
    }
}
