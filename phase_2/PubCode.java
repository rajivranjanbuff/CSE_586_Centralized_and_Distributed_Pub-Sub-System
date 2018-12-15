public class PubCode {
    public void publish(info message, serv_code pubSubService) {
        pubSubService.addMessageToQueue(message);

    }
}
