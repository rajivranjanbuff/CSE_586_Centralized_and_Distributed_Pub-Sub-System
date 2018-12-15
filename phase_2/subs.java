import java.util.ArrayList;
import java.util.List;

public  class subs {
    private List<info> subscriberMessages = new ArrayList<info>();

    public List<info> getSubscriberMessages() {
        return subscriberMessages;
    }

    public void setSubscriberMessages(List<info> subscriberMessages) {
        this.subscriberMessages = subscriberMessages;
    }

    //Add subscriber with PubSubService for a topic
    public void addSubscriber(String topic, serv_code pubSubService) {
        pubSubService.addSubscriber(topic, this);
    }

    //Unsubscribe subscriber with PubSubService for a topic
    public void unSubscribe(String topic, serv_code pubSubService) {
        pubSubService.removeSubscriber(topic, this);}

        //Request specifically for messages related to topic from PubSubService
        public void getMessagesForSubscriberOfTopic (String topic, serv_code pubSubService){
            pubSubService.getMessagesForSubscriberOfTopic(topic, this);

        }
        //Print all messages received by the subscriber
        public void printMessages () {
            for (info message : subscriberMessages) {
                System.out.println("Message_Topic_->" + message.getTopic() + ":" + message.getPayload());
            }
        }
    }

