
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Client{
    /*private Socket socket;
=======
public class Client implements Runnable,Publisher{
    private Socket socket;
>>>>>>> f67d99aab055942761125ae7583c86124a57fc5b

    private static PrintWriter pw;
    private BufferedReader br;
    private int newPort;
    private static ObjectInputStream ois;
    private static ObjectOutputStream oos;
    private static Thread thrd;
    //private static Scanner scan;

    public Client(int port, String host){
        try{
            socket = new Socket(host, port);
            pw = new PrintWriter(socket.getOutputStream(),true);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw.println("Client Connected");
            newPort = Integer.parseInt(br.readLine());
            System.out.println(newPort);
            pw.close();
            br.close();
            socket.close();
            thrd = new Thread(this);
        }
        catch(Exception e){
            System.out.println("Not Connected");
        }
    }
    public static void main(String[] args) {
        Client client = new Client(5000, "localhost");
        client.connectToNewPort("localhost");
        thrd.start();
        Scanner scan = new Scanner(System.in);
        while(true){
            System.out.println("Enter 1 to advertise" + "\n" + "Enter 2 Subscribe");
            int i = 0;
            try {
                i = scan.nextInt();
            }
            catch(InputMismatchException e){
                System.out.println("Incorrect input");
                continue;
            }
            switch(i){
                case 1:
                    try {
                        oos.writeObject("Topic");
                    }
                    catch(IOException e){
                        System.out.println("IO Error");
                        continue;
                    }
                    System.out.println("Enter Topic");
                    String topic = scan.next();
                    List<String> key = new ArrayList<String>();
                    key.add(topic);
                    while(!scan.hasNext("0")) {
                        key.add(scan.nextLine());
                    }
                    client.advertise( new Topic(topic.hashCode(), key, topic));
                    break;

                case 2:
                    try {
                        oos.writeObject("Subscribe");
                        System.out.print("Search: ");
                        oos.writeObject(scan.next());
                        System.out.println("Enter 1 to subscribe, 0 to cancel");
                        oos.writeObject(scan.nextInt());
                    }
                    catch(IOException e){
                        System.out.println("IO Error");
                        continue;
                    }

            }
        }
    }
    private void connectToNewPort(String host){
        try{
            socket = new Socket(host, newPort);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            oos.writeObject("Client Connected to " + newPort);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void publish(Event event){

    }

    public void advertise(Topic topic){
        try {
            oos.writeObject(topic);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        while(true){
            try {
                System.out.println((String) ois.readObject());
            }
            catch (IOException e){
                System.out.println("IO Error");
            }
            catch (ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }*/
}


