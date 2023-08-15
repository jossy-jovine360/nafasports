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
public class GroupStandingDAO {
    public static String getGroupStandingDataByGroupTeamID(String gtid, String column){
        String name = "0";
        String query = "select "+column+" from "+AppData.GROUP_STANDINGS+" where iGroupTeamID = '"+gtid+"'";
        
        Connection conn = null;
        DataAccessObject dac = null;
        
        conn = Konnect.getNAFAConnection();
        dac = DataAccessObject.loadCustomRecord(query, conn);
        try {
            if(dac.next()){
                String first = dac.getString(1);
                name = first;
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(GroupStandingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return name;
    }
}
