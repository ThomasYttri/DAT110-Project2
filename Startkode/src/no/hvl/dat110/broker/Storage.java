package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	// Task E
	// data structure for managing messages for disconnected clients
	// maps from user to corresponding set of messages
	protected ConcurrentHashMap<String, Set<Message>> buffer;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();

		// TASK E
		buffer = new ConcurrentHashMap<String, Set<Message>>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public Set<Message> getMessages (String user) {
		return (buffer.get(user));
	}

	// TASK E
	// Add messages to buffer
	public void addBufferMessage(String user, Message msg){
		if (!buffer.containsKey(user)) {
			Set<Message> msgset = new HashSet<Message>();
			msgset.add(msg);
			buffer.put(user, msgset);
		} else {
			buffer.get(user).add(msg);
		}
	}

	public void addClientSession(String user, Connection connection) {

		//add ClientSession
		clients.put(user, new ClientSession(user, connection));
	}

	public void removeClientSession(String user) {

		//remove client session for user from the storage
		clients.remove(user);
	}

	public void createTopic(String topic) {

		//create topic in the storage
		Set<String> set = new HashSet<String>();
		subscriptions.put(topic, set);
	}

	public void deleteTopic(String topic) {

		//delete topic from the storage
		subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {

		//add the user as subscriber to the topic
		Set<String> subset = getSubscribers(topic);
		subset.add(user);

		subscriptions.put(topic, subset);
    }

	public void removeSubscriber(String user, String topic) {

		//remove the user as subscriber to the topic
		Set<String> subset = getSubscribers(topic);
		subset.remove(user);

		subscriptions.put(topic, subset);
	}
}
