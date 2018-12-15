import java.io.Serializable;
import java.util.List;
/*
 * Each object is a topic under which there will be many events. A topic has properties such as id, name and a list of keywords.
 */
public class Topic implements Serializable{
	public int id;
	public List<String> keywords;
	public String name;
	public Topic(int i, List<String> l, String n){
		id = i;
		keywords = l;
		name = n;
	}
}