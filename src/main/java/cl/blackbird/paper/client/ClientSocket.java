package cl.blackbird.paper.client;

import cl.blackbird.paper.api.model.Message;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClientSocket {
    private static BufferedReader reader;
    private static PrintStream printer;
    private static Socket socket;

    public static void init(String host, int port) {
        try {
            if(socket == null) {
                socket = new Socket(host, port);
                socket.setSoTimeout(1000);
                printer = new PrintStream(socket.getOutputStream());
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            }
        } catch (UnknownHostException e) {
            System.out.println("Can't connect to a PaperServer in "+host+":"+port);
        } catch (IOException e) {
            System.out.println("Couldn't connect to PaperServer");
        }
    }

    public static void destroy() throws IOException {
        if(socket != null) {
            printer.println("BYEE");
            reader.close();
            printer.close();
            socket.close();
            socket = null;
        }
    }

    public synchronized static ArrayList<Message> getMessages(String address) {
        ArrayList<Message> messages = new ArrayList<Message>();
        if(socket == null){
            return messages;
        }
        printer.println("PULL "+address);
        try {
            String response = reader.readLine();
            System.out.println(response);
            String[] parts = response.split(" ");
            int messageCount = 0;
            int fileCount = 0;
            if(parts[0].equals("OK")){
                messageCount = Integer.valueOf(parts[1]);
                fileCount = Integer.valueOf(parts[3]);
            }
            if(messageCount > 0){
                for(int i = 0; i < messageCount; i++){
                    String[] messageParts = reader.readLine().split(" ", 4);
                    System.out.println(messageParts[1]+" "+messageParts[2]+" "+messageParts[3]);
                    String length = messageParts[1];
                    String date = messageParts[2];
                    String content = messageParts[3];
                    Message message = new Message(date, content);
                    messages.add(message);
                }
            }
            if(fileCount > 0){
                for(int i = 0; i < fileCount; i++){
                    String[] messageParts = reader.readLine().split(" ", 4);
                    System.out.println(messageParts[1]+" "+messageParts[2]+" "+messageParts[3]);
                    String length = messageParts[1];
                    String date = messageParts[2];
                    String content = messageParts[3];
                    Message document = new Message(date, content);
                    messages.add(document);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public synchronized static Message postMessage(String destiny, String content) {
        if(socket == null){
            return null;
        }
        int length = content.getBytes().length;
        printer.println(String.format("PUSH %s %d %s", destiny, length, content));
        try {
            String response = reader.readLine();
            String[] parts = response.split(" ");
            if(parts[0].equals("OK")){
                return new Message(parts[2], content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int readInt() throws IOException, NumberFormatException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Boolean trailing = true;
        int ch;
        while ((ch = reader.read()) != -1) {
            if (ch == ' ' && !trailing) {
                break;
            } else if (ch >= '0' && ch <= '9') {
                trailing = false;
                output.write(ch);
            } else {
                break;
            }
        }
        return Integer.parseInt(new String(output.toByteArray(), "UTF-8"));
    }

    private static String readWord() throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Boolean trailing = true;
        int ch;
        while ((ch = reader.read()) != -1) {
            if (ch == ' ' && !trailing) {
                break;
            } else {
                trailing = false;
                output.write(ch);
            }
        }
        return new String(output.toByteArray(), "UTF-8");
    }

    private static String readMessage(int length) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int remaining = length;
        while (remaining-- > 0) {
            output.write(reader.read());
        }
        return new String(output.toByteArray(), "UTF-8");
    }
}
