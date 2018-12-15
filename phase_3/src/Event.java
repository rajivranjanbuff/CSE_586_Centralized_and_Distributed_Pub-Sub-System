import java.io.Serializable;
/*
 * Each object of this class is an event for a topic. This has properties such as id, title, content and under which topic 
 * this event belongs to. 
 */
public class Event implements Serializable {
	public int id;
	public Topic topic;
	public String title;
	public String content;

	public Event(int i, Topic t, String ti, String con){
		id = i;
		topic = t;
		title = ti;
		content = con;
	}
}
