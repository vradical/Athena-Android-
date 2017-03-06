package com.vh.athena;

import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.*; 

import com.twilio.sdk.*; 
import com.twilio.sdk.resource.factory.*; 
 
public class EmergencySendService {
 
    private Logger logger = Logger.getLogger(EmergencySendService.class.getName());
	private static final String ACCOUNT_SID = ""; 
	private static final String AUTH_TOKEN = ""; 
 
    public boolean sendEmail(String email, String name, String country, String address, String latitude, String longitude) {
 
        final String GMAIL_HOST = "smtp.gmail.com";
        final String TLS_PORT = "897";
        final String SENDER_EMAIL = "project.athena@gmail.com";
        final String SENDER_USERNAME = "";
        final String SENDER_PASSWORD = "";
 
        // protocol properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtps.host", GMAIL_HOST); //                                       
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtps.auth", "true");
        // close connection upon quit being sent
        props.put("mail.smtps.quitwait", "false");
 
        Session session = Session.getInstance(props, null);
 
        try {
            // create the message
            final MimeMessage msg = new MimeMessage(session);
 
            // set recipients and content
            msg.setFrom(new InternetAddress(SENDER_EMAIL));
            
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
            msg.setSubject(name + " has send you an emergency request.");
            msg.setText("This is an emergency." + name + " may be in danger. Please reach out to him/her immediately at "
                        + country + " " + address +" ("+latitude +", "+longitude+"). Please do not reply to this email.", "utf-8", "html");
            msg.setSentDate(new Date());
 
            // this means you do not need socketFactory properties
            Transport transport = session.getTransport("smtps");
 
            // send the mail
            transport.connect(GMAIL_HOST, SENDER_USERNAME, SENDER_PASSWORD);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
 
            return true;
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, "Failed to send message", e);
            return false;
 
        }
    }
    
    public boolean sendSMS(String name, String country, String address, String  contactPhone, String latitude, String longitude) {
        try {
    		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
       	 
    	 String sendMessage = name + "This is an emergecny." + name + "may be in danger. Please reach out to him/her immediately at " 
    	 + country + address +"("+latitude+","+longitude+"). Please do not reply to this SMS.";
    		
   		 List<NameValuePair> params = new ArrayList<NameValuePair>(); 
   		 params.add(new BasicNameValuePair("To", contactPhone)); 
   		 params.add(new BasicNameValuePair("From", "+12089356997")); 
   		 params.add(new BasicNameValuePair("Body", sendMessage));   
   	 
   		 MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
   		 com.twilio.sdk.resource.instance.Message message = messageFactory.create(params); 
   		 System.out.println(message.getSid()); 
            return true;
        } catch (TwilioRestException e) {
            logger.log(Level.SEVERE, "Failed to send message", e);
            return false;
        }
    }
    
}
