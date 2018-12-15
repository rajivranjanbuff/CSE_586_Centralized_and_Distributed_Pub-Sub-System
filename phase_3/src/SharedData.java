import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * This class acts as the data model for the server. This class has the list of events posted by all the clients and 
 * the list of clients and the topics.
 */
public class SharedData {
    public static List<InetAddress> ips;
    public static List<Topic> topics;
    public static Map<Integer,List<InetAddress>> subscriberMap;
    public static List<Event> event;
    public SharedData(){
        event = new ArrayList<Event>();
        ips = new ArrayList<InetAddress>();
        topics = new ArrayList<Topic>();
        subscriberMap = new HashMap<Integer, List<InetAddress>>();
    }
    public void addTopic(Topic t){
        topics.add(t);
    }
    public void addIP(InetAddress i){
        ips.add(i);
    }
    public List<Topic> getTopicList(){
        return topics;
    }
    public List<InetAddress> getIPList(){
        return ips;
    }
    public List<Event> getEventList(){
        return event;
    }
    public Map<Integer,List<InetAddress>> getMap(){
        return subscriberMap;
    }
    //Adds the topic based on the keyword for the particular client
    public String addSub(String key, InetAddress ina){
        int i = 0;
        Topic temp = null;
        List<InetAddress> tempList;
        while(i < topics.size()){
            if(topics.get(i).keywords.contains(key)){
                temp = topics.get(i);
                break;
            }
            i++;
        }
        if(temp != null && subscriberMap.containsKey(temp.id)){
            tempList = subscriberMap.get(temp.id);
            tempList.add(ina);
        }
        else if(temp != null && !subscriberMap.containsKey(temp.id)){
            List <InetAddress> newList = new ArrayList<InetAddress>();
            newList.add(ina);
            subscriberMap.put(temp.id,newList);
        }
        else{
            return "Not Found";
        }
        return "Subscribed";
    }
    public String addEvent(Event e){
        event.add(e);
        return "Published";
    }
    //Takes the ip address of the client as an argument and deletes the subscribed topic(unsubscribes) for the particular client
    public String removeSub(int i, InetAddress ina){
        if(subscriberMap.containsKey(i) && subscriberMap.get(i).contains(ina)){
            subscriberMap.get(i).remove(ina);
            return "Unsubscribed";
        }
        return "Not Found";
    }
}
