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
public class OfficialDAO {
    public static LinkedHashMap loadAllOfficial(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.GAMES_OFFICIALS+"  order by vFullName asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    String state = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String name = dacs.getString(2);

                        value = name;

                        link.put(id, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(OfficialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
    public static LinkedHashMap loadAllOfficialsDesignations(){
        LinkedHashMap link = null;
        Connection conn_link1 = null;
        
        if(conn_link1 == null){
            conn_link1 = Konnect.getNAFAConnection();
            String query = "select * from "+ AppData.GAMES_OFFICIALS+"  group by vDesignation order by vDesignation asc";
            DataAccessObject dacs = ReportDAO.loadRecord(query, conn_link1);
            try {
                if(dacs.next()){
                    link = new LinkedHashMap();
                    dacs.previous();
                    String value = "";
                    while(dacs.next()){
                        String id = dacs.getString(1);
                        String name = dacs.getString(6);

                        value = name;

                        link.put(value, value);
                    }
                }
                conn_link1.close();
            } catch (SQLException ex) {
                Logger.getLogger(OfficialDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return link;
    }
}
