/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import appData.AppData;
import appData.Konnect;
import com.jovine.dao.DataAccessObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class ProfileMenuDAO {
    public static LinkedHashMap loadProfileMenus(String userid) {
        LinkedHashMap link = null;
        Connection conn = Konnect.getNAFAConnection();
        String query = "select iMenuID, vMenu from " + AppData.MENUS + "";
        DataAccessObject dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                dac.previous();
                String name = "";
                link = new LinkedHashMap();
                while(dac.next()){
                    String id = dac.getString(1);
                    String code = dac.getString(2);
                    name = code;
                    link.put(id, name);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProfileMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn);
        return link;
    }
    public static String getProfileMenuByID(String menuid){
        String val = "";
        String query = "select vMenu from "+AppData.MENUS+" where iMenuID = '"+menuid+"'";
        
        Connection conn2 = null;
        DataAccessObject dac = null;
        
        conn2 = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn2);
        try {
            if(dac.next()){
                String profile = dac.getString(1);
                val = profile;
            }
            conn2.close();
        } catch (SQLException ex) {
            Logger.getLogger(ProfileMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
}
