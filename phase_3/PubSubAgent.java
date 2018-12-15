
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class PubSubAgent implements Runnable {

    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public Socket socket;
    public int port;
    public Thread thrd;
    public List<Topic> topics;
    public String host;
    public PubSubAgent(int p, String h){
        try {
            host = h;
            thrd = new Thread(this);
            socket = new Socket(host, p);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            port = (int)ois.readObject();
            ois.close();
            oos.close();
            socket.close();
            topics = Collections.synchronizedList(new ArrayList<Topic>());
            thrd.start();
        }
        catch(IOException e){
            //e.printStackTrace();
            System.out.println("Could not connect");
            System.exit(1);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
    	
    		if(args.length < 1) {
    			System.out.println("Please provide host in command line argument");
    			System.exit(0);
    		}
        PubSubAgent agent = new PubSubAgent(5000, args[0]);

        while (true) {
            System.out.println("Enter 1 to Advertise \nEnter 2 to Subscribe \nEnter 3 to Publish\nEnter 4 to Unsubscribe");
            Scanner scan = new Scanner(System.in);
            try {
                switch (scan.nextInt()) {
                    case 1:
                        System.out.println("Enter Topic");
                        String topic = scan.next();
                        List<String> key = new ArrayList<String>();
                        key.add(topic);
                        while (!scan.hasNext("0")) {
                            key.add(scan.nextLine());
                        }
                        agent.advertise(new Topic(topic.hashCode(), key, topic));
                        break;
                    case 2:
                        System.out.println("Enter Topic");
                        String subTopic = scan.next();
                        agent.subscribe(subTopic);
                        break;
                    case 3:
                        int i = 0;
                        List<Topic> temp;
                        synchronized (agent.topics) {
                            temp = agent.topics;
                        }
                        System.out.println("Enter Topic");
                        String t = scan.next();
                        Topic tempTop = null;
                        while (i < temp.size()) {
                            if (temp.get(i).keywords.contains(t)) {
                                tempTop = temp.get(i);
                                System.out.println("Topic found");
                                break;
                            }

                            i++;
                        }
                        System.out.println("Enter Title");
                        String title = scan.next();
                        System.out.println("Enter Content");
                        String content = scan.next();
                        int id = (title + content).hashCode();
                        Event event = new Event(id, tempTop, title, content);
                        agent.publish(event);
                        break;
                    case 4:
                        System.out.println("Enter Topic");
                        String s = scan.next();
                        List<Topic> tmp;
                        Topic tmpTop = null;
                        int j =0;
                        synchronized (agent.topics) {
                            tmp = agent.topics;
                        }
                        while (j < tmp.size()) {
                            if (tmp.get(j).keywords.contains(s)) {
                                tmpTop = tmp.get(j);
                                System.out.println("Topic found");
                                break;
                            }
                            j++;
                        }
                        agent.unsubscribe(tmpTop);
                        break;
                }
            }catch(InputMismatchException e){
                System.out.println("Wrong Input");
            }
        }
    }

    public void connect(){
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println((String)ois.readObject());
        }
        catch(IOException e){
            System.out.println("Connection error");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void advertise(Topic newTopic){
        connect();
        try {
            oos.writeObject("Topic");
            oos.writeObject(newTopic);
            closeConnection();
        } catch (IOException e) {
            System.out.println("Connection Error");
            System.exit(1);
        }
    }

    public void run(){
        try {
            ServerSocket ss = new ServerSocket(7000);
            List<Event> events = new ArrayList<Event>();
            while(true) {
                Socket s = ss.accept();
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                try {
                    synchronized (topics) {
                        out.writeObject(topics.size());
                        int i = (int) in.readObject();
                        while (i > 0) {
                            Topic temp = (Topic) in.readObject();
                            topics.add(temp);
                            System.out.println("New Topic: " + temp.name);
                            i--;
                        }
                    }
                    out.writeObject(events.size());
                    int i = (int) in.readObject();
                    while (i > 0) {
                        Event temp = (Event) in.readObject();
                        events.add(temp);
                        System.out.println("Published: " + temp.topic.name + " " + temp.title);
                        i--;
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: Incompatible type object");
                }
            }
        }
        catch(IOException e){
            System.out.println("Connection Error");
            //e.printStackTrace();
        }
    }

    public void subscribe(String keyword){
        connect();
        try{
            oos.writeObject("Subscribe");
            oos.writeObject(keyword);
            System.out.println((String)ois.readObject());
            closeConnection();
        }catch (IOException e) {
            System.out.println("Connection Error");
            System.exit(1);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            oos.close();
            ois.close();
            socket.close();
        }
        catch(IOException e){
            System.out.println("Connection Error");
            System.exit(1);
        }
    }

    public void publish(Event e){
        if(e.topic == null){
            System.out.println("Topic not found");
            return;
        }
        connect();
        try{
            oos.writeObject("Publish");
            oos.writeObject(e);
            System.out.println((String)ois.readObject());
            closeConnection();
        }catch (IOException io){
            System.out.println("Disconnected");
        }
        catch (ClassNotFoundException cl){
            System.out.println("Incompatible type object");
        }
    }

    public void unsubscribe(Topic t){
        if(t == null){
            System.out.println("Topic not found");
            return ;
        }
        connect();
        try {
            oos.writeObject("Unsubscribe");
            oos.writeObject(t.id);
            System.out.println((String)ois.readObject());
        }
        catch(IOException e){
            System.out.println("Disconnected");
        }
        catch(ClassNotFoundException e){
            System.out.println("Incompatible type object");
        }
    }

}
