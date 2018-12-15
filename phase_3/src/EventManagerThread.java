import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
 * This class connects to the client and reads the option selected from the menu by the client and calls the appropriate 
 * methods in the event manager which fetches the data or alters the data in the SharedData class and returns the response 
 * to the client.
 */
public class EventManagerThread implements Runnable {
    public Socket clientSocket;
    public ServerSocket serverSocket;
    public EventManager evnt;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    public Thread thrd;
    public EventManagerThread(ServerSocket ss, EventManager ev){
        evnt = ev;
        serverSocket = ss;
    }
    public void run(){
        while (true) {
            connect();
            try {
                switch ((String) ois.readObject()) {
                    case "Topic":
                        evnt.addTopic((Topic) ois.readObject()); 
                        break;
                    case "Subscribe":
                        String response = evnt.addSubscriber((String) ois.readObject(), clientSocket.getInetAddress());
                        oos.writeObject(response); 
                        break;
                    case "Publish":
                        String res = evnt.addEvent((Event) ois.readObject());
                        oos.writeObject(res);
                        break;
                    case "Unsubscribe":
                        String unsub = evnt.unsubscribe((int)ois.readObject(), clientSocket.getInetAddress());
                        oos.writeObject(unsub);
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void connect(){
        try {
            clientSocket = serverSocket.accept();
            oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ois = new ObjectInputStream(clientSocket.getInputStream());
            oos.writeObject("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
