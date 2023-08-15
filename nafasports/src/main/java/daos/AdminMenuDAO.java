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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author josiah
 */
public class AdminMenuDAO {
    public static String getMenuName(String menuid){
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
            Logger.getLogger(AdminMenuDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return val;
    }
}
