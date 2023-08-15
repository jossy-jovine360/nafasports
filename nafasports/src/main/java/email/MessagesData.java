/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package email;

import appData.AppData;

/**
 *
 * @author josiah
 */
public class MessagesData {
    
    public static String getNewAdminNotification(String name, String email, String pass){
        String msg = "<h2>Dear, "+name+", </h2>"
            + "<p>"
            + "This is to notify you that you have been registered successfully as an admin user on NAFA. This provides you with the capacity to manage "
                + "players, lineups, view game fixtures and so on."
            + "</p>"
                + "<p'>"
                + "Your login details are as follows:<br>"
                + "<div style='display: block'>Email: "+email+"</div>"
                + "<div style='display: block'>Password: "+pass+"</div>"
                + "<div style='display: block'>Click here: "+AppData.DOMAIN_ADMIN+"</div"
                + "</p>"
                + "<br>"
            + "<p style='margin-bottom: 100px; font-weight: bold'>"
            + "NAFA Administrator Team<br>"
            + "</p>"
            + "<hr style='width: 90%; border:2px solid cyan; '>"
            + "<p>"
            + "You are receiving this email as a notifiction of your new account registration acknowledgement. Kindly disregard this email "
            + "and its information if you are not eligible, as the right receipient."
            + "</p>";

        return msg;
    }
    public static String getNewCoachNotification(String name, String email, String pass){
        String msg = "<h2>Dear, "+name+", </h2>"
            + "<p>"
            + "This is to notify you that you have been registered successfully as an accredited coach user on NAFA. This provides you with the capacity to manage "
                + "players, lineups, view game fixtures and so on."
            + "</p>"
                + "<p'>"
                + "Your login details are as follows:<br>"
                + "<div style='display: block'>Email: "+email+"</div>"
                + "<div style='display: block'>Password: "+pass+"</div>"
                + "<div style='display: block'>Click here: "+AppData.DOMAIN_COACH+"</div"
                + "</p>"
                + "<br>"
            + "<p style='margin-bottom: 100px; font-weight: bold'>"
            + "NAFA Administrator Team<br>"
            + "</p>"
            + "<hr style='width: 90%; border:2px solid cyan; '>"
            + "<p>"
            + "You are receiving this email as a notifiction of your new account registration acknowledgement. Kindly disregard this email "
            + "and its information if you are not eligible, as the right receipient."
            + "</p>";

        return msg;
    }
}
