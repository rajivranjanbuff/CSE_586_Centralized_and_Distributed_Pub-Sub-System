import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConnectionManager {
    /*private static BufferedReader br;
    private static PrintWriter pw;
    private static ServerSocket serverSocket, ss;
    private static Socket clientSocket,newSocket;
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static List<Integer> ports = Collections.synchronizedList(new ArrayList<Integer>());
    private static EventManager evntmngr;
    private Thread thrd;
    public static void main(String[] args) {
        for(int i = 0; i < 10; i++){
            ports.add(8000+i);
        }
        try{
            serverSocket = new ServerSocket(5000);
            evntmngr = new EventManager();
            ss = new ServerSocket(ports.get(0));
            while(true){
                clientSocket = serverSocket.accept();
                br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                pw = new PrintWriter(clientSocket.getOutputStream(),true);
                System.out.println(br.readLine());
                pw.println(ports.get(0));
                newSocket = ss.accept();
                oos = new ObjectOutputStream(newSocket.getOutputStream());
                ois = new ObjectInputStream(newSocket.getInputStream());
                new ConnManagerThread(newSocket, evntmngr, false, oos, ois);
                new ConnManagerThread(newSocket, evntmngr, true, oos, ois);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }*/

}
