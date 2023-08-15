/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package appData;

import com.jovine.connection.DataConnection;
import java.sql.Connection;

/**
 *
 * @author josiah
 */
public class Konnect {
    public static Connection getNAFAConnection(){
        String offline_url = AppData.offline_url_nafa;
        String offline_username = AppData.username_nafa;
        String offline_password = AppData.password_nafa;
        
        String online_url = AppData.online_url_nafa;
        String online_username = AppData.online_username_nafa;
        String online_password = AppData.online_password_nafa;
        
        Connection connection = null;
//        connection = DataConnection.getConnection(offline_url, offline_username, offline_password);
        connection = DataConnection.getConnection(online_url, online_username, online_password);
        
        
        return connection;
    }
}
