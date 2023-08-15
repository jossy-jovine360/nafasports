/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

import java.util.Date;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author OMEN
 */
public class SendMail {

    public static String sendEmailHTML() {
        String status = "no";
        try {
            String from = EmailData.getSender();
            String to = EmailData.getReceiver();
            String subject = EmailData.getSubject();
            String body = EmailData.getMsg();    
            
            String host = EmailData.getHost();
//            String host = "mail.zincoinvestment.com";
            String userid = EmailData.getUsername();
            String password = EmailData.getPassword();
            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtps");
            prop.put("mail.smtp.user", userid);
            prop.put("mail.smtp.password", password);
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.debug", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.fallback", "false");
            
            Session session = Session.getDefaultInstance(prop, null);
            MimeMessage message = new MimeMessage(session);
            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress(to);
            } catch (AddressException e) {
                e.printStackTrace();
//                System.out.println(e.getMessage());
            }
            message.setFrom((Address) fromAddress);
            message.setRecipient(Message.RecipientType.TO, (Address) toAddress);
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtps");
            transport.connect(host, userid, password);
            transport.sendMessage((Message) message, message.getAllRecipients());
            status = "yes";
        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
        return status;
    }
    public static String sendEmailHTML(String sender, String receiver, String sub, String msg) {
        String status = "no";
        try {
            String from = sender;
            String to = receiver;
            String subject = sub;
            String body = msg;
            String host = "zincoinvestment.com";
//            String host = "mail.zincoinvestment.com";
            String userid = EmailData.getUsername();
            String password = EmailData.getPassword();
            Properties prop = System.getProperties();
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtps");
            prop.put("mail.smtp.user", userid);
            prop.put("mail.smtp.password", password);
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.debug", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop.put("mail.smtp.socketFactory.fallback", "false");
            
            Session session = Session.getDefaultInstance(prop, null);
            MimeMessage message = new MimeMessage(session);
            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress(to);
            } catch (AddressException e) {
                e.printStackTrace();
//                System.out.println(e.getMessage());
            }
            message.setFrom((Address) fromAddress);
            message.setRecipient(Message.RecipientType.TO, (Address) toAddress);
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtps");
            transport.connect(host, userid, password);
            transport.sendMessage((Message) message, message.getAllRecipients());
            status = "yes";
        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
        return status;
    }
    public static String sendUnsecuredHTML(String sender, String receiver, String sub, String msg) {
        String status = "no";
        try {
            String from = sender;
            String to = receiver;
            String subject = sub;
            String body = msg;
            String host = "zincoinvestment.com";
//            String host = "mail.zincoinvestment.com";
            String userid = EmailData.getUsername();
            String password = EmailData.getPassword();
            Properties prop = System.getProperties();
//            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.starttls.enable", "false");
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtps");
            prop.put("mail.smtp.user", userid);
            prop.put("mail.smtp.password", password);
            prop.put("mail.smtp.port", "26");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.debug", "true");
//            prop.put("mail.smtp.socketFactory.port", "465");
//            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            prop.put("mail.smtp.socketFactory.fallback", "false");
            
            Session session = Session.getDefaultInstance(prop, null);
            MimeMessage message = new MimeMessage(session);
            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress(to);
            } catch (AddressException e) {
                e.printStackTrace();
//                System.out.println(e.getMessage());
            }
            message.setFrom((Address) fromAddress);
            message.setRecipient(Message.RecipientType.TO, (Address) toAddress);
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtps");
            transport.connect(host, userid, password);
            transport.sendMessage((Message) message, message.getAllRecipients());
            status = "yes";
        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
        return status;
    }

    public static String sendMultipleEmailHTML(String sender, String[] receiver, String sub, String msg) {
        String status = "no";
        try {
            String from = sender;
            String[] to = receiver;
            String subject = sub;
            String body = msg;
            String host = "zincoinvestment.com";
            String userid = EmailData.getUsername();
            String password = EmailData.getPassword();
            Properties prop = System.getProperties();
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtps");
            prop.put("mail.smtp.user", userid);
            prop.put("mail.smtp.password", password);
//            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.port", "26");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.debug", "true");
            
            Session session = Session.getDefaultInstance(prop, null);
            MimeMessage message = new MimeMessage(session);
            InternetAddress fromAddress = null;
            InternetAddress[] toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress[receiver.length];
                for (int i = 0; i < to.length; i++) {
                    toAddress[i] = new InternetAddress(to[i]);
                }
            } catch (AddressException e) {
                e.printStackTrace();
//                System.out.println(e.getMessage());
            }
            message.setFrom((Address) fromAddress);
            message.setRecipients(Message.RecipientType.BCC, (Address[]) toAddress);
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            message.setSentDate(new Date());
            Transport transport = session.getTransport("smtps");
            transport.connect(host, userid, password);
            transport.sendMessage((Message) message, message.getAllRecipients());
            status = "yes";
        } catch (MessagingException e) {
            e.printStackTrace();
//            System.out.println(e.getMessage());
        }
        return status;
    }
    public static int sendEmailUsingGmail(){
        
        try{
//        String from = sender;
//        String to = receiver;
//        String subject = sub;
//        String body = msg;
        
            String from = EmailData.getGmail_sender();
            String to = EmailData.getReceiver();
            String subject = EmailData.getSubject();
            String body = EmailData.getMsg();
            
            String host = "smtp.gmail.com";
            String userid = EmailData.getGmail_email(); 
            String password = EmailData.getGmail_pwd();

    //            System.out.println("Username: "+userid);

            Properties prop = System.getProperties();
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.setProperty("mail.transport.protocol", "smtps");
            prop.put("mail.smtp.user", userid);
            prop.put("mail.smtp.password", password);
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.debug", "true");

            Session session = Session.getDefaultInstance(prop, null); 
            MimeMessage message = new MimeMessage(session);
            InternetAddress fromAddress = null;
            InternetAddress toAddress = null;
            try {
                fromAddress = new InternetAddress(from);
                toAddress = new InternetAddress(to);
            } catch (AddressException e) {
                e.printStackTrace();
            }

            message.setFrom(fromAddress);
            message.setRecipient(Message.RecipientType.TO, toAddress);
            message.setSubject(subject);
    //            message.setContent(body, "text/html");
            message.setText(body, "utf-8", "html");
            message.setSentDate(new Date());


            //SMTPSSLTransport transport =(SMTPSSLTransport)session.getTransport("smtps");

            Transport transport = session.getTransport("smtps"); 
            transport.connect(host, userid, password);
            transport.sendMessage(message, message.getAllRecipients()); 
            transport.close(); 

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int sendEmailUsingGmail(String sender, String receiver, String sub, String msg){
        
        try{
        String from = sender;
        String to = receiver;
        String subject = sub;
        String body = msg;
        
        String host = "smtp.gmail.com";
        String userid = EmailData.getGmail_email(); 
        String password = EmailData.getGmail_pwd();
        
//            System.out.println("Username: "+userid);
        
        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.setProperty("mail.transport.protocol", "smtps");
        prop.put("mail.smtp.user", userid);
        prop.put("mail.smtp.password", password);
        prop.put("mail.smtp.port", "465");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.debug", "true");
        
        Session session = Session.getDefaultInstance(prop, null); 
        MimeMessage message = new MimeMessage(session);
        InternetAddress fromAddress = null;
        InternetAddress toAddress = null;
        try {
            fromAddress = new InternetAddress(from);
            toAddress = new InternetAddress(to);
        } catch (AddressException e) {
            e.printStackTrace();
        }

        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);
        message.setSubject(subject);
//            message.setContent(body, "text/html");
        message.setText(body, "utf-8", "html");
        message.setSentDate(new Date());

        //SMTPSSLTransport transport =(SMTPSSLTransport)session.getTransport("smtps");

        Transport transport = session.getTransport("smtps"); 
        transport.connect(host, userid, password);
        transport.sendMessage(message, message.getAllRecipients()); 
        transport.close(); 


        } catch (MessagingException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
