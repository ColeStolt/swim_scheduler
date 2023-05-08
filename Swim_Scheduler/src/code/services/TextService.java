package code.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;


public class TextService implements java.io.Serializable{

	  /**
	 * 
	 */
	private static final long serialVersionUID = -1923331596655919604L;
	public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
	  public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");
	
	public static void sendText(String toNumber, String messageText) {
		  // Find your Account Sid and Token at twilio.com/console
		    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		    Message message = Message.creator(
		      new com.twilio.type.PhoneNumber("+1" + toNumber),
		      new com.twilio.type.PhoneNumber("+19258077153"),
		      messageText)
		    .create();

		    System.out.println(message.getSid());  
	}
	
}
