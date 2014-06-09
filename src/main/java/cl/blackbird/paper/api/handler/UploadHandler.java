package cl.blackbird.paper.api.handler;

import cl.blackbird.paper.api.model.Contact;
import cl.blackbird.paper.api.model.ContactManager;
import cl.blackbird.paper.server.ServerException;
import cl.blackbird.paper.server.handler.RequestHandler;
import cl.blackbird.paper.server.protocol.Request;
import cl.blackbird.paper.server.protocol.Response;

public class UploadHandler extends RequestHandler {
    private final ContactManager manager;

    public UploadHandler(Request request, Response response) {
        super(request, response);
        manager = new ContactManager();
    }

    @Override
    public void get() throws ServerException {

    }

    @Override
    public void post() throws ServerException {
        int contactId = Integer.parseInt(this.request.getLastPart());
        Contact contact = this.manager.getInstance(contactId);
        if(contact != null){
            String disposition = this.request.getParam("Content-Disposition");
            String[] parts = disposition.split("\"");
            String fileName = parts[parts.length-1];
            System.out.println(this.request.getParam("payload"));
        }
    }

    @Override
    public void put() throws ServerException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() throws ServerException {
        throw new UnsupportedOperationException();
    }
}
