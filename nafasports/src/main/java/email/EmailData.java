/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package email;

/**
 *
 * @author OMEN
 */
public class EmailData {
//    public static final String username = "noreply@jovine360solutions.com";
//    public static final String password = "jovine44real.com!!#";
    public static final String username = "noreply@nafafootball.com";
    public static final String password = "jovineWs44real.com!!#";
    public static String sender = "NAFA<"+username+">";
    public static String receiver = "";
    public static String subject = "";
    public static String msg = "";
    public static final String host = "mail.nafafootball.com";
    
    public static final String gmail_email = "noreply.jovinesolutions@gmail.com";
    public static final String gmail_pwd = "admin4real";
    public static String gmail_sender = "Jovine360 Mall<"+gmail_email+">";
    
//    ====================== Email Data ===============================
    public static void setEmailAttributesWithoutMSG(String receipient, String subject){        
        
        EmailData.receiver = receipient;
        EmailData.subject = subject;
        
        EmailData.setSender(sender);
        EmailData.setReceiver(receiver);
        
        EmailData.setSubject(subject);
    }
    public static void setGmailEmailAttributes(String receipient, String sub, String msg){        
        
        EmailData.receiver = receipient;
        EmailData.subject = sub;
        EmailData.msg = msg;
        
        EmailData.setSender(gmail_sender);
        EmailData.setReceiver(receiver);
        EmailData.setSubject(subject);
        EmailData.setMsg(msg);
    }
    public static void setEmailAttributes(String receipient, String sub, String msgs){        
       
        EmailData.receiver = receipient;
        EmailData.subject = sub;
        EmailData.msg = msgs;
        
        EmailData.setSender(sender);
        EmailData.setReceiver(receiver);
        EmailData.setSubject(subject);
        EmailData.setMsg(msg); 
    }
    public static void setEmailAttributes(String senders, String receipient, String sub, String msgs){        
        EmailData.sender = senders;
        EmailData.receiver = receipient;
        EmailData.subject = sub;
        EmailData.msg = msgs;
        
        EmailData.setSender(sender);
        EmailData.setReceiver(receiver);
        EmailData.setSubject(subject);
        EmailData.setMsg(msg); 
    }
//    ====================== Email Data ===============================    

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    
    public static String getReceiver() {
        return receiver;
    }

    public static void setReceiver(String receiver) {
        EmailData.receiver = receiver;
    }

    public static String getSender() {
        return sender;
    }

    public static void setSender(String sender) {
        EmailData.sender = sender;
    }

    public static String getSubject() {
        return subject;
    }

    public static void setSubject(String subject) {
        EmailData.subject = subject;
    }   

    public static String getMsg() {
        return msg;
    }

    public static void setMsg(String msg) {
        EmailData.msg = msg;
    }

    public static String getGmail_sender() {
        return gmail_sender;
    }

    public static void setGmail_sender(String gmail_sender) {
        EmailData.gmail_sender = gmail_sender;
    }

    public static String getGmail_email() {
        return gmail_email;
    }

    public static String getGmail_pwd() {
        return gmail_pwd;
    }

    public static String getHost() {
        return host;
    }

//    public static void setHost(String host) {
//        EmailData.host = host;
//    }
    
}
