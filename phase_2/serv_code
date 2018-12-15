
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class serv_code {
    //Keeps set of subscriber topic wise, using set to prevent duplicates
    Map<String, Set<subs>> subscribersTopicMap = new HashMap<String, Set<subs>>();

    //Holds messages published by publishers
    Queue<info> messagesQueue = new LinkedList<info>();

    //Adds message sent by publisher to queue
    public void addMessageToQueue(info message){
        messagesQueue.add(message);
    }

    //Add a new Subscriber for a topic
    public void addSubscriber(String topic, subs subscriber){

        if(subscribersTopicMap.containsKey(topic)){
            Set<subs> subscribers = subscribersTopicMap.get(topic);
            subscribers.add(subscriber);
            subscribersTopicMap.put(topic, subscribers);
        }else{
            Set<subs> subscribers = new HashSet<subs>();
            subscribers.add(subscriber);
            subscribersTopicMap.put(topic, subscribers);
        }
    }

    //Remove an existing subscriber for a topic
    public void removeSubscriber(String topic, subs subscriber){

        if(subscribersTopicMap.containsKey(topic)){
            Set<subs> subscribers = subscribersTopicMap.get(topic);
            subscribers.remove(subscriber);
            subscribersTopicMap.put(topic, subscribers);
        }
    }

    //Broadcast new messages added in queue to All subscribers of the topic. messagesQueue will be empty after broadcasting
    public void broadcast(){
        if(messagesQueue.isEmpty()){
            System.out.println("No messages from publishers to display");
        }else{
            while(!messagesQueue.isEmpty()){
                info message = messagesQueue.remove();
                String topic = message.getTopic();

                Set<subs> subscribersOfTopic = subscribersTopicMap.get(topic);

                for(subs subscriber : subscribersOfTopic){
                    //add broadcasted message to subscribers message queue
                    List<info> subscriberMessages = subscriber.getSubscriberMessages();
                    subscriberMessages.add(message);
                    subscriber.setSubscriberMessages(subscriberMessages);
                }
            }
        }
    }

    //Sends messages about a topic for subscriber at any point
    public void getMessagesForSubscriberOfTopic(String topic, subs subscriber) {
        if(messagesQueue.isEmpty()){
            System.out.println("No messages from publishers to display");
        }else{
            while(!messagesQueue.isEmpty()){
                info message = messagesQueue.remove();

                if(message.getTopic().equalsIgnoreCase(topic)){

                    Set<subs> subscribersOfTopic = subscribersTopicMap.get(topic);

                    for(subs _subscriber : subscribersOfTopic){
                        if(_subscriber.equals(subscriber)){
                            //add broadcasted message to subscriber message queue
                            List<info> subscriberMessages = subscriber.getSubscriberMessages();
                            subscriberMessages.add(message);
                            subscriber.setSubscriberMessages(subscriberMessages);
                        }
                    }
                }
            }
        }
    }
}
