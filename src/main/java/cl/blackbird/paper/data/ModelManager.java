package cl.blackbird.paper.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ModelManager<T extends BaseModel> {
    protected final File dataFile;
    protected ArrayList<T> objectList;

    protected ModelManager(File dataFile) {
        this.dataFile = dataFile;
        this.buildList();
    }

    protected File getDataFile(){
        return this.dataFile;
    }

    public T getInstance(int id){
        for(T object: this.objectList){
            if(object.getId().equals(id)){
                return object;
            }
        }
        return null;
    }

    public T saveInstance(T model) throws IOException {
        if(model.getId() != null){
            return this.updateInstance(model);
        }
        model.setId(this.getNextId());
        this.objectList.add(model);
        this.saveList(this.objectList);
        return model;
    }

    public T updateInstance(T model) throws IOException {
        if(model.getId() == null) {
            return null;
        }
        for(T object : this.objectList){
            if(model.getId().equals(object.getId())){
                object.update(model);
                this.saveList(this.objectList);
                return object;
            }
        }
        return null;
    }

    public boolean deleteInstance(int id) {
        for(T object : this.objectList){
            if(object.getId().equals(id)){
                boolean success = this.objectList.remove(object);
                if(success){
                    try {
                        this.saveList(this.objectList);
                        return success;
                    } catch (IOException e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    protected void buildList(){
        if(this.objectList == null){
            try {
                this.objectList = this.loadList();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                try {
                    this.objectList = new ArrayList<T>();
                    this.saveList(this.objectList);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private ArrayList<T> loadList() throws IOException, ClassNotFoundException {
        ArrayList<T> list;
        FileInputStream fileInputStream = new FileInputStream(this.getDataFile());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        list = (ArrayList<T>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return list;
    }

    private void saveList(ArrayList<T> list) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.getDataFile());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.objectList);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    public List<T> getAll(){
        return this.objectList;
    }

    public Integer getNextId(){
        Integer maxId = -1;
        for(T object : this.objectList){
            if(object.getId() > maxId){
                maxId = object.getId();
            }
        }
        return ++maxId;
    }
}
