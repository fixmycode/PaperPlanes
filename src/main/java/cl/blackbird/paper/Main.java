package cl.blackbird.paper;

public class Main {

    public String saludar(){
        return "Hola, Mundo!";
    }

    public static void main(String[] args){
        Main app = new Main();
        System.out.println(app.saludar());
    }
}
