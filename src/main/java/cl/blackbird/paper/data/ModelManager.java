package cl.blackbird.paper.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase base de un administrador de modelos
 * @param <T> la clase que define el modelo a administrar
 */
public abstract class ModelManager<T extends BaseModel> {
    protected final File dataFile;
    protected ArrayList<T> objectList;

    /**
     * El constructor asegura la creación de un archivo de lista
     * @param dataFile el puntero al archivo donde se guarda la lista
     */
    protected ModelManager(File dataFile) {
        this.dataFile = dataFile;
        this.buildList();
    }

    /**
     * Retorna un puntero al archivo donde se guarda la lista
     * @return un puntero al archivo.
     */
    protected File getDataFile(){
        return this.dataFile;
    }

    /**
     * Obtiene una instancia del modelo usando un identificador
     * @param id el identificador a consultar
     * @return la instancia correspondiente o nulo.
     */
    public T getInstance(int id){
        for(T object: this.objectList){
            if(object.getId().equals(id)){
                return object;
            }
        }
        return null;
    }

    /**
     * Guarda una instancia del modelo en la lista. En el caso de que la instancia ya exista en la lista, entonces llama
     * automáticamente al método de actualización.
     * @param model la instancia a guardar
     * @return la instancia guardada con su id
     * @throws IOException
     */
    public T saveInstance(T model) throws IOException {
        if(model.getId() != null){
            return this.updateInstance(model);
        }
        model.setId(this.getNextId());
        this.objectList.add(model);
        this.saveList(this.objectList);
        return model;
    }

    /**
     * Actualiza una instancia del modelo
     * @param model la instancia con los datos nuevos
     * @return la instancia actualizada o nulo
     * @throws IOException
     */
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

    /**
     * Borra una instancia de la lista
     * @param id el id de la instancia a borrar
     * @return verdadero si se pudo borrar, falso de otra forma
     */
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

    /**
     * Construye la lista.
     * Trata de cargar una lista existente o crea una nueva.
     */
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

    /**
     * Carga una lista existente
     * @return una lista con los datos existentes
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private ArrayList<T> loadList() throws IOException, ClassNotFoundException {
        ArrayList<T> list;
        FileInputStream fileInputStream = new FileInputStream(this.getDataFile());
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        list = (ArrayList<T>) objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
        return list;
    }

    /**
     * Guarda una lista en el archivo
     * @param list la lista a guardar
     * @throws IOException
     */
    private void saveList(ArrayList<T> list) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(this.getDataFile());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this.objectList);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    /**
     * Devuelve todos las instancias de la lista
     * @return
     */
    public List<T> getAll(){
        return this.objectList;
    }

    /**
     * Obtiene el siguiente ID desocupado en la lista
     * @return el ID desocupado
     */
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
