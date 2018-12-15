import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ConnManagerThread {
    /*private Socket clientSocket;
    private PrintWriter pw;
    private BufferedReader br;
    private Thread thrd ;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private EventManager evnt;
    private boolean listen;
    private int topicSize;
    public ConnManagerThread(Socket cs,EventManager ev, boolean b, ObjectOutputStream out, ObjectInputStream in ){
        try {
            thrd = new Thread(this);
            listen = b;
            //System.out.println(notify);
            clientSocket = cs;
            evnt = ev;
            topicSize = 0;
            synchronized(evnt){
                evnt.addAddress(clientSocket.getInetAddress(), clientSocket.getLocalPort());
                //evnt.displayAddress();
            }
            oos = out;
            ois = in;
            thrd.start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public void run(){
        try {
            //ois = new ObjectInputStream(clientSocket.getInputStream());
            if(!listen) {
                System.out.println((String) ois.readObject());
                while (true) {
                    switch ((String) ois.readObject()) {
                        case "Topic":
                            synchronized (evnt) {
                                evnt.addTopic((Topic) ois.readObject());
                            }
                            break;
                        case "Subscribe":
                            synchronized (evnt){
                                //evnt.addSubscriber();
                                int i = 0;
                                String s = (String)ois.readObject();
                                oos.writeObject("Topics Found");
                                while(i < evnt.topics.size()){
                                    if(evnt.topics.get(i++).keywords.contains(s))
                                        oos.writeObject(evnt.topics.get(i-1).name);
                                }
                                int j = (int)ois.readObject();

                            }
                    }
                }
            }
            else{
                while(true) {
                    synchronized (evnt) {
                        while (topicSize < evnt.topics.size()) {
                            oos.writeObject("New Topic: " + evnt.topics.get(evnt.topics.size() - 1).name);
                            topicSize++;
                        }
                    }
                }
            }
        }
        catch(SocketException e){
            System.out.println("Disconnected");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }*/
}
