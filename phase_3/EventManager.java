import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Event Manager class listens to requests at 5000 ports This class also sends
 * notifications
 */
public class EventManager implements Runnable {
	// Stores the sockets
	private static Map<Integer, ServerSocket> socketLoad;
	// Synchronized data object
	private static SharedData data;
	private static Thread thrd;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	// Array of threads
	private static Runnable[] thrdPool;
	// Thread pool
	private static ExecutorService pool;

	/*
	 * Creates thread pools and sockets Create new thread for sending data
	 */
	public EventManager() {
		pool = Executors.newFixedThreadPool(5);
		thrdPool = new Runnable[5];
		data = new SharedData();
		socketLoad = new HashMap<Integer, ServerSocket>();
		thrd = new Thread(this);
		for (int i = 0; i < 5; i++) {
			try {
				socketLoad.put(i, new ServerSocket(8000 + i));
				thrdPool[i] = new EventManagerThread(socketLoad.get(i), this);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		thrd.start();
	}

	/*
	 * Start the service on port 5000 Provide a port for the client Start a thread
	 */
	public void startService(int port) {
		try {
			ServerSocket ss = new ServerSocket(port);
			while (true) {
				System.out.println("Waiting for request");
				Socket cs = ss.accept();
				synchronized (data) {
					data.addIP(cs.getInetAddress());
				}
				System.out.println(cs.getLocalAddress() + " requested");
				oos = new ObjectOutputStream(cs.getOutputStream());
				ois = new ObjectInputStream(cs.getInputStream());
				Random rand = new Random();
				int i = rand.nextInt(5);
				ServerSocket newSS = socketLoad.get(i);
				oos.writeObject(newSS.getLocalPort());
				System.out.println("Connecting with " + cs.getRemoteSocketAddress() + " at " + newSS.getLocalPort());
				// new EventManagerThread(newSS, this);
				pool.execute(thrdPool[i]);
			}
		} catch (IOException e) {
			System.out.println("Connection Error");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		EventManager evnt = new EventManager();
		evnt.startService(5000);
	}

	/*
	 * Add topic in database
	 */
	public void addTopic(Topic topic) {
		synchronized (data) {
			data.addTopic(topic);
		}
	}

	public ServerSocket getSocket() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return socketLoad.get(i);
	}

	public Runnable getThread() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		return thrdPool[i];
	}

	/*
	 * Add subscriber to database
	 */
	public String addSubscriber(String key, InetAddress ina) {
		synchronized (data) {
			return data.addSub(key, ina);
		}
	}

	/*
	 * Add event to database
	 */
	public String addEvent(Event e) {
		synchronized (data) {
			return data.addEvent(e);
		}
	}

	/*
	 * Unsubscribe client
	 */
	public String unsubscribe(int i, InetAddress ina) {
		synchronized (data) {
			return data.removeSub(i, ina);
		}
	}

	/*
	 * Talk to all clients at port 7000
	 */
	public void run() {
		int noOfTopics = 0;
		int noOfEvents = 0;
		while (true) {
			List<InetAddress> ip;
			List<Topic> t;
			List<Event> ev;
			Map<Integer, List<InetAddress>> mp;
			synchronized (data) {
				ip = data.getIPList();
				t = data.getTopicList();
				ev = data.getEventList();
				mp = data.getMap();
			}
			// If condition to check whether a new topic is advertised or a new event is published. Only when a new topic or event is published
			// the Event Manager will try to connect to the client (Publishers/Subscribers) to deliver the notification
			if (t.size() > noOfTopics || ev.size() > noOfEvents) {
				int i = 0;
				synchronized (data) {
					noOfEvents = ev.size();
					noOfTopics = t.size();
				}
				while (i < ip.size()) {
					try {
						Socket skt = new Socket(ip.get(i++).getHostAddress(), 7000);
						ObjectOutputStream out = new ObjectOutputStream(skt.getOutputStream());
						ObjectInputStream in = new ObjectInputStream(skt.getInputStream());
						int j = (int) in.readObject();
						out.writeObject(t.size() - j);
						while (j < t.size()) {
							out.writeObject(t.get(j++));
						}
						int k = (int) in.readObject();
						int c = 0, d = k;
						while (k < ev.size()) {
							if (mp.containsKey(ev.get(k).topic.id)
									&& mp.get(ev.get(k++).topic.id).contains(ip.get(i - 1))) {
								c++;
							}
						}
						out.writeObject(c);
						while (d < ev.size()) {

							if (mp.get(ev.get(d).topic.id).contains(ip.get(i - 1))) {
								out.writeObject(ev.get(d));
							}
							d++;
						}
						out.close();
						skt.close();
					} catch (IOException e) {
						System.out.println("Server unreachable at " + ip.get(i - 1).getHostAddress());

					} catch (ClassNotFoundException e) {
						System.out.println("Incompatible data");
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
