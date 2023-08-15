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
public class AdminRoleDAO {
    
    public static DataAccessObject loadAdminSubMenusID(String email){
        DataAccessObject menus = null;
        Connection connect = null;
        String query = "select * from "+AppData.ADMIN_MENUS+"  where vEmail = '"+email+"'";
        
        connect = Konnect.getNAFAConnection();
        menus = DataAccessObject.loadCustomRecord(query, connect);
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminRoleDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return menus;
    }
    public static String getAdminSubmenu(String submenuid){
        String val = "";
        String query = "select vSubMenu from "+AppData.MENU_SUBS+" where iSubID = '"+submenuid+"'";
        
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
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getAdminSubmenuMenuID(String submenuid){
        String val = "";
        String query = "select iMenuID from "+AppData.MENU_SUBS+" where iSubID = '"+submenuid+"'";
        
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
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static String getAdminSubmenuMenu(String menuid){
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
            Logger.getLogger(AdminDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
    public static LinkedHashMap loadAllAdminRoles(){
        LinkedHashMap link = null;
        Connection conn_link = null;
        
        if(conn_link == null){
            conn_link = Konnect.getNAFAConnection();
            String query = "select iRoleID, vRole from "+ AppData.ROLES +" where vRole != 'super administrator'";
            link = DataAccessObject.loadCustomRecords(query, Boolean.FALSE, conn_link);
        }
        
        return link;
    }
}
