import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DriveMain {
    public static void main(String[] args) throws IOException {

        //Instantiate publishers, subscribers and PubSubService
        PubCode dogPublisher = new PubCode();
        PubCode catPublisher = new PubCode();
        PubCode snakePublisher = new PubCode();
        PubCode tigerPublisher = new PubCode();
        PubCode fishPublisher = new PubCode();

        subs dogSubscriber = new subs();
        subs allAnimalSubscriber = new subs();
        subs catSubscriber = new subs();
        subs snakeSubscriber = new subs();
        subs tigerSubscriber = new subs();
        subs fishSubscriber = new subs();


        File file = new File("abc.txt");

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String st[];
        String temp;
        List<String[]> msg = new ArrayList<>();
        while (( temp = br.readLine()) != null) {
            st=temp.split(":");
            msg.add(st);
           // System.out.println(st[0]);
        }

        serv_code pubSubService = new serv_code();
        for(int i=0;i<msg.size();i++){
            String s1= msg.get(i)[0];
            String s2 = msg.get(i)[1];
            if(s1.equals("Dog")||s1.equals("dog")){
                info dogMsg = new info("Dog", s2);
                dogPublisher.publish(dogMsg, pubSubService);
            }
            else if(s1.equals("Cat")||s1.equals("cat")){
                info catMsg = new info("Cat", s2);
                catPublisher.publish(catMsg, pubSubService);
            }
            else if(s1.equals("Snake")||s1.equals("snake")){
                info snakeMsg = new info("Snake", s2);
                snakePublisher.publish(snakeMsg, pubSubService);
            }
            else if(s1.equals("Tiger")||s1.equals("tiger")){
                info tigerMsg = new info("Tiger", s2);
                tigerPublisher.publish(tigerMsg, pubSubService);
            }
            else if(s1.equals("fish")||s1.equals("Fish")){
                info fishMsg = new info("Fish", s2);
                fishPublisher.publish(fishMsg, pubSubService);
            }

        }


        //Declare Subscribers
        dogSubscriber.addSubscriber("Dog",pubSubService);
        catSubscriber.addSubscriber("Cat",pubSubService);
        tigerSubscriber.addSubscriber("Tiger", pubSubService);
        fishSubscriber.addSubscriber("Fish",pubSubService);
        snakeSubscriber.addSubscriber("Snake",pubSubService);


        //Broadcast message to all subscribers. After broadcast, messageQueue will be empty in PubSubService
        pubSubService.broadcast();

        //Print messages of each subscriber to see which messages they got
        System.out.println("Messages_of_Dog_Subscriber_are:");
        dogSubscriber.printMessages();

        System.out.println("Messages_of_Cat_Subscriber_are:");
        catSubscriber.printMessages();

        System.out.println("Messages_of_Tiger_Subscriber_are:");
        tigerSubscriber.printMessages();

        System.out.println("Messages_of_Snake_Subscriber_are:");
        snakeSubscriber.printMessages();

        System.out.println("Messages_of_Fish_Subscriber_are:");
        fishSubscriber.printMessages();
        //After broadcast the messagesQueue will be empty, so publishing new messages to server

        info dogMsg2 = new info("Dog", "retriever");
        dogPublisher.publish(dogMsg2, pubSubService);

        info catMsg2 = new info("Cat", "Balienese");
        catPublisher.publish(catMsg2, pubSubService);
        pubSubService.broadcast();
        System.out.println("Messages_of_Dog_Subscriber_are:");
        dogSubscriber.printMessages();

        System.out.println("Messages_of_Cat_Subscriber_are:");
        catSubscriber.printMessages();

    }
}
