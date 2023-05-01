package code.datapersistance_dao;

import code.dataObjects.Client;

/**
 * 
 * @author Cole Stoltz
 * This class allows the client card to pass a reference to the client
 * that it holds to the data entry controller.
 *
 */

public class ClientCardReferenceSingleton {
	
	private static final ClientCardReferenceSingleton instance = new ClientCardReferenceSingleton();
	
	private Client clientReference = null;
	
	private ClientCardReferenceSingleton() {
		
	}
	
	public static ClientCardReferenceSingleton getInstance() {
		return instance;
	}
	
	public void setClientReference(Client clientReference) {
		this.clientReference = clientReference;
	}
	
	public Client getClient() {
		return this.clientReference;
	}
	
}
