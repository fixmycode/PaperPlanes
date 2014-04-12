package cl.blackbird.paper.api.model;

import cl.blackbird.paper.data.BaseModel;

import java.util.Date;

/**
 * Modelo de la clase Contacto para almacenar la información de los contactos en el sistema.
 */
public class Contact extends BaseModel {
    private Integer id;
    private String name;
    private String ipAddress;
    private Integer port;
    private Date createdAt;
    private Date updatedAt;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void update(BaseModel model) {
        Contact contact = (Contact) model;
        if(contact.getId() != null){
            this.setId(contact.getId());
        }
        if(contact.getName() != null){
            this.setName(contact.getName());
        }
        if(contact.getIpAddress() != null){
            this.setIpAddress(contact.getIpAddress());
        }
        if(contact.getPort() != null){
            this.setPort(contact.getPort());
        }
        if(contact.getCreatedAt() != null){
            this.setCreatedAt(contact.getCreatedAt());
        }
        this.updatedAt = new Date();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
