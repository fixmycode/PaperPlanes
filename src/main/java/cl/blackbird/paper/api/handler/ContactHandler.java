package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.Contact;
import cl.blackbird.paper.api.model.ContactManager;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.adapter.JSONAdapter;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Controla la interacción con un usuario en particular dentro del sistema.
 * Para más información de los métodos revisa el wiki.
 */
public class ContactHandler extends RequestHandler {

    private final ContactManager manager;

    /**
     * Constructor por defecto del manejador, en particular este se encarga de instanciar un nuevo administrador de
     * contactos.
     * @param request un puntero a la solicitud
     * @param response un puntero a la respuesta
     */
    public ContactHandler(Request request, Response response) {
        super(request, response);
        this.manager = new ContactManager();
    }

    @Override
    public void get() throws ServerException {
        int id = Integer.valueOf(this.request.getLastPart());
        Contact contact = this.manager.getInstance(id);
        if(contact != null){
            JSONObject json = new JSONObject();
            json.put("id", contact.getId());
            json.put("name", contact.getName());
            json.put("port", contact.getPort());
            json.put("ipAddress", contact.getIpAddress());
            this.response.setAdapter(new JSONAdapter(json));
            this.response.write();
        } else {
            throw new ServerException(404);
        }
    }

    @Override
    public void post() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void put() throws ServerException {
        try {
            int id = Integer.valueOf(this.request.getLastPart());
            Contact contact = this.manager.getInstance(id);
            if(contact != null){
                JSONObject json = new JSONObject(this.request.getParam("payload"));
                contact.setPort(json.getInt("port"));
                contact.setIpAddress(json.getString("ipAddress"));
                contact.setName(json.getString("name"));
                contact = manager.updateInstance(contact);
                json.put("id", contact.getId());
                this.response.setAdapter(new JSONAdapter(json));
                this.response.write();
            } else {
                throw new ServerException(404);
            }
        } catch (IOException e) {
            throw new ServerException(500);
        } catch (JSONException e1) {
            throw new ServerException(400);
        }
    }

    @Override
    public void delete() throws ServerException {
        int id = Integer.valueOf(this.request.getLastPart());
        Contact contact = this.manager.getInstance(id);
        if(contact != null){
            JSONObject json = new JSONObject();
            json.put("success", this.manager.deleteInstance(id));
            this.response.setAdapter(new JSONAdapter(json));
            this.response.write();
        } else {
            throw new ServerException(404);
        }
    }
}
